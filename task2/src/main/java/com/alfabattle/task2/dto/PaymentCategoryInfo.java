package com.alfabattle.task2.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class PaymentCategoryInfo {
    private BigDecimal min;
    private BigDecimal max;
    private BigDecimal sum;
}
