package com.yellow.ordermanageryellow;

import com.yellow.ordermanageryellow.model.Orders;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.time.LocalDate;

@EnableMongoRepositories
@SpringBootApplication
public class OrderManagerYellowApplication {

	public static void main(String[] args) {

		SpringApplication.run(OrderManagerYellowApplication.class, args);
		Orders o=new Orders();
}
}

