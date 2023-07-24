package com.yellow.ordermanageryellow.model;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.List;

;

@AllArgsConstructor
@NoArgsConstructor

@Data
@Document(collection = "Orders")

public class Orders {
    @Id
    private String id;
    @DBRef
    private Users employee;
    @DBRef
    private Users customer;
    private double totalAmount;


    @DBRef
    private List<Order_Items> orderItems;
    private status orderStatusId;
    @DBRef
    private Company companyId;
    private String creditCardNumber;
    private String expiryOn;
    private String cvc;
    private Boolean notificationFlag;
    private AuditData auditData;

    public Orders(String id) {
        this.id = id;
    }

    public enum status {New, cancelled, approved, charging, packing, delivered}
}
