package com.alfabattle.task2.controllers;

import com.alfabattle.task2.converters.UserPaymentAnalyticConverter;
import com.alfabattle.task2.dto.ErrorResponse;
import com.alfabattle.task2.dto.UserPaymentAnalytic;
import com.alfabattle.task2.entities.UserNotFoundException;
import com.alfabattle.task2.entities.UserPaymentStats;
import com.alfabattle.task2.entities.UserTemplate;
import com.alfabattle.task2.services.PaymentAnalyticService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/analytic")
@RequiredArgsConstructor
public class PaymentAnalyticController {
    private final PaymentAnalyticService paymentAnalyticService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<UserPaymentAnalytic> getAllAnalytic() {
        return paymentAnalyticService.getAllAnalytic();
    }

    @GetMapping(value = "/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserPaymentAnalytic> getUserAnalytic(@PathVariable("userId") String userId) {
        var res = paymentAnalyticService.getAnalyticByUser(userId);
        return ResponseEntity.ok(UserPaymentAnalyticConverter.convert(res));
    }

    @GetMapping(value = "/{userId}/stats", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserPaymentStats> getPaymentStatsByUser(@PathVariable("userId") String userId) {
        var res = paymentAnalyticService.getStatsForUser(userId);
        return ResponseEntity.ok(res);
    }

    @GetMapping(value = "/{userId}/templates", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<UserTemplate> getUserTemplates(@PathVariable("userId") String userId) {
        return paymentAnalyticService.getUserPaymentTemplates(userId);
    }


    @ResponseBody
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(UserNotFoundException.class)
    public ErrorResponse handle(UserNotFoundException ex) {
        return new ErrorResponse("user not found");
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

