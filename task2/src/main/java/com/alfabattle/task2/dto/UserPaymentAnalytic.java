package com.alfabattle.task2.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Map;

@Data
@Builder
public class UserPaymentAnalytic {
    private String userId;
    private BigDecimal totalSum;
    private Map<Integer, PaymentCategoryInfo> analyticInfo;
}
