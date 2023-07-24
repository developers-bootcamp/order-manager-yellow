package com.yellow.ordermanageryellow.controller;

import com.yellow.ordermanageryellow.service.OrdersStatusService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.Month;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/OrdersStatus")
public class OrdersStatusController {

    @Autowired
    private OrdersStatusService ordersStatusService;

    @GetMapping()
//    public ResponseEntity<Map<String,Map<Integer,Integer>>> getStatus() {
    public ResponseEntity getStatus(@RequestParam Integer monthAmount) {
        try {
            Map<Month, Map<Integer, Integer>> ordersMap = ordersStatusService.getStatus(monthAmount);
            return ResponseEntity.ok().body(ordersMap);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

}




