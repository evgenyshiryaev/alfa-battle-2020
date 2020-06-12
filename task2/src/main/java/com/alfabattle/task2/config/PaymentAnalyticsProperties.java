package com.alfabattle.task2.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties("analytics")
public class PaymentAnalyticsProperties {
    private KafkaProperties kafka;

    @Data
    public static class KafkaProperties {

        private boolean enabled;

        private KafkaSourcesProperties sources;

        private KafkaSinkProperties sinks;

        @Data
        public static class KafkaSourcesProperties {

            private String rawPaymentSource;

        }

        @Data
        public static class KafkaSinkProperties {

            private String paymentAnalyticSink;

        }

    }
}
