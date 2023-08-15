package com.yellow.ordermanageryellow.service;

import com.mongodb.client.AggregateIterable;
import com.yellow.ordermanageryellow.Dto.ProductAmountDto;
import com.yellow.ordermanageryellow.Dto.TopProductDTO;
import com.yellow.ordermanageryellow.Exception.NoDataException;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.*;

import org.springframework.stereotype.Service;

import org.bson.Document;

@Service
public class TopProductService {


    @Autowired
    private MongoTemplate mongoTemplate;

    public AggregateIterable<Document> aggregationTopSoldProduct() {
        LocalDate currentDate = LocalDate.now();
        LocalDate beginningOfCurrentMonth = currentDate.withDayOfMonth(1);
        LocalDate threeMonthsAgo = beginningOfCurrentMonth.minusMonths(3);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String startDate = threeMonthsAgo.format(formatter);
        String endDate = beginningOfCurrentMonth.format(formatter);
        System.out.println(threeMonthsAgo);
        AggregateIterable<Document> result = mongoTemplate.getCollection("Orders").aggregate(Arrays.asList(new Document("$match",
                        new Document("auditData.createDate",
                                new Document("$gte",
                                        new java.util.Date(1648771200000L))
                                        .append("$lt",
                                                new java.util.Date(1719792000000L)))
                                .append("orderStatusId", "delivered")),
                new Document("$unwind",
                        new Document("path", "$orderItems")),
                new Document("$group",
                        new Document("_id",
                                new Document("month",
                                        new Document("$month", "$auditData.createDate"))
                                        .append("product", "$orderItems.productId"))
                                .append("count",
                                        new Document("$sum", "$orderItems.quantity"))),
                new Document("$addFields",
                        new Document("newField", "$_id.product.$id")),
                new Document("$lookup",
                        new Document("from", "Product")
                                .append("localField", "_id.product.$id")
                                .append("foreignField", "_id")
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
                                .append("product",
                                        new Document("$slice", Arrays.asList("$products", 5L))))));
        return result;
    }

    @SneakyThrows
    public List<TopProductDTO> topSoldProduct() {

        AggregateIterable<Document> result = aggregationTopSoldProduct();
        for (Document document : result) {
            System.out.println(document);
        }
        List<TopProductDTO> topProductsList = new ArrayList<>();
        for (Document document : result) {
            int monthInt = document.getInteger("month");
            Month monthEnum = Month.of(monthInt);
            TopProductDTO TopProductDTO = new TopProductDTO();
            TopProductDTO.setMonth(monthEnum);
            List<Document> products = (List<Document>) document.get("product");
            List<ProductAmountDto> ListProductAmountDto = new ArrayList<>();
            for (Document product : products) {
                String productName = product.getString("product");
                int totalQuantity = product.getInteger("totalQuantity");
                ProductAmountDto productAmountDto = new ProductAmountDto();
                productAmountDto.setProductName(productName);
                productAmountDto.setAmount(totalQuantity);
                ListProductAmountDto.add(productAmountDto);
            }
            TopProductDTO.setProducts(ListProductAmountDto);
            topProductsList.add(TopProductDTO);
        }
        if (topProductsList.size() == 0)
            throw new NoDataException("no orders in the last three months");
        return topProductsList;

    }
}

