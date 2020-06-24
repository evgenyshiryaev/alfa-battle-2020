package com.soypita.battle.testcases.models.task5;

import java.math.BigDecimal;

import lombok.Data;


@Data
public class FinalPricePosition {

    private String id;

    private String name;

    private BigDecimal price;

    private BigDecimal regularPrice;

}
