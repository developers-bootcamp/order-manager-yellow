package com.yellow.ordermanageryellow.controller;

import com.yellow.ordermanageryellow.Service.GraphService;
import com.yellow.ordermanageryellow.Service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/Orders")
public class OrdersController {
    @Autowired
    private OrdersService ordersService;

    @GetMapping("/fill")
    public void fill() {
        ordersService.fill();
    }

}
