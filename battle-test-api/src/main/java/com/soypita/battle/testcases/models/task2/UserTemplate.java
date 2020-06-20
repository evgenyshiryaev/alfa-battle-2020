package com.soypita.battle.testcases.models.task2;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class UserTemplate {
    private String recipientId;
    private Integer categoryId;
    private BigDecimal amount;
}
