package com.yellow.ordermanageryellow.model;

import org.apache.catalina.User;
import org.springframework.data.mongodb.core.mapping.DBRef;

public class TopEmploeey {
    @DBRef
    private User user;
    private String CountOrders;


}
