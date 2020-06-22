package com.soypita.battle.testcases.models.task2;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PaymentCategoryInfo {
    private BigDecimal min;
    private BigDecimal max;
    private BigDecimal sum;
}
