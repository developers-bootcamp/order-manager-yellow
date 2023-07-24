package com.yellow.ordermanageryellow.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.annotation.Collation;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

;
@Collation
@Document(collection = "Roles")
@AllArgsConstructor
@NoArgsConstructor
public class Roles {
    private String id;
    private name name;
    private String desc;
    @DBRef
    private AuditData auditData;


    public Roles(String id) {
        this.id = id;
    }
}
