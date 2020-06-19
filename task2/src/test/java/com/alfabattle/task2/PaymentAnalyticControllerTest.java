package com.alfabattle.task2;

import com.alfabattle.task2.config.PaymentAnalyticsProperties;
import com.alfabattle.task2.entities.PaymentAnalyticsResult;
import com.alfabattle.task2.entities.RawPaymentInfo;
import com.alfabattle.task2.entities.UserPaymentStats;
import com.alfabattle.task2.entities.UserTemplate;
import lombok.SneakyThrows;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.kafka.streams.TestInputTopic;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.TopologyTestDriver;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.config.KafkaStreamsConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.math.BigDecimal;
import java.util.List;
import java.util.Properties;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class PaymentAnalyticControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    private PaymentAnalyticsProperties properties;

    @Autowired
    KafkaProperties kafkaProperties;

    @Autowired
    KafkaStreamsConfiguration kafkaStreamsConfiguration;

    @Autowired
    private Serde<RawPaymentInfo> rawPaymentInfoSerde;

    @Autowired
    private Topology topology;

    @Autowired
    private ObjectMapper objectMapper;

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
    @SneakyThrows
    public void successfullyGetAllAnalyticInfo() {
        // given
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

        // when
        var result = mockMvc.perform(get("/analytic"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsByteArray();

        // then
        var userResults = objectMapper.readValue(result, new TypeReference<List<PaymentAnalyticsResult>>() {
        });
        Assert.assertNotNull(userResults);
        Assert.assertEquals(2, userResults.size());
    }

    @Test
    @SneakyThrows
    public void getEmptyResponseWhenNoDataProcessed() {
        // when
        var result = mockMvc.perform(get("/analytic"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsByteArray();

        // then
        var userResults = objectMapper.readValue(result, new TypeReference<List<PaymentAnalyticsResult>>() {
        });
        Assert.assertNotNull(userResults);
        Assert.assertTrue(userResults.isEmpty());
    }

    @Test
    @SneakyThrows
    public void getUserPaymentAnalyticTest() {
        // given
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
        inputTopic.pipeInput("REF2", rawPayment2);

        // when
        var result = mockMvc.perform(get(String.format("/analytic/%s", "USER_1")))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsByteArray();

        // then
        var userResult = objectMapper.readValue(result, PaymentAnalyticsResult.class);
        Assert.assertNotNull(userResult);
        Assert.assertEquals(new BigDecimal(10), userResult.getTotalSum());
        Assert.assertEquals(1, userResult.getAnalyticInfo().size());
        Assert.assertNotNull(userResult.getAnalyticInfo().getOrDefault(1, null));
    }

    @Test
    @SneakyThrows
    public void getNotFoundStatusForUnexpectedUser() {
        // given
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
        inputTopic.pipeInput("REF2", rawPayment2);

        // when
        mockMvc.perform(get(String.format("/analytic/%s", "User3")))
                .andExpect(status().isNotFound());
    }

    @Test
    @SneakyThrows
    public void getStatisticForUser() {
        // given
        var rawPayment = new RawPaymentInfo();
        rawPayment.setUserId("USER_1");
        rawPayment.setRecipientId("USER_2");
        rawPayment.setCategoryId(1);
        rawPayment.setRef("REF1");
        rawPayment.setAmount(BigDecimal.valueOf(10, 0));

        var rawPayment2 = new RawPaymentInfo();
        rawPayment2.setUserId("USER_1");
        rawPayment2.setRecipientId("USER_2");
        rawPayment2.setCategoryId(2);
        rawPayment2.setRef("REF2");
        rawPayment2.setAmount(BigDecimal.valueOf(5, 0));

        var rawPayment3 = new RawPaymentInfo();
        rawPayment3.setUserId("USER_1");
        rawPayment3.setRecipientId("USER_2");
        rawPayment3.setCategoryId(1);
        rawPayment3.setRef("REF3");
        rawPayment3.setAmount(BigDecimal.valueOf(3, 0));

        var rawPayment4 = new RawPaymentInfo();
        rawPayment4.setUserId("USER_1");
        rawPayment4.setRecipientId("USER_2");
        rawPayment4.setCategoryId(1);
        rawPayment4.setRef("REF4");
        rawPayment4.setAmount(BigDecimal.valueOf(20, 0));

        var rawPayment5 = new RawPaymentInfo();
        rawPayment5.setUserId("USER_1");
        rawPayment5.setRecipientId("USER_2");
        rawPayment5.setCategoryId(3);
        rawPayment5.setRef("REF4");
        rawPayment5.setAmount(BigDecimal.valueOf(15, 0));

        var rawPayment6 = new RawPaymentInfo();
        rawPayment6.setUserId("USER_1");
        rawPayment6.setRecipientId("USER_2");
        rawPayment6.setCategoryId(2);
        rawPayment6.setRef("REF2");
        rawPayment6.setAmount(BigDecimal.valueOf(1, 0));


        inputTopic.pipeInput("REF1", rawPayment);
        inputTopic.pipeInput("REF2", rawPayment2);
        inputTopic.pipeInput("REF3", rawPayment3);
        inputTopic.pipeInput("REF4", rawPayment4);
        inputTopic.pipeInput("REF5", rawPayment5);
        inputTopic.pipeInput("REF6", rawPayment6);

        // when
        var result = mockMvc.perform(get(String.format("/analytic/%s/stats", "USER_1")))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        // then
        var userResult = objectMapper.readValue(result, UserPaymentStats.class);
        Assert.assertNotNull(userResult);
        Assert.assertTrue(userResult.getOftenCategoryId() == 1);
        Assert.assertTrue(userResult.getRareCategoryId() == 3);
        Assert.assertTrue(userResult.getMaxAmountCategoryId() == 1);
        Assert.assertTrue(userResult.getMinAmountCategoryId() == 2);
    }

    @Test
    @SneakyThrows
    public void returnNotFoundForUnexpectedUserStats() {
        // when
        mockMvc.perform(get(String.format("/analytic/%s/stats", "USER3")))
                .andExpect(status().isNotFound());
    }

    @Test
    @SneakyThrows
    public void getUserTemplates() {
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

        // when
        var result = mockMvc.perform(get(String.format("/analytic/%s/templates", "USER_1")))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        var templates = objectMapper.readValue(result, new TypeReference<List<UserTemplate>>() {
        });
        Assert.assertNotNull(templates);
        Assert.assertFalse(templates.isEmpty());
        Assert.assertEquals(1, templates.size());
    }

    @Test
    @SneakyThrows
    public void getEmptyTemplateResponse() {
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

        inputTopic.pipeInput("REF5", rawPayment2);
        inputTopic.pipeInput("REF6", rawPayment2);

        // when
        var result = mockMvc.perform(get(String.format("/analytic/%s/templates", "USER_1")))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        var templates = objectMapper.readValue(result, new TypeReference<List<UserTemplate>>() {
        });
        Assert.assertNotNull(templates);
        Assert.assertTrue(templates.isEmpty());
    }
}
