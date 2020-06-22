package com.alfabattle.task2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.kafka.annotation.EnableKafkaStreams;

@EnableKafkaStreams
@EnableMongoRepositories
@SpringBootApplication
@EnableConfigurationProperties
public class Task2Application {
	public static void main(String[] args) {
		SpringApplication.run(Task2Application.class, args);
	}

}
