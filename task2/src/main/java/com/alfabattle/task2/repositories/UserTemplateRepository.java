package com.alfabattle.task2.repositories;

import com.alfabattle.task2.entities.UserTemplate;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface UserTemplateRepository extends MongoRepository<UserTemplate, String> {
    List<UserTemplate> findByUserId(String userId);

    Optional<UserTemplate> findByUserIdAndAmountAndCategoryIdAndRecipientId(String userId,
                                                                            BigDecimal amount,
                                                                            Integer categoryId,
                                                                            String recipientId);
}
