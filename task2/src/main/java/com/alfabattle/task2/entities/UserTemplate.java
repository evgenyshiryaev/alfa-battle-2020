package com.alfabattle.task2.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Data
@Builder
@Document("user-templates")
@NoArgsConstructor
@AllArgsConstructor
public class UserTemplate {
    @Id
    @JsonIgnore
    private String id;

    @JsonIgnore
    private String userId;

    private String recipientId;

    private Integer categoryId;

    private BigDecimal amount;
}
