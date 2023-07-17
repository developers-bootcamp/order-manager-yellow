package com.yellow.ordermanageryellow.Dao;

import com.yellow.ordermanageryellow.model.Currency;
import com.yellow.ordermanageryellow.model.Users;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface GetCurrencyRepository extends MongoRepository<Currency, String> {
}
