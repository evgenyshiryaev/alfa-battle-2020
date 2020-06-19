package com.alfabattle.task2.services;

import com.alfabattle.task2.converters.UserPaymentAnalyticConverter;
import com.alfabattle.task2.dto.PaymentCategoryInfo;
import com.alfabattle.task2.dto.UserPaymentAnalytic;
import com.alfabattle.task2.entities.PaymentAnalyticsResult;
import com.alfabattle.task2.entities.UserPaymentStats;
import com.alfabattle.task2.entities.UserTemplate;
import com.alfabattle.task2.repositories.PaymentAnalyticRepository;
import com.alfabattle.task2.repositories.UserTemplateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PaymentAnalyticService {
    private final PaymentAnalyticRepository paymentAnalyticRepository;
    private final UserTemplateRepository userTemplateRepository;

    public List<UserPaymentAnalytic> getAllAnalytic() {
        var rawAnalytic = paymentAnalyticRepository.findAll();
        List<UserPaymentAnalytic> res = rawAnalytic
                .stream()
                .map(UserPaymentAnalyticConverter::convert)
                .collect(Collectors.toList());
        return res;
    }

    public Optional<PaymentAnalyticsResult> getAnalyticByUser(String userId) {
        return paymentAnalyticRepository.findByUserId(userId);
    }

    public Optional<UserPaymentStats> getStatsForUser(String userId) {
        var optUserAnalytic = paymentAnalyticRepository.findByUserId(userId);
        if (optUserAnalytic.isEmpty()) {
            return Optional.empty();
        }
        var userAnalytic = optUserAnalytic.get();

        Integer oftenCategoryId = userAnalytic.getAnalyticInfo()
                .entrySet()
                .stream()
                .max(Comparator.comparing(e -> e.getValue().getTotalCount()))
                .get()
                .getKey();

        Integer rareCategoryId = userAnalytic.getAnalyticInfo()
                .entrySet()
                .stream()
                .min(Comparator.comparing(e -> e.getValue().getTotalCount()))
                .get()
                .getKey();

        Integer maxAmountCategoryId = userAnalytic.getAnalyticInfo()
                .entrySet()
                .stream()
                .max(Comparator.comparing(e -> e.getValue().getMax()))
                .get()
                .getKey();

        Integer minAmountCategoryId = userAnalytic.getAnalyticInfo()
                .entrySet()
                .stream()
                .min(Comparator.comparing(e -> e.getValue().getMax()))
                .get()
                .getKey();

        return Optional.ofNullable(
                UserPaymentStats.builder()
                        .oftenCategoryId(oftenCategoryId)
                        .rareCategoryId(rareCategoryId)
                        .maxAmountCategoryId(maxAmountCategoryId)
                        .minAmountCategoryId(minAmountCategoryId)
                        .build()
        );

    }

    public List<UserTemplate> getUserPaymentTemplates(String userId) {
        return userTemplateRepository.findByUserId(userId);
    }
}
