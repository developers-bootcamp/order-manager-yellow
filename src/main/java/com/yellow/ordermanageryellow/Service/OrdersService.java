package com.yellow.ordermanageryellow.Service;

import com.yellow.ordermanageryellow.Dto.ProductDTO;
import com.yellow.ordermanageryellow.dao.OrdersRepository;
import com.yellow.ordermanageryellow.dao.ProductRepository;

import com.yellow.ordermanageryellow.exceptions.NotValidStatusExeption;
import com.yellow.ordermanageryellow.model.Discount;
import com.yellow.ordermanageryellow.model.Order_Items;
import com.yellow.ordermanageryellow.model.Orders;
import com.yellow.ordermanageryellow.model.Orders.status;
import com.yellow.ordermanageryellow.model.Product;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;


@Service
public class OrdersService {
    @Autowired
    private OrdersRepository ordersRepository;
    @Autowired
    private ProductRepository productRepository;

    @Value("${pageSize}")
    private int pageSize;

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

       ordersRepository.save(currencyOrder);
        return true;
    }
    public Map<String, HashMap<Double, Integer>> calculateOrderService( @RequestParam  Orders order) {
        HashMap<String, HashMap<Double, Integer>> calculatedOrder = new HashMap<String, HashMap<Double, Integer>>();
        double total = 0;
        for (int i = 0; i < order.getOrderItems().stream().count(); i++) {
            Order_Items orderItem = order.getOrderItems().get(i);
            Optional<Product> p = productRepository.findById(orderItem.getProductId().getId());
            HashMap<Double, Integer> o = new HashMap<Double, Integer>();
            double sum = 0;
            if (p.get().getDiscount() == Discount.FixedAmount) {

                sum = (p.get().getPrice()- p.get().getDiscountAmount()) * order.getOrderItems().get(i).getQuantity();
                o.put(sum, p.get().getDiscountAmount());

            } else {
                sum = (p.get().getPrice() * p.get().getDiscountAmount()) / 100 * (100 - p.get().getDiscountAmount()) * order.getOrderItems().get(i).getQuantity();
                o.put(sum, p.get().getDiscountAmount());
            }
            calculatedOrder.put(p.get().getId(), o);
            total += sum;
        }
        HashMap<Double, Integer> o = new HashMap<Double, Integer>();
        o.put(total, -1);
        calculatedOrder.put("-1", o);
        return calculatedOrder;
    }


}