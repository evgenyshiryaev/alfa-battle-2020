package com.alfabattle.task2.config;


import com.alfabattle.task2.stream.PaymentInfoKStreamBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.streams.Topology;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.KafkaStreamsConfiguration;
import org.springframework.kafka.config.StreamsBuilderFactoryBean;
import org.springframework.kafka.core.CleanupConfig;


import java.util.Map;

import static org.springframework.kafka.annotation.KafkaStreamsDefaultConfiguration.DEFAULT_STREAMS_BUILDER_BEAN_NAME;
import static org.springframework.kafka.annotation.KafkaStreamsDefaultConfiguration.DEFAULT_STREAMS_CONFIG_BEAN_NAME;


@Slf4j
@Configuration
@RequiredArgsConstructor
public class KafkaConfig {

    @Bean(DEFAULT_STREAMS_CONFIG_BEAN_NAME)
    public KafkaStreamsConfiguration defaultKafkaStreamsConfig(KafkaProperties properties) {
        Map<String, Object> streamsProperties = properties.buildStreamsProperties();
        log.info("Kafka streams properties: {}", streamsProperties);
        return new KafkaStreamsConfiguration(streamsProperties);
    }

    @Bean(DEFAULT_STREAMS_BUILDER_BEAN_NAME)
    public StreamsBuilderFactoryBean streamsBuilderFactoryBean(
            @Qualifier(DEFAULT_STREAMS_CONFIG_BEAN_NAME) KafkaStreamsConfiguration streamsConfiguration) {
        return new StreamsBuilderFactoryBean(streamsConfiguration, new CleanupConfig(true, true));
    }

    @Bean("paymentAnalyticsResultKStream")
    public Topology paymentAnalyticsResultKStream(PaymentInfoKStreamBuilder builder) {
        return builder.build();
    }
}
