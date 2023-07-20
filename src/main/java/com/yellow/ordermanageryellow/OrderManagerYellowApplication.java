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
    // private final OrdersRepository ordersRepository;

    /* public OrderManagerYellowApplication(OrdersRepository ordersRepository, MongoTemplate mongoTemplate) {
         this.ordersRepository = ordersRepository;
     }*/




    public static void main(String[] args) {
        //ApplicationContext context =
           SpringApplication.run(OrderManagerYellowApplication.class, args);
        //SpringApplication.run(OrderManagerYellowApplication.class, args);
        /*
         * Requires the MongoDB Java Driver.
         * https://mongodb.github.io/mongo-java-driver
         */

        /*   MongoClient mongoClient = new MongoClient(
                new MongoClientURI(
                        "mongodb://localhost:27017/"
                )
        );*/
     /*   com.mongodb.client.MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
        MongoDatabase database = mongoClient.getDatabase("Orders");
        MongoCollection<Document> collection = database.getCollection("Orders");*/
        // AggregateIterable<Document> result = mongoTemplate.getCollection("Orders").aggregate(Arrays.asList(...));
       /* OrderManagerYellowApplication mainClass = context.getBean(OrderManagerYellowApplication.class);
        mainClass.q();
*/
    }}