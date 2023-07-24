package com.yellow.ordermanageryellow.model;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.DBRef;

@Data
@AllArgsConstructor
public class OrderPayment {
    @DBRef
    private Orders orderId;

    @DBRef
    private Users userId;

    private int amount;

    private long invoiceNumber;

    private PaymentTypes paymentType;

    private AuditData auditData;
}
