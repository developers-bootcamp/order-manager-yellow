package com.yellow.ordermanageryellow.model;

import lombok.*;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.annotation.Collation;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "Company")
public class Company {

    public Company(String id){
        this.id=id;
    }
    @Id
    private String id;
    private String name;
    private String currency;
    private AuditData auditData;
}
