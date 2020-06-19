package com.alfabattle.task2.controllers;

import com.alfabattle.task2.entities.PaymentAnalyticsResult;
import com.alfabattle.task2.entities.UserPaymentStats;
import com.alfabattle.task2.entities.UserTemplate;
import com.alfabattle.task2.services.PaymentAnalyticService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/analytic")
@RequiredArgsConstructor
public class PaymentAnalyticController {
    private final PaymentAnalyticService paymentAnalyticService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<PaymentAnalyticsResult> getAllAnalytic() {
        return paymentAnalyticService.getAllAnalytic();
    }

    @GetMapping(value = "/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PaymentAnalyticsResult> getUserAnalytic(@PathVariable("userId") String userId) {
        var optRes = paymentAnalyticService.getAnalyticByUser(userId);
        if (optRes.isPresent()) {
            return ResponseEntity.ok(optRes.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping(value = "/{userId}/stats", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserPaymentStats> getPaymentStatsByUser(@PathVariable("userId") String userId) {
        var optRes = paymentAnalyticService.getStatsForUser(userId);
        if (optRes.isPresent()) {
            return ResponseEntity.ok(optRes.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping(value = "/{userId}/templates", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<UserTemplate> getUserTemplates(@PathVariable("userId") String userId) {
        return paymentAnalyticService.getUserPaymentTemplates(userId);
    }

    // - Выводит список всех пользователей с их категориями
    // - Выводит конкретного пользователя
    // - Статистика по пользователю:
    // наиболее частая категория трат,
    // наиболее редкая;
    // категория с максимальной суммой;
    // категория с минимальной суммой;
    // Будем делать легкую аналитику на постоянные платежи?* одна и та же сумма + повторяется более трех раз, категория одна + получатель один -> ввывести списком темплейт платежей
    // - В тестах должен быть кейс проверяющий корапченные данные в топике, такие данные должны отбрасываться и не идти на процессинг
}

