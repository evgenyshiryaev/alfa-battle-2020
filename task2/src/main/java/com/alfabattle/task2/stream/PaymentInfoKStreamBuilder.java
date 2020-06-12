package com.alfabattle.task2.stream;

import com.alfabattle.task2.config.PaymentAnalyticsProperties;
import com.alfabattle.task2.entities.PaymentAnalyticsResult;
import com.alfabattle.task2.entities.PaymentGroupInfo;
import com.alfabattle.task2.entities.RawPaymentInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.*;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.HashMap;

@Slf4j
@Component
@RequiredArgsConstructor
public class PaymentInfoKStreamBuilder {
    private final PaymentAnalyticsProperties properties;

    private final StreamsBuilder builder;

    private final Serde<String> stringSerde;

    private final Serde<RawPaymentInfo> rawPaymentInfoSerde;

    private final Serde<PaymentAnalyticsResult> paymentAnalyticsResultSerde;

    public Topology build() {
        final String rawPaymentSource = properties.getKafka().getSources().getRawPaymentSource();
        final String outputPaymentInfoSink = properties.getKafka().getSinks().getPaymentAnalyticSink();

        builder.stream(rawPaymentSource, consumed())
                .groupBy((k, v) -> v.getUserId(),
                        Grouped.with(Serdes.String(), rawPaymentInfoSerde))
                .aggregate(() -> new PaymentAnalyticsResult(new HashMap<>(), new BigDecimal(0), ""),
                        (key, value, agg) -> {
                            agg.setUserId(value.getUserId());
                            var currSum = agg.getTotalSum();
                            agg.setTotalSum(currSum.add(value.getAmount()));
                            var currGroupInfo = agg.getAnalyticInfo().getOrDefault(value.getCategoryId(),
                                    new PaymentGroupInfo(BigDecimal.valueOf(Double.MAX_VALUE), BigDecimal.valueOf(Double.MIN_VALUE),
                                            BigDecimal.valueOf(0)));
                            var resGroupInfo = new PaymentGroupInfo(currGroupInfo.getMin().min(value.getAmount()), currGroupInfo.getMax().max(value.getAmount()),
                                    currGroupInfo.getSum().add(value.getAmount()));
                            agg.getAnalyticInfo().put(value.getCategoryId(), resGroupInfo);
                            return agg;
                        }, Materialized.with(Serdes.String(), paymentAnalyticsResultSerde))
                .toStream()
                .through(outputPaymentInfoSink, produced());
        return builder.build();

    }

    private Consumed<String, RawPaymentInfo> consumed() {
        return Consumed.with(stringSerde, rawPaymentInfoSerde);
    }

    private Produced<String, PaymentAnalyticsResult> produced() {
        return Produced.with(stringSerde, paymentAnalyticsResultSerde);
    }
}
