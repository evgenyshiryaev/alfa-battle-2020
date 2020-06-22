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
public class RawPaymentInfo {
    private String ref;
    private Integer categoryId;
    private String userId;
    private String recipientId;
    private String desc;
    private BigDecimal amount;
}

