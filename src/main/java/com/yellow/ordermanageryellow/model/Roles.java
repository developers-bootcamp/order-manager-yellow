package com.yellow.ordermanageryellow.model;

import lombok.Data;
import org.springframework.data.mongodb.core.annotation.Collation;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

;
@Data
@Collation
@Document(collection = "Roles")

public class Roles {
    private String id;
    private RoleNames name;
    private String desc;
    private AuditData auditData;
}
