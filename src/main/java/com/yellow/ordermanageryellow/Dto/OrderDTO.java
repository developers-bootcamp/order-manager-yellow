package com.yellow.ordermanageryellow.Dto;

import com.yellow.ordermanageryellow.model.Orders;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {
    private String orderId ;
    private String customerId ;
    private int paymentAmount;
    private String address;
    private String email;
    private String telephone;
    private Orders.status orderStatusId;
    private long creditCardNumber;
    private LocalDate expiryOn;
    private String cvc;

//	Credit/Debit


}
