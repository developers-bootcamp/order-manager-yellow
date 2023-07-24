package com.yellow.ordermanageryellow;

import com.mongodb.client.*;
import com.yellow.ordermanageryellow.dao.OrdersRepository;
import com.yellow.ordermanageryellow.model.Orders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.bson.Document;
import com.mongodb.client.MongoCollection;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Arrays;
import org.bson.Document;


import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.conversions.Bson;
import java.util.concurrent.TimeUnit;
import org.bson.Document;
import com.mongodb.client.AggregateIterable;


@EnableMongoRepositories
@SpringBootApplication
public class OrderManagerYellowApplication {
  public static void main(String[] args) {
         SpringApplication.run(OrderManagerYellowApplication.class, args);


    }}