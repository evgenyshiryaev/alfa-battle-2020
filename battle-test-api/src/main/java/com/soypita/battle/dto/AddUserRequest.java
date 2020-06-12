package com.soypita.battle.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class AddUserRequest {
    @NotNull
    private String loginId;

    @NotNull
    private String host;
}
