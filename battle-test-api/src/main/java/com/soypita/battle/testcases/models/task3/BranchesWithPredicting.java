package com.soypita.battle.testcases.models.task3;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
public class BranchesWithPredicting extends Branches {

    private Long id;

    private String title;

    private Double lon;

    private Double lat;

    private String address;

    private Integer dayOfWeek;

    private Integer hourOfDay;

    private Long predicting;

}
