package com.soypita.battle.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class TestResultResponse {
    private float result;
    private Long totalTestsCount;
    private Long failedTestsCount;
    private Long succeededTestsCount;
    private List<TaskFailure> failuresList;
}
