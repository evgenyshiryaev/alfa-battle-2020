package com.alfabattle.task2.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document("payment-analytic")
public class PaymentAnalyticsResult {
    @Id
    private String userId;
    private BigDecimal totalSum;
    private Map<Integer, PaymentGroupInfo> analyticInfo;
}
