package com.soypita.battle.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Builder
public class RunTestRequest {
    @NotBlank
    private String loginId;

    @NotNull
    private Integer taskId;
}
