package com.soypita.battle.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.validation.constraints.NotNull;
import java.util.Map;

@Data
@ConfigurationProperties("mapping")
public class TasksProperties {
    @NotNull
    Map<Integer, String> taskNames;

    @NotNull
    Map<Integer, String> taskPorts;
}
