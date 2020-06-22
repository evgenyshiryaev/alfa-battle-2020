package com.alfabattle.task2.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
public class TemplateKey {
    private String userId;
    private String recipientId;
    private Integer categoryId;
    private BigDecimal amount;
}
