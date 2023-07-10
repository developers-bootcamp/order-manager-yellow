package com.yellow.ordermanageryellow.Service;

import com.yellow.ordermanageryellow.Dao.OrdersRepository;
import com.yellow.ordermanageryellow.model.Company;
import com.yellow.ordermanageryellow.model.Orders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;


@Service
public class OrdersService  {
    private  OrdersRepository OrdersRepository;

}