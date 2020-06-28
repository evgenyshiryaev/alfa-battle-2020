package ru.alfabank.alfabattle.task1;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;


@Data
@Component
@ConfigurationProperties
public class Task1Properties {

    private String webSocketPath = "ws://127.0.0.1:8100";

}
