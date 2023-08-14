package com.yellow.ordermanageryellow.service;

import com.yellow.ordermanageryellow.dao.OrdersRepository;
import com.yellow.ordermanageryellow.exceptions.NotValidStatusExeption;
import com.yellow.ordermanageryellow.model.Orders;
import com.yellow.ordermanageryellow.model.Orders.status;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;


@Service
public class OrdersService {
    @Autowired
    private final OrdersRepository ordersRepository;

    @Autowired
    public OrdersService(OrdersRepository OrdersRepository) {
        this.ordersRepository = OrdersRepository;
    }

    @Value("${pageSize}")
    private int pageSize;
    @Value("${rabbitmq.exchange.name}")
    private String exchange;
    @Value("${rabbitmq.routing.key}")
    private String routingKey;
    @Autowired
    private RabbitTemplate rabbitTemplate;
    public List<Orders> getOrders(String token, String userId, Orders.status status, int pageNumber) {

        String companyId = token;
        Sort.Order sortOrder = Sort.Order.asc("auditData.updateDate");
        Sort sort = Sort.by(sortOrder);

        Pageable pageable = PageRequest.of(pageNumber, pageSize/* pageSize parameter omitted */, sort);

        Page<Orders> pageOrders = ordersRepository.findByCompanyId_IdAndOrderStatusIdAndEmployee(companyId, status, userId, pageable);
        return pageOrders.getContent();

    }

    public String insert(Orders newOrder) {
        if (newOrder.getOrderStatusId() != status.New || newOrder.getOrderStatusId() != status.approved) {
            throw new NotValidStatusExeption("Order should be in status new or approve");
        }
        if(newOrder.getOrderStatusId() == status.approved){
            newOrder.setOrderStatusId(status.charging);
            rabbitTemplate.convertAndSend(exchange,routingKey,newOrder);}
        Orders order = ordersRepository.insert(newOrder);

        return order.getId();
    }

    public boolean edit(Orders currencyOrder) {
        if (currencyOrder.getOrderStatusId() != status.cancelled || currencyOrder.getOrderStatusId() != status.approved) {
            throw new NotValidStatusExeption("You can only approve or cancel an order");
        }

        Optional<Orders> order = ordersRepository.findById(currencyOrder.getId());
        if (order.isEmpty()) {
            throw new NoSuchElementException();
        }
        if (order.get().getOrderStatusId() != status.New || order.get().getOrderStatusId() != status.packing) {
            throw new NotValidStatusExeption("It is not possible to change an order that is not in status new or packaging");
        }
        if(currencyOrder.getOrderStatusId() == status.approved){
            currencyOrder.setOrderStatusId(status.charging);
            rabbitTemplate.convertAndSend(exchange,routingKey,currencyOrder);}
       ordersRepository.save(currencyOrder);
        return true;
    }


                sum = (p.get().getPrice()- p.get().getDiscountAmount()) * order.getOrderItems().get(i).getQuantity();
                o.put(sum, p.get().getDiscountAmount());

            } else {
                sum = (p.get().getPrice() * p.get().getDiscountAmount()) / 100 * (100 - p.get().getDiscountAmount()) * order.getOrderItems().get(i).getQuantity();
                o.put(sum, p.get().getDiscountAmount());
            }
            calculatedOrder.put(p.get().getName(), o);
            total += sum;
        }
        HashMap<Double, Integer> o = new HashMap<Double, Integer>();
        o.put(total, -1);
        calculatedOrder.put("-1", o);
        return calculatedOrder;
    }
}
