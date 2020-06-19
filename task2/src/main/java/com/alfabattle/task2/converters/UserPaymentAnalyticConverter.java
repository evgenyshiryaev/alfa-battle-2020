package com.alfabattle.task2.converters;

import com.alfabattle.task2.dto.PaymentCategoryInfo;
import com.alfabattle.task2.dto.UserPaymentAnalytic;
import com.alfabattle.task2.entities.PaymentAnalyticsResult;

import java.util.Map;
import java.util.stream.Collectors;

public class UserPaymentAnalyticConverter {
    public static UserPaymentAnalytic convert(PaymentAnalyticsResult res) {
        var result = UserPaymentAnalytic
                .builder()
                .userId(res.getUserId())
                .totalSum(res.getTotalSum())
                .build();
        var resAnalytic = res.getAnalyticInfo().entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey,
                        e -> PaymentCategoryInfo
                                .builder()
                                .max(e.getValue().getMax())
                                .min(e.getValue().getMin())
                                .sum(e.getValue().getSum())
                                .build()
                ));
        result.setAnalyticInfo(resAnalytic);
        return result;
    }
}
