package com.alfabattle.task2;

import com.alfabattle.task2.config.PaymentAnalyticsProperties;
import com.alfabattle.task2.entities.PaymentAnalyticsResult;
import com.alfabattle.task2.entities.RawPaymentInfo;
import com.alfabattle.task2.stream.PaymentInfoKStreamBuilder;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.kafka.streams.*;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.config.KafkaStreamsConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.Properties;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class PaymentInfoKStreamBuilderTest {
    @Autowired
    private PaymentAnalyticsProperties properties;

    @Autowired
    KafkaProperties kafkaProperties;

    @Autowired
    KafkaStreamsConfiguration kafkaStreamsConfiguration;

    @Autowired
    private Serde<RawPaymentInfo> rawPaymentInfoSerde;

    @Autowired
    private Serde<PaymentAnalyticsResult> paymentAnalyticsResultSerde;

    @Autowired
    private Topology topology;

    private TopologyTestDriver testDriver;

    private TestInputTopic<String, RawPaymentInfo> inputTopic;
    private TestOutputTopic<String, PaymentAnalyticsResult> outputTopic;

    @Before
    public void setup() {
        String rawPaymentSource = properties.getKafka().getSources().getRawPaymentSource();
        String outputPaymentInfoSink = properties.getKafka().getSinks().getPaymentAnalyticSink();

        var properties = new Properties();
        properties.putAll(kafkaProperties.buildStreamsProperties());

        testDriver = new TopologyTestDriver(topology, kafkaStreamsConfiguration.asProperties());

        inputTopic = testDriver.createInputTopic(rawPaymentSource, new StringSerializer(), rawPaymentInfoSerde.serializer());
        outputTopic = testDriver.createOutputTopic(outputPaymentInfoSink, new StringDeserializer(), paymentAnalyticsResultSerde.deserializer());
    }

    @After
    public void cleanup() {
        if (testDriver != null) {
            testDriver.close();
        }
    }

    @Test
    public void RawPaymentsSuccessProcessTest() {
        var rawPayment = new RawPaymentInfo();
        rawPayment.setUserId("USER_1");
        rawPayment.setCategoryId(1);
        rawPayment.setRef("REF1");
        rawPayment.setAmount(BigDecimal.valueOf(10, 0));

        var rawPayment2 = new RawPaymentInfo();
        rawPayment2.setUserId("USER_1");
        rawPayment2.setCategoryId(1);
        rawPayment2.setRef("REF2");
        rawPayment2.setAmount(BigDecimal.valueOf(5, 0));

        var rawPayment3 = new RawPaymentInfo();
        rawPayment3.setUserId("USER_1");
        rawPayment3.setCategoryId(2);
        rawPayment3.setRef("REF3");
        rawPayment3.setAmount(BigDecimal.valueOf(5, 0));

        var rawPayment4 = new RawPaymentInfo();
        rawPayment4.setUserId("USER_2");
        rawPayment4.setCategoryId(1);
        rawPayment4.setRef("REF4");
        rawPayment4.setAmount(BigDecimal.valueOf(5, 0));

        inputTopic.pipeInput("REF1", rawPayment);
        inputTopic.pipeInput("REF2", rawPayment2);
        inputTopic.pipeInput("REF3", rawPayment3);
        inputTopic.pipeInput("REF4", rawPayment4);
        inputTopic.pipeInput("REF5", rawPayment);


        // then
        var res = outputTopic.readValuesToList();

        System.out.println(res);
    }
}
