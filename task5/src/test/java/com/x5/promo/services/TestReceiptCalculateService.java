package com.x5.promo.services;

import static org.assertj.core.api.Assertions.*;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.x5.promo.ApplicationConfiguration;
import com.x5.promo.model.FinalPriceReceipt;
import com.x5.promo.model.PromoMatrix;
import com.x5.promo.model.ShoppingCart;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = TestReceiptCalculateService.TestConfiguration.class, webEnvironment = WebEnvironment.NONE)
@ActiveProfiles({ "logging" })
@DisplayName("Receipt calculation cases")
class TestReceiptCalculateService {
  @Configuration
  @ComponentScan(basePackages = "com.x5.promo.services", excludeFilters = @ComponentScan.Filter(type = FilterType.REGEX, pattern = "com.x5.[\\w+.]+Test\\w+.Test\\w+"))
  @EnableConfigurationProperties(ApplicationConfiguration.ReferenceDataProperties.class)
  public static class TestConfiguration {
    @Bean
    public ObjectMapper objectMapper() {
      final ObjectMapper mapper = new ObjectMapper() //
        .setSerializationInclusion(JsonInclude.Include.NON_NULL) //
        .setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
      return mapper;
    }

    @Bean
    public ObjectWriter prettyPrinter(final ObjectMapper mapper) {
      return mapper.writerWithDefaultPrettyPrinter();
    }

    @Bean
    public Function<String, Resource> sourceFactory() {
      return name -> new ClassPathResource("calculate/" + name + ".json");
    }
  }

  private static final int SHOP_ID = 10;

  @Autowired
  private ReferenceDataService referenceService;
  @Autowired
  private PromoRuleService promoService;
  @Autowired
  private ReceiptCalculateService calculateService;
  @Autowired
  private ObjectMapper objectMapper;
  @Autowired
  private ObjectWriter objectWriter;
  @Autowired
  private Function<String, Resource> sourceFactory;

  <T> String jsonOf(T value) {
    try {
      return objectWriter.writeValueAsString(value);
    } catch (final JsonProcessingException e) {
      return e.toString();
    }
  }

  private void doTest(String caseNum, ShoppingCart cart, FinalPriceReceipt expected) {
    log.info("case[{}]: cart = {}", caseNum, jsonOf(cart));

    final FinalPriceReceipt actual = calculateService.calculate(cart);
    log.info("case[{}]: expected = {}, actual = {}", caseNum, jsonOf(expected), jsonOf(actual));
    actual.setPositions(null); // reset details

    assertThat(actual).isEqualTo(expected);
  }

  @Test
  @DisplayName("Smoke test")
  void testCalculateReceiptSmoke() throws JsonProcessingException {
    final String itemId = "1122"; // 1122, конфеты Нежность, FR0606005, 206.6600
    assertThat(referenceService.getItem(itemId)).isPresent();

    final PromoMatrix matrix = Helpers.matrixOf(null, List.of(Helpers.ruleOf(null, "1122", 2, 1)), null);
    log.info("matrix: {}", jsonOf(matrix));
    promoService.setMatrix(matrix);

    final ShoppingCart cart = Helpers.cartOf(15, null, Helpers.positionOf("1122", 3));
    log.info("cart: {}", jsonOf(cart));

    final FinalPriceReceipt result = calculateService.calculate(cart);

    final BigDecimal expectedTotal = BigDecimal.valueOf(206.66 * 2).setScale(2, RoundingMode.HALF_EVEN);
    final BigDecimal actualTotal = Optional.ofNullable(result.getTotal()) //
      .map(value -> value.setScale(2, RoundingMode.HALF_EVEN)) //
      .orElse(null);
    log.info("expectedTotal = {}, actualTotal = {}", expectedTotal, actualTotal);

    assertThat(actualTotal).isNotNull().isEqualTo(expectedTotal);
  }

  @Nested
  @DisplayName("Object cases")
  class TestObjectCases {
    @BeforeEach
    void configureMatrix() {
      final PromoMatrix matrix = Helpers.matrixOf( //
        List.of( //
          Helpers.ruleOf(-1, 0.05) //
        ), //
        List.of( //
          Helpers.ruleOf(SHOP_ID, "3688443", 2, 1), //
          Helpers.ruleOf(-1, "3432166", 1, 1), //
          Helpers.ruleOf(SHOP_ID, "3432166", 3, 1)), //
        List.of( //
          Helpers.ruleOf(SHOP_ID, "FD0611002", 0.1), //
          Helpers.ruleOf(-1, "NF1301002", 0.05), //
          Helpers.ruleOf(SHOP_ID, "3432166", 0.02) //
        ));

      log.info("matrix: {}", jsonOf(matrix));
      promoService.setMatrix(matrix);
    }

    @Test
    @DisplayName("Case 1")
    void testCase1() {
      doTest("1", //
        Helpers.cartOf(SHOP_ID, false, //
          Helpers.positionOf("3416444", 1) // 3416444, СЕВЗАПУГОЛЬ Мангал сбор.+6шамп., NF1202004, 269.99
        ), //
        Helpers.receiptOf("269.99", "0.00"));
    }

    @Test
    @DisplayName("Case 2")
    void testCase2() {
      doTest("2", //
        Helpers.cartOf(SHOP_ID, false, //
          Helpers.positionOf("3501530", 2), // 3501530, ДЕС.КОР.Мозаика968, NF0502012,129.99
          Helpers.positionOf("3670263", 1), // 3670263, ПРАЗД.Шок.мол.с дроб.ор/из.90г, FD0611002, 94.99
          Helpers.positionOf("3650564", 1) // 3650564, СПАРТАК Шок.гор.элитный 72% 90г, FD0611002, 66.90
        ), //
        Helpers.receiptOf("405.68", "16.19"));
    }

    @Test
    @DisplayName("Case 3")
    void testCase3() {
      doTest("3", //
        Helpers.cartOf(SHOP_ID, true, //
          Helpers.positionOf("3683968", 2) // 3683968, SANBON.Вермиш.из бел.риса 250г, FD0211003, 114.90
        ), //
        Helpers.receiptOf("218.31", "11.49"));
    }

    @Test
    @DisplayName("Case 4")
    void testCase4() {
      doTest("4", //
        Helpers.cartOf(SHOP_ID, true, //
          Helpers.positionOf("3478296", 1), // 3478296, С.ПУДОВЪ Смесь БОРОДИН.ХЛ.500г, FD0223007, 77.90
          Helpers.positionOf("3501221", 2), // 3501221, МАРК.ПЕР.Изд.мак.РОЖКИ гр.А400г, FD0211001, 39.90
          Helpers.positionOf("39774", 1), // 39774 ,Пахлава БАКИНСКАЯ 350г, FD0602001, 88.99
          Helpers.positionOf("78004858", 1), // 78004858, ТЕНД.Ряпушка н/р охл.1кг, FR1003002, 198.99
          Helpers.positionOf("3274609", 2), // 3274609, FL.ALP.Чай ОРГ.АЛ.ВЕЧ 4м.тр.20г, FD0402003, 314.99
          Helpers.positionOf("3688443", 5), // 3688443, MEL.Изд.мак.ЭНИМАЛС 500г, FD0211001, 153.99
          Helpers.positionOf("3407647", 1), // 3407647, ВК.ТРАД.Соль ГРУЗИН.АРОМ.180г, FD0219002, 37.99
          Helpers.positionOf("3621435", 2) // 3621435, ВЕРЕС Икра грибная ст/б 500г, FD0205003, 165.00
        ), //
        Helpers.receiptOf("1987.42", "226.18"));
    }

    @Test
    @DisplayName("Case 5")
    void testCase5() {
      doTest("5", //
        Helpers.cartOf(SHOP_ID, false, //
          Helpers.positionOf("3432166", 1), // 3432166, ЛУК.Жид.АНТИФ.G11 Green нез.1кг, NF0102001, 141.99
          Helpers.positionOf("3695762", 1) // 3695762, ГАВР.Сем.Ипомея Виолетта 0.5г, NF1301002, 12.90
        ), //
        Helpers.receiptOf("154.89", "0.00"));
    }
  }

  private <T> T readValue(String resourceName, Class<T> type) throws IOException {
    log.debug("readValue(resourceName = \"{}\", type = \"{}\")", resourceName, type.getName());

    final Resource resource = sourceFactory.apply(resourceName);
    try (InputStream stream = resource.getInputStream()) {
      return objectMapper.readValue(stream, type);
    }
  }

  @Nested
  @DisplayName("JSON file cases")
  class TestJsonFileCases {
    @BeforeEach
    void configureMatrix() throws IOException {
      final PromoMatrix matrix = readValue("matrix", PromoMatrix.class);
      log.info("matrix: {}", jsonOf(matrix));
      promoService.setMatrix(matrix);
    }

    private void doTest(String caseNum) throws IOException {
      final ShoppingCart cart = readValue(caseNum + "/cart", ShoppingCart.class);
      final FinalPriceReceipt receipt = readValue(caseNum + "/receipt", FinalPriceReceipt.class);
      receipt.setPositions(null);
      TestReceiptCalculateService.this.doTest(caseNum, cart, receipt);
    }

    @Test
    @DisplayName("Case 1")
    void testCase1() throws IOException {
      doTest("case1");
    }

    @Test
    @DisplayName("Case 2")
    void testCase2() throws IOException {
      doTest("case2");
    }

    @Test
    @DisplayName("Case 3")
    void testCase3() throws IOException {
      doTest("case3");
    }

    @Test
    @DisplayName("Case 4")
    void testCase4() throws IOException {
      doTest("case4");
    }

    @Test
    @DisplayName("Case 5")
    void testCase5() throws IOException {
      doTest("case5");
    }
  }
}