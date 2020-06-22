package com.alfabattle.task2.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentGroupInfo {
    private BigDecimal min;
    private BigDecimal max;
    private BigDecimal sum;
    private int totalCount;
}
