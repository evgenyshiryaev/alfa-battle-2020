package com.alfabattle.task2.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JsonIgnore
    private int totalCount;
}
