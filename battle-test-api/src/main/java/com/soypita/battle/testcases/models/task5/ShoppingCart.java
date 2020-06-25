package com.soypita.battle.testcases.models.task5;

import java.util.List;

import lombok.Data;


@Data
public class ShoppingCart {

    private int shopId;

    private boolean loyaltyCard;

    private List<ItemPosition> positions;

}
