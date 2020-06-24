package com.soypita.battle.testcases.models.task5;

import java.math.BigDecimal;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FinalPriceReceipt {

    private BigDecimal total;

    private BigDecimal discount;

    private List<FinalPricePosition> positions;

}
