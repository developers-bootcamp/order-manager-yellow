package com.yellow.ordermanageryellow.controller;

import com.yellow.ordermanageryellow.Service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/Order")
public class OrderController {

        @Autowired
        private OrdersService orderService;




    //Deliver/Cancel Orders api
    @GetMapping("/getStatus")
//    public ResponseEntity<Map<String,Map<Integer,Integer>>> getStatus() {
    public ResponseEntity getStatus(@RequestParam Integer monthAmount) {
        try {
            Map<String, Map<Integer, Integer>> ordersMap = orderService.getStatus(monthAmount);
            return ResponseEntity.ok().body(ordersMap);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

}

