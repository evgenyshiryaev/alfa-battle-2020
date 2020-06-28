package com.alfabattle.task2;

import com.alfabattle.task2.config.PaymentAnalyticsProperties;
import com.alfabattle.task2.entities.RawPaymentInfo;
import com.alfabattle.task2.repositories.PaymentAnalyticRepository;
import com.alfabattle.task2.repositories.UserTemplateRepository;
import org.apache.kafka.common.serialization.Serde;
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
    private PaymentAnalyticRepository paymentAnalyticRepository;

    @Autowired
    private UserTemplateRepository userTemplateRepository;

    @Autowired
    private Topology topology;

    private TopologyTestDriver testDriver;

    private TestInputTopic<String, RawPaymentInfo> inputTopic;

    @Before
    public void setup() {
        String rawPaymentSource = properties.getKafka().getSources().getRawPaymentSource();

        var properties = new Properties();
        properties.putAll(kafkaProperties.buildStreamsProperties());

        testDriver = new TopologyTestDriver(topology, kafkaStreamsConfiguration.asProperties());

        inputTopic = testDriver.createInputTopic(rawPaymentSource, new StringSerializer(), rawPaymentInfoSerde.serializer());
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
        rawPayment.setRecipientId("USER_2");
        rawPayment.setCategoryId(1);
        rawPayment.setRef("REF1");
        rawPayment.setAmount(BigDecimal.valueOf(10, 0));

        var rawPayment2 = new RawPaymentInfo();
        rawPayment2.setUserId("USER_1");
        rawPayment2.setRecipientId("USER_2");
        rawPayment2.setCategoryId(1);
        rawPayment2.setRef("REF2");
        rawPayment2.setAmount(BigDecimal.valueOf(5, 0));

        var rawPayment3 = new RawPaymentInfo();
        rawPayment3.setUserId("USER_1");
        rawPayment3.setRecipientId("USER_2");
        rawPayment3.setCategoryId(2);
        rawPayment3.setRef("REF3");
        rawPayment3.setAmount(BigDecimal.valueOf(5, 0));

        var rawPayment4 = new RawPaymentInfo();
        rawPayment4.setUserId("USER_2");
        rawPayment4.setRecipientId("USER_1");
        rawPayment4.setCategoryId(1);
        rawPayment4.setRef("REF4");
        rawPayment4.setAmount(BigDecimal.valueOf(5, 0));

        inputTopic.pipeInput("REF1", rawPayment);
        inputTopic.pipeInput("REF2", rawPayment2);
        inputTopic.pipeInput("REF3", rawPayment3);
        inputTopic.pipeInput("REF4", rawPayment4);
        inputTopic.pipeInput("REF5", rawPayment);
        inputTopic.pipeInput("REF6", rawPayment);

        // then
        var analytic = paymentAnalyticRepository.findAll();
        Assert.assertNotNull(analytic);
        Assert.assertFalse(analytic.isEmpty());
        Assert.assertEquals(2, analytic.size());

        var templates = userTemplateRepository.findAll();
        Assert.assertNotNull(templates);
        Assert.assertFalse(templates.isEmpty());
        Assert.assertEquals(1, templates.size());
    }

    @Test
    public void paymentTemplatesAggregationTest() {
        var rawPayment = new RawPaymentInfo();
        rawPayment.setUserId("USER_1");
        rawPayment.setRecipientId("USER_2");
        rawPayment.setCategoryId(1);
        rawPayment.setRef("REF1");
        rawPayment.setAmount(BigDecimal.valueOf(10, 0));

        var rawPayment2 = new RawPaymentInfo();
        rawPayment2.setUserId("USER_2");
        rawPayment2.setRecipientId("USER_1");
        rawPayment2.setCategoryId(1);
        rawPayment2.setRef("REF4");
        rawPayment2.setAmount(BigDecimal.valueOf(5, 0));

        inputTopic.pipeInput("REF1", rawPayment);
        inputTopic.pipeInput("REF2", rawPayment);
        inputTopic.pipeInput("REF3", rawPayment);
        inputTopic.pipeInput("REF4", rawPayment);

        inputTopic.pipeInput("REF5", rawPayment2);
        inputTopic.pipeInput("REF6", rawPayment2);
        inputTopic.pipeInput("REF7", rawPayment2);

        var templates = userTemplateRepository.findAll();
        Assert.assertNotNull(templates);
        Assert.assertFalse(templates.isEmpty());
        Assert.assertEquals(2, templates.size());
    }
}
