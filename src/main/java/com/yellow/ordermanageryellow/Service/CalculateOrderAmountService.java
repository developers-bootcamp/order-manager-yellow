package com.yellow.ordermanageryellow.Service;

import com.yellow.ordermanageryellow.model.Discount;
import com.yellow.ordermanageryellow.model.Product.*;
import com.yellow.ordermanageryellow.model.Order_Items;
import com.yellow.ordermanageryellow.model.Product;

import com.yellow.ordermanageryellow.model.Orders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.yellow.ordermanageryellow.Dao.OrdersRepository;
import com.yellow.ordermanageryellow.Dao.ProductRepository;


import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;
@Service

public class CalculateOrderAmountService {
    private    OrdersRepository ordersRepository;
    private  ProductRepository productRepository;

    @Autowired
  public CalculateOrderAmountService(OrdersRepository ordersRepository) {
        this.ordersRepository = ordersRepository;
    }

    public Map<String, HashMap<Double, Integer>> calculateOrderAmount(@RequestParam Orders order){
        HashMap<String, HashMap<Double, Integer>> calculatedOrder = new HashMap<String, HashMap<Double, Integer>>();
        double total=0;
        for (int i = 0; i < order.getOrderItems().stream().count(); i++) {
            Order_Items oi = order.getOrderItems().get(i);
            Product p = oi.getProductId();
            HashMap<Double, Integer> o = new HashMap<Double, Integer>();
            double sum = 0;
            if (p.getDiscount() == Discount.FixedAmount) {
                sum =( p.getPrice() - p.getSumDiscount())*order.getOrderItems().get(i).getQuantity();
                o.put(sum, p.getSumDiscount());
            } else {
                sum = (p.getPrice() * p.getSumDiscount()) / 100 * (100 - p.getSumDiscount())*order.getOrderItems().get(i).getQuantity();
                o.put(sum, p.getSumDiscount());
            }
            calculatedOrder.put(p.getId(), o);
             total += sum;
        }
        HashMap<Double, Integer> o = new HashMap<Double, Integer>();
        o.put(total, -1);
        calculatedOrder.put("-1", o);
        return calculatedOrder;
    }

        }
