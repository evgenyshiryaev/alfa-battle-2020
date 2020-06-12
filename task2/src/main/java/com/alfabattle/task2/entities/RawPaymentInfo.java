package com.alfabattle.task2.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RawPaymentInfo {
    @JsonProperty("ref")
    private String ref;
    @JsonProperty("catId")
    private Integer categoryId;
    @JsonProperty("userId")
    private String userId;
    @JsonProperty("recipientId")
    private String recipientId;
    @JsonProperty("desc")
    private String purpose;
    @JsonProperty("ama")
    private BigDecimal amount;
}
