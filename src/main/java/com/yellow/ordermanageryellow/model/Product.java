package com.yellow.ordermanageryellow.model;

import lombok.experimental.SuperBuilder;
import org.springframework.data.mongodb.core.annotation.Collation;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

enum Discount{
    Percentage,FixedAmount
}
@Collation
@SuperBuilder(toBuilder = true)

@Document(collection = "Product")

public class Product {
    private String id;

    public Product(String id) {
        this.id = id;
    }

    private String name;
    private String desc;
    private double price;
    private Discount discount;
    private ProductCategory categoryId;
    private int inventory;
    private Company companyId;
    @DBRef
    private AuditData auditData;
}
