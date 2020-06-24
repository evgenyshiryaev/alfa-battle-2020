package com.soypita.battle.testcases.models.task5;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShoppingCart {

    private int shopId;

    private boolean loyaltyCard;

    private List<ItemPosition> positions;

}
