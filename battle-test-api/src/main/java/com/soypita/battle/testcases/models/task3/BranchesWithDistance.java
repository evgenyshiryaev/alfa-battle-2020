package com.soypita.battle.testcases.models.task3;
import lombok.Data;

@Data
public class BranchesWithDistance extends Branches {
    private Long id;

    private String title;

    private Double lon;

    private Double lat;

    private String address;

    private Long distance; //метры


}
