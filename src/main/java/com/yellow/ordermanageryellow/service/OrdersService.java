package com.yellow.ordermanageryellow.service;

import com.yellow.ordermanageryellow.Dao.OrdersRepository;
import com.yellow.ordermanageryellow.exception.NotValidStatusExeption;
import com.yellow.ordermanageryellow.model.Orders;
import com.yellow.ordermanageryellow.model.Orders.status;
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


}