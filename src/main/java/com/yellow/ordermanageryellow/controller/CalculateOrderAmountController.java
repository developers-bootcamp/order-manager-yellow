package com.yellow.ordermanageryellow.controller;
import com.yellow.ordermanageryellow.model.Orders;
import org.springframework.http.HttpStatus;

import com.yellow.ordermanageryellow.Service.CalculateOrderAmountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.HashMap;


@RestController
@RequestMapping("/CalculateOrderAmount")
public class CalculateOrderAmountController {
    private final CalculateOrderAmountService calculateOrderAmountService;
    @Autowired
    public CalculateOrderAmountController(CalculateOrderAmountService calculateOrderAmountService){
        this.calculateOrderAmountService=calculateOrderAmountService;
    }
    @PostMapping
    @RequestMapping("/")
    public ResponseEntity< Map<String, HashMap<Double,Integer>> > calculateOrderAmount ( @RequestBody Orders order){
        try{
            return ResponseEntity.ok( this.calculateOrderAmountService.calculateOrderAmount(order));
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();

        }
    }
}


