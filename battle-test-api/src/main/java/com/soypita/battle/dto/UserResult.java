package com.soypita.battle.dto;

import lombok.Data;

import java.util.Map;

@Data
public class UserResult {
    private String loginId;
    private Map<Integer, Float> testResults;
}
