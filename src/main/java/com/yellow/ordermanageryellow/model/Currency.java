package com.yellow.ordermanageryellow.model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
@Document(collection = "Currency")

public enum Currency {
    DOLLAR(1, "USD"),
    EURO(2, "EUR"),
    SHEKEL(3, "ILS"),
    FRENCH_FRANC(4, "CHF"),
    POUND_STERLING(5, "GBP");
    private final int id;
    private final String code;
    Currency(int id, String code) {
        this.id = id;
        this.code = code;
    }}

