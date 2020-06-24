package com.soypita.battle.testcases.models.task5;

import lombok.Data;


@Data
public class ItemCountRule {

    private int shopId;

    private String itemId;

    private int triggerQuantity;

    private int bonusQuantity;

}
