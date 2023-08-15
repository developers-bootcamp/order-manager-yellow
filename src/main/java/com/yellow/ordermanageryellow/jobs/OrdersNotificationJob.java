package com.yellow.ordermanageryellow.jobs;

import com.yellow.ordermanageryellow.model.Orders;
import com.yellow.ordermanageryellow.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;
import com.yellow.ordermanageryellow.Exception.*;
import java.util.List;

public class OrdersNotificationJob {
    @Autowired
    private OrdersService orderService;
    @Scheduled(cron = "* * * * * ?")
    @Transactional
    public void OrderNotifications() throws NotFoundException {
        System.out.println("hi");
        List<Orders> ordersToNotify = orderService.getOrdersWithNotificationFlag();
        for (Orders order : ordersToNotify) {
            Notification(order);
            order.setNotificationFlag(false);
            orderService.edit(order);
        }
    }
    private void Notification(Orders order) {
        mockNotification(order);
    }
    private void mockNotification(Orders order) {
        System.out.println("mock notification sent for order ID: " + order.getId());
    }
}
