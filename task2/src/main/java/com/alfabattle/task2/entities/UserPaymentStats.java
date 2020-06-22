package com.alfabattle.task2.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserPaymentStats {
    private Integer oftenCategoryId;
    private Integer rareCategoryId;
    private Integer maxAmountCategoryId;
    private Integer minAmountCategoryId;
}
