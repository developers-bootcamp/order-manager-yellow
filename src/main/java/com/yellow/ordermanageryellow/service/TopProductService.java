package com.yellow.ordermanageryellow.service;

import com.mongodb.client.AggregateIterable;
import com.yellow.ordermanageryellow.dao.OrdersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.Arrays;

import org.springframework.stereotype.Service;

import org.bson.Document;

@Service
public class TopProductService {

    private final OrdersRepository ordersRepository;
    @Autowired
    private  MongoTemplate mongoTemplate;
    @Autowired
    public TopProductService(OrdersRepository ordersRepository, MongoTemplate mongoTemplate) {
        this.ordersRepository = ordersRepository;
        this.mongoTemplate = mongoTemplate;
    }

    public AggregateIterable<Document> TopSoldProduct() {
        AggregateIterable<Document> result = mongoTemplate.getCollection("Orders").aggregate(Arrays.asList(new Document("$match",
                        new Document("audit_data.create_date",
                                new Document("$gte", "2023-04-01T00:00:00Z")
                                        .append("$lt", "2023-07-01T00:00:00Z"))),
                new Document("$unwind",
                        new Document("path", "$order_items")),
                new Document("$group",
                        new Document("_id",
                                new Document("month",
                                        new Document("$month",
                                                new Document("$dateFromString",
                                                        new Document("dateString", "$audit_data.create_date")
                                                                .append("format", "%Y-%m-%dT%H:%M:%SZ"))))
                                        .append("product", "$order_items.product_id"))
                                .append("count",
                                        new Document("$sum", 1L))),
                new Document("$lookup",
                        new Document("from", "Product")
                                .append("localField", "_id.product")
                                .append("foreignField", "id")
                                .append("as", "product")),
                new Document("$unwind",
                        new Document("path", "$product")
                                .append("preserveNullAndEmptyArrays", true)),
                new Document("$sort",
                        new Document("count", -1L)),
                new Document("$group",
                        new Document("_id", "$_id.month")
                                .append("products",
                                        new Document("$push",
                                                new Document("product", "$product.name")
                                                        .append("totalQuantity", "$count")))),
                new Document("$project",
                        new Document("_id", 0L)
                                .append("month", "$_id")
                                .append("products",
                                        new Document("$slice", Arrays.asList("$products", 2L))))));
        for (Document document : result) {
            System.out.println(document.toJson());
        }

                return  result;
        /*  Map<String, Map<String, Integer>> topProducts= new HashMap<>();
       LocalDate threeMonthsAgo = LocalDate.now().minusMonths(3);
       MatchOperation filterMonth = match(new Criteria("ordersRepository.auditData.createDate").gt(threeMonthsAgo));
       UnwindOperation unwind = Aggregation.unwind("ordersRepository.orderItems");
       GroupOperation groupByMonthAndProduct = group("ordersRepository.auditData.createDate.month","ordersRepository.orderItems.productId").count().as("countProduct") ;;
       SortOperation sortByPopDesc = sort(Sort.by(Sort.Direction.DESC, "$_id.month"));
        GroupOperation groupByMonth = group("ordersRepository.auditData.createDate.month","ordersRepository.orderItems.productId").push(new BasicDBObject
                ("product", "$_id.product").append
                ("totalQuantity", "$count")).as("arr");
        ProjectionOperation projectStage = Aggregation.project();
       Aggregation aggregation = newAggregation(filterMonth);
      AggregationResults<Document> product = mongoTemplate.aggregate(
             aggregation,  "topProduct", Document.class);

 return null;*/
    }
}
