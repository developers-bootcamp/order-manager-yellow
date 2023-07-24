package com.yellow.ordermanageryellow.model;
import lombok.AllArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.core.annotation.Order;
import org.springframework.data.mongodb.core.annotation.Collation;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.List;

@Document(collection = "Orders")
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
public class Orders {
    private String id;
    private Users employee;
    private Users customer;
    private double totalAmount;
    @DBRef
    private List<Order_Items> orderItems;
    private String OrderStatusId;
    private Company companyId;
    private long CreditCardNumber;
    private LocalDate expiryOn;
    private String cvc;
    private Boolean notificationFlag;
    @DBRef
    private AuditData auditData;


}
