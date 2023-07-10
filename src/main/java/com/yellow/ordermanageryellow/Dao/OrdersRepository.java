package com.yellow.ordermanageryellow.Dao;

import com.yellow.ordermanageryellow.model.Orders;
import com.yellow.ordermanageryellow.model.Users;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdersRepository extends MongoRepository<Orders, String> {
}
