package com.soypita.battle;

import com.soypita.battle.config.TasksProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
@EnableConfigurationProperties(value = {TasksProperties.class})
public class BattleTestApplication {
    public static void main(String[] args) {
        SpringApplication.run(BattleTestApplication.class, args);
    }
}
