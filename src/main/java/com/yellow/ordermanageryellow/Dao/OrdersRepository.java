package com.yellow.ordermanageryellow.Dao;

import com.yellow.ordermanageryellow.model.Orders;
import com.yellow.ordermanageryellow.model.Users;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

public interface OrdersRepository extends MongoRepository<Orders, String> {



    List<Orders> findByAuditDataCreateDate(LocalDate currentdate);
}
