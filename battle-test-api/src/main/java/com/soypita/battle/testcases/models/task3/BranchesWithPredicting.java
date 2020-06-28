package com.soypita.battle.testcases.models.task3;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class BranchesWithPredicting extends Branches {

    private Integer dayOfWeek;

    private Integer hourOfDay;

    private Long predicting;

}
