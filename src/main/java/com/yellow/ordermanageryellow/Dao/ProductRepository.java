package com.yellow.ordermanageryellow.Dao;

import com.yellow.ordermanageryellow.model.Product;
import com.yellow.ordermanageryellow.model.Users;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository extends MongoRepository<Product, String> {
}
