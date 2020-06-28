package com.soypita.battle.testcases.models.task3;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class BranchesWithDistance extends Branches {

    private Long distance; //метры

}
