package com.alfabattle.task2.config;

import com.alfabattle.task2.entities.*;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

@Configuration
public class SerdeConfig {
    private final Jackson2ObjectMapperBuilder builder;

    public SerdeConfig(Jackson2ObjectMapperBuilder builder) {
        this.builder = builder;
        builder.visibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
    }

    @Bean
    public Serde<String> stringSerde() {
        return Serdes.String();
    }

    @Bean
    public Serde<RawPaymentInfo> rawPaymentInfoSerde() {
        final var objectMapper = builder.factory(new JsonFactory()).build();
        return Serdes.serdeFrom(new JsonSerializer<>(objectMapper), new JsonDeserializer<>(RawPaymentInfo.class));
    }

    @Bean
    public Serde<PaymentAnalyticsResult> paymentAnalyticsResultSerde() {
        final var objectMapper = builder.factory(new JsonFactory()).build();
        return Serdes.serdeFrom(new JsonSerializer<>(objectMapper), new JsonDeserializer<>(PaymentAnalyticsResult.class));
    }

    @Bean
    public Serde<PaymentGroupInfo> paymentGroupInfoSerde() {
        final var objectMapper = builder.factory(new JsonFactory()).build();
        return Serdes.serdeFrom(new JsonSerializer<>(objectMapper), new JsonDeserializer<>(PaymentGroupInfo.class));
    }

    @Bean
    public  Serde<TemplateKey> templateKeySerde() {
        final var objectMapper = builder.factory(new JsonFactory()).build();
        return Serdes.serdeFrom(new JsonSerializer<>(objectMapper), new JsonDeserializer<>(TemplateKey.class));
    }
}
