package com.yellow.ordermanageryellow.dao;

import com.yellow.ordermanageryellow.model.Orders;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDate;
import java.util.List;

public interface OrdersRepository extends MongoRepository<Orders, String> {



    List<Orders> findByAuditDataCreateDate(LocalDate currentdate);
}
