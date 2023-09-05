package com.yellow.ordermanageryellow.model;

import lombok.*;
import lombok.experimental.SuperBuilder;
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Setter @Getter
public class Filter {
    private String fieldName;
    private Object filterValue;
}