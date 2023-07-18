package com.yellow.ordermanageryellow.Service;

import com.yellow.ordermanageryellow.Dao.OrdersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

@Service
public class TopProductService {
    private final MongoTemplate mongoTemplate;
    private final OrdersRepository ordersRepository;
    @Autowired
    public TopProductService(OrdersRepository ordersRepository, MongoTemplate mongoTemplate) {
        this.ordersRepository = ordersRepository;
        this.mongoTemplate = mongoTemplate;
    }

    public ResponseEntity<Map<String, Map<String, Integer>>> TopSoldProduct() {
        Map<String, Map<String, Integer>> topProducts= new HashMap<>();
        LocalDate threeMonthsAgo = LocalDate.now().minusMonths(3);
        MatchOperation filterMonth = match(new Criteria("ordersRepository.auditData.createDate").gt(threeMonthsAgo));
        GroupOperation groupByMonth = group("ordersRepository.auditData.createDate.month");
        GroupOperation groupByProduct = group("ordersRepository.orderItems.productId").count().as("Product") ;
        Aggregation aggregation = newAggregation(filterMonth,groupByProduct,groupByMonth);
        AggregationResults<OrdersRepository> product = mongoTemplate.aggregate(
              aggregation,  "topProduct", OrdersRepository.class);
 return null;
    }
}
