package com.soypita.battle.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class TaskFailure {
    private String taskName;
    @JsonIgnore
    private String failureMessage;
}
