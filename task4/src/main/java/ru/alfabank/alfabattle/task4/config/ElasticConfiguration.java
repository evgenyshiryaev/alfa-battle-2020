package ru.alfabank.alfabattle.task4.config;

import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;

@Configuration
public class ElasticConfiguration {
    @Bean
    public ClientConfiguration clientConfiguration(ElasticProperties properties) {
        return ClientConfiguration.builder().connectedTo(properties.getHost()).build();
    }

    @Bean
    public RestHighLevelClient restHighLevelClient(ClientConfiguration clientConfiguration) {
        return RestClients.create(clientConfiguration).rest();
    }
}
