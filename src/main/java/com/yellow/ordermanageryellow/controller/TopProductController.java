package com.yellow.ordermanageryellow.controller;


import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoIterable;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.yellow.ordermanageryellow.service.TopProductService;

@RestController
@RequestMapping("/graph")
public class TopProductController {
    private final TopProductService topProductService;

    @Autowired
    public TopProductController(TopProductService topProductService){
        this.topProductService=topProductService;
    }
    @GetMapping("/topProductService")
    public MongoIterable<Document> topProduct(){
        try {
            AggregateIterable<Document> result = topProductService.TopSoldProduct();
            for (Document document : result) {
                System.out.println(document.toJson());
            }
            return result;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
