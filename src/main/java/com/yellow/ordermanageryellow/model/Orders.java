package com.yellow.ordermanageryellow.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.core.annotation.Order;
import org.springframework.data.mongodb.core.annotation.Collation;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.List;

@Document(collection = "Orders")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Orders {
    private String id;
    private Users employee;
    private Users customer;
    private double totalAmount;
    private List<Order_Items> orderItems;
    private String OrderStatusId;
    @DBRef
    private Company companyId;
    private long CreditCardNumber;
    private LocalDate expiryOn;
    private String cvc;
    private Boolean notificationFlag;
    private AuditData auditData;


}
