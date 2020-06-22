package com.alfabattle.task2.repositories;

import com.alfabattle.task2.entities.PaymentAnalyticsResult;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface PaymentAnalyticRepository extends MongoRepository<PaymentAnalyticsResult, String> {
    Optional<PaymentAnalyticsResult> findByUserId(String userId);
}
