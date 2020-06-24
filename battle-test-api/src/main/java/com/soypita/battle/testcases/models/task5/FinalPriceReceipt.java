package com.soypita.battle.testcases.models.task5;

import java.math.BigDecimal;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
public class FinalPriceReceipt {

    private BigDecimal total;

    private BigDecimal discount;

    @EqualsAndHashCode.Exclude
    private List<FinalPricePosition> positions;

}
