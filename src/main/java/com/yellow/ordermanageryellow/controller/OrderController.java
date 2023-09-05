package com.yellow.ordermanageryellow.controller;

import com.yellow.ordermanageryellow.model.Filter;
import com.yellow.ordermanageryellow.service.OrdersService;
import com.yellow.ordermanageryellow.exceptions.NotValidStatusExeption;
import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.yellow.ordermanageryellow.model.Orders;


import java.util.*;

import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/order")
@RestController

public class OrderController {
    private final OrdersService orderservice;

    @Autowired
    public OrderController(OrdersService orderservice) {
        this.orderservice = orderservice;
    }



    @GetMapping("/order-test")
    public ResponseEntity getOrderTest() {
        return ResponseEntity.ok("");
    }

        @GetMapping("/")
        public ResponseEntity getOrders(@PathParam("isCancelled") boolean isCanceled, @PathParam("pageNumber") int pageNumber, @RequestBody ArrayList<Filter> filter ) {
        try {

            List<Orders> orders = orderservice.getOrders("token", isCanceled, pageNumber,new ArrayList<>());
            return ResponseEntity.ok(orders);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity insert(@RequestBody Orders newOrder) {
        try {
            String orderId = orderservice.insert(newOrder);
            return ResponseEntity.ok((orderId));
        } catch (NotValidStatusExeption ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());

        }
    }

    @PutMapping
    public ResponseEntity edit(@RequestBody Orders currencyOrder) {
        try {
            orderservice.edit(currencyOrder);
            return ResponseEntity.ok(true);
        } catch (NotValidStatusExeption ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        } catch (NoSuchElementException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("not found order");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }

    }
    @PostMapping
    @RequestMapping("/CalculateOrderAmount")
    public ResponseEntity<Map<String, HashMap<Double,Integer>>> calculateOrderController (@RequestBody Orders order){
        try{
            return ResponseEntity.ok( this.orderservice.calculateOrderService(order));
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();

        }
    }
}