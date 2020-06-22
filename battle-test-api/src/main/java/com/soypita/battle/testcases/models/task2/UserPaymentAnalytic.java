package com.soypita.battle.testcases.models.task2;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Map;

@Data
public class UserPaymentAnalytic {
    private String userId;
    private BigDecimal totalSum;
    private Map<Integer, PaymentCategoryInfo> analyticInfo;
}