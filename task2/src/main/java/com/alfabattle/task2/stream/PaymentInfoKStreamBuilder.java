package com.alfabattle.task2.stream;

import com.alfabattle.task2.config.PaymentAnalyticsProperties;
import com.alfabattle.task2.entities.*;
import com.alfabattle.task2.repositories.PaymentAnalyticRepository;
import com.alfabattle.task2.services.PaymentTemplatesService;
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

    private final Serde<TemplateKey> templateKeySerde;

    private final Serde<PaymentAnalyticsResult> paymentAnalyticsResultSerde;

    private final PaymentAnalyticRepository paymentAnalyticRepository;

    private final PaymentTemplatesService paymentTemplatesService;

    private static final Long TEMPLATE_COUNT_THRESHOLD = 3l;

    public Topology build() {
        final String rawPaymentSource = properties.getKafka().getSources().getRawPaymentSource();

        var rawInputStream = builder.stream(rawPaymentSource, consumed())
                .peek(((key, value) -> log.info("{} : {}", key, value)));

        // Collect analytic data
        rawInputStream.groupBy((k, v) -> v.getUserId(),
                Grouped.with(Serdes.String(), rawPaymentInfoSerde))
                .aggregate(() -> new PaymentAnalyticsResult("", new BigDecimal(0), new HashMap<>()),
                        (key, value, agg) -> {
                            agg.setUserId(value.getUserId());
                            var currSum = agg.getTotalSum();
                            agg.setTotalSum(currSum.add(value.getAmount()));
                            var currGroupInfo = agg.getAnalyticInfo().getOrDefault(value.getCategoryId(),
                                    new PaymentGroupInfo(BigDecimal.valueOf(Double.MAX_VALUE), BigDecimal.valueOf(Double.MIN_VALUE),
                                            BigDecimal.valueOf(0), 0));
                            var totalCount = currGroupInfo.getTotalCount() + 1;
                            var resGroupInfo = new PaymentGroupInfo(currGroupInfo.getMin().min(value.getAmount()), currGroupInfo.getMax().max(value.getAmount()),
                                    currGroupInfo.getSum().add(value.getAmount()),  totalCount);
                            agg.getAnalyticInfo().put(value.getCategoryId(), resGroupInfo);
                            return agg;
                        }, Materialized.with(Serdes.String(), paymentAnalyticsResultSerde))
                .toStream()
                .mapValues(paymentAnalyticRepository::save);

        rawInputStream.groupBy((k, v) ->
                        TemplateKey.builder()
                                .userId(v.getUserId())
                                .categoryId(v.getCategoryId())
                                .recipientId(v.getRecipientId())
                                .amount(v.getAmount())
                                .build(),
                Grouped.with(templateKeySerde, rawPaymentInfoSerde))
                .count()
                .toStream()
                .filter((k, v) -> v >= TEMPLATE_COUNT_THRESHOLD)
                .peek((key, value) -> paymentTemplatesService.createTemplate(key.getUserId(),
                        key.getRecipientId(),
                        key.getCategoryId(),
                        key.getAmount()));

        return builder.build();

    }

    private Consumed<String, RawPaymentInfo> consumed() {
        return Consumed.with(stringSerde, rawPaymentInfoSerde);
    }
}
