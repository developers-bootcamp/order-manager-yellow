package com.yellow.ordermanageryellow.service;

import com.yellow.ordermanageryellow.Dao.OrdersRepository;
import com.yellow.ordermanageryellow.Dto.OrderDTO;
import com.yellow.ordermanageryellow.Dto.OrderMapper;
import com.yellow.ordermanageryellow.model.Order_Items;
import com.yellow.ordermanageryellow.model.Orders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChargingService {
    @Autowired
    private OrdersRepository ordersRepository;
    private RabbitMQProducer rabbitMQProducer;

    public void chargingStep(Orders order) {
        try{
            order.setOrderStatusId(Orders.status.charging);
            for (Order_Items item:order.getOrderItems())
            {
                if(item.getProductId().getInventory()<item.getQuantity())
                    order.setOrderStatusId(Orders.status.cancelled);
                else{
                    item.getProductId().setInventory((int)(item.getProductId().getInventory()-item.getQuantity()));
                    OrderDTO orderDTO = OrderMapper.INSTANCE.orderToOrderDTO(order);
                    rabbitMQProducer.sendMessage(orderDTO);}
            }
            ordersRepository.save(order);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
    public void CompletedPayment(OrderDTO orderDTO) {
        Orders order =ordersRepository.findById(orderDTO.getOrderId()).orElse(null);
        if(order.getOrderStatusId().equals(Orders.status.approved))
            order.setOrderStatusId(Orders.status.packing);
        else {
            order.setOrderStatusId(Orders.status.cancelled);
            for (Order_Items item:order.getOrderItems())
                item.getProductId().setInventory((int)(item.getProductId().getInventory()+item.getQuantity()));
        }
        ordersRepository.save(order);
    }
}
