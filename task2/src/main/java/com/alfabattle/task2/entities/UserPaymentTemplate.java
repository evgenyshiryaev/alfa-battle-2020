package com.alfabattle.task2.entities;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class UserPaymentTemplate {
    private Integer categoryId;
    private String recipientId;
    private BigDecimal amount;
}
