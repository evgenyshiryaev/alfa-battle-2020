package com.alfabattle.task2.config;

import com.alfabattle.task2.entities.PaymentAnalyticsResult;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.KStream;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.StreamsBuilderFactoryBean;

import static org.springframework.kafka.annotation.KafkaStreamsDefaultConfiguration.DEFAULT_STREAMS_BUILDER_BEAN_NAME;

@TestConfiguration
public class PaymentAnalyticTestConfiguration {
    @Bean
    StreamsBuilder streamsBuilder() {
        return new StreamsBuilder();
    }

    @Bean(DEFAULT_STREAMS_BUILDER_BEAN_NAME)
    StreamsBuilderFactoryBean streamsBuilderFactoryBean() {
        return Mockito.mock(StreamsBuilderFactoryBean.class);
    }

    @Bean
    KStream<String, PaymentAnalyticsResult> reputationRiskKStream() {
        return (KStream<String, PaymentAnalyticsResult>) Mockito.mock(KStream.class);
    }
}
