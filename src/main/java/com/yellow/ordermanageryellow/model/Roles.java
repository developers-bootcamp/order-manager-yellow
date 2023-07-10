package com.yellow.ordermanageryellow.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.annotation.Collation;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

enum name{ Admin,employee,customer};
@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "Roles")

public class Roles {
    @Id
    private String id;
    private name name;
    private String desc;
    private AuditData auditData;



}
