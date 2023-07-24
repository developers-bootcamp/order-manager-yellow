package com.yellow.ordermanageryellow.Service;
import com.yellow.ordermanageryellow.Dao.OrdersRepository;
import com.yellow.ordermanageryellow.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.stereotype.Service;
import static com.mongodb.client.model.Aggregates.limit;
import org.springframework.data.mongodb.core.query.Criteria;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.project;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.limit;



import org.springframework.data.mongodb.core.aggregation.Aggregation;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class GraphService {
    @Autowired
    private OrdersRepository orderRepository;
    @Autowired
    private MongoTemplate mongoTemplate;
        public void topEmployee() {

            LocalDate threeMonthsAgo = LocalDate.now().minusMonths(3);
            Aggregation aggregation = Aggregation.newAggregation(
                     match(Criteria.where("OrderStatusId").is("1").and("auditData.createDate").gte(threeMonthsAgo)),
                    group("employee").count().as("CountOfDeliveredOrders"),
                    project("CountOfDeliveredOrders").and("employeeId").previousOperation(),
                    sort(Sort.Direction.DESC, "CountOfDeliveredOrders"),
                    (AggregationOperation) limit(5));

                    AggregationResults<TopEmploeey> result = mongoTemplate.aggregate(
                    aggregation, "Order", TopEmploeey.class);
                    List<TopEmploeey> resultList = result.getMappedResults();
                               }


    }























