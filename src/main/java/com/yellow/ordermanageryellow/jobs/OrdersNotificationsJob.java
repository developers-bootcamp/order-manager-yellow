package com.yellow.ordermanageryellow.jobs;

import com.yellow.ordermanageryellow.Dao.OrdersRepository;
import com.yellow.ordermanageryellow.model.Orders;
import com.yellow.ordermanageryellow.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;
import com.yellow.ordermanageryellow.Exception.*;
import java.util.List;
import java.util.Optional;

public class OrdersNotificationsJob {
    @Autowired
    private OrdersService orderService;
    @Autowired
    private OrdersRepository ordersRepository;
    @DBRef  List<Orders> ordersToNotify;
    @Scheduled(cron = "*/2 * * * * *")
    @Transactional
    public void OrderNotifications() throws NotFoundException {
        List<Orders> ordersToNotify = ordersRepository.findByNotificationFlag(true);

        for (Orders order : ordersToNotify) {
            Notification(order);
            order.setNotificationFlag(false);
        }
      ordersRepository.saveAll(ordersToNotify);
    }
    private void Notification(Orders order) {
        mockNotification(order);
    }
    private void mockNotification(Orders order) {
        System.out.println("mock notification sent for order ID: " + order.getId());
    }
}