package ru.alfabank.alfabattle.task4.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Data
@ConfigurationProperties("elastic")
public class ElasticProperties {
    private String host;
}
