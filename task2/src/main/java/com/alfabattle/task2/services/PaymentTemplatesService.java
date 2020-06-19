package com.alfabattle.task2.services;

import com.alfabattle.task2.entities.UserTemplate;
import com.alfabattle.task2.repositories.UserTemplateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class PaymentTemplatesService {
    private final UserTemplateRepository userTemplateRepository;

    public void createTemplate(String userId, String recipientId, Integer categoryId, BigDecimal amount) {
        var optTemplate = userTemplateRepository.findByUserIdAndAmountAndCategoryIdAndRecipientId(userId, amount, categoryId, recipientId);
        if (optTemplate.isEmpty()) {
            userTemplateRepository.save(UserTemplate
                    .builder()
                    .userId(userId)
                    .categoryId(categoryId)
                    .amount(amount)
                    .recipientId(recipientId)
                    .build());
        }
    }
}
