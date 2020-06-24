package com.soypita.battle.testcases.models.task5;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;


@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PromoMatrix {
    private List<LoyaltyCardRule> loyaltyCardRules;

    private List<ItemCountRule> itemCountRules;

    private List<ItemGroupRule> itemGroupRules;

}
