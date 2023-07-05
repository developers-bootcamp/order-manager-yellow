package com.yellow.ordermanageryellow.Service;
import com.yellow.ordermanageryellow.Dao.OrdersRepository;
import com.yellow.ordermanageryellow.model.Company;
import com.yellow.ordermanageryellow.model.Orders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Month;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class OrdersService implements CommandLineRunner {
    private final OrdersRepository OrdersRepository;

    @Autowired
    public OrdersService(OrdersRepository OrdersRepository) {
        this.OrdersRepository = OrdersRepository;
    }
    @Override
    public void run(String... args) {
        Orders Orders = new Orders("12");
        OrdersRepository.save(Orders);
    }



    public Map<String, Map<Integer,Integer>> getStatus(Integer monthAmount) {


    LocalDate currentdate = LocalDate.now();

        int cancel = 0, deliver = 0;
        Map<String, Map<Integer, Integer>> mapOrder = new HashMap<>();


        for (int i = 0; i < monthAmount; i++) {
         Month currentMonth = currentdate.minusMonths(i).getMonth();
            List<Orders> OrderMonth = OrdersRepository.findByAuditDataCreateDate(currentdate);
            for(Orders o:OrderMonth){
                if(o.getOrderStatusId().equals("cancel"))
                    cancel++;
                else
                    deliver++;
            }
            Map<Integer,Integer> mapMonth = new HashMap<>();
            mapMonth.put(cancel,deliver);
            mapOrder.put(String.valueOf(currentMonth),mapMonth);
        }
        return mapOrder;
    }
    }



