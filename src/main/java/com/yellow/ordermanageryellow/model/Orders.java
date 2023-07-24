package com.yellow.ordermanageryellow.model;
import lombok.Getter;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Document(collection = "Orders")

public class Orders {
    private String id;
    private Users employee;
    private Users customer;
    private double totalAmount;
    @DBRef
    private List<Order_Items> orderItems;
    @Getter
    private Integer orderStatusId;
    private Company companyId;
    private long creditCardNumber;
    private LocalDate expiryOn;
    private String cvc;
    private Boolean notificationFlag;
    private AuditData auditData;
    private Date createDate;

    public Orders(String id) {
        this.id = id;
    }
}
