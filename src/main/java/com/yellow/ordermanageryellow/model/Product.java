package com.yellow.ordermanageryellow.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.annotation.Collation;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

enum Discount{
    Percentage,FixedAmount
}

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "Product")

public class Product {
    @Id
    private String id;
    private String name;
    private String desc;
    private double price;
    private Discount discount;
    @DBRef
    private ProductCategory ProductCategoryId;
    private int inventory;
    @DBRef
    private Company companyId;
    private AuditData auditData;
}
