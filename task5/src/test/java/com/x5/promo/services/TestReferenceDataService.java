package com.x5.promo.services;

import static org.assertj.core.api.Assertions.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.x5.promo.ApplicationConfiguration;
import com.x5.promo.ApplicationConfiguration.ReferenceDataProperties;
import com.x5.promo.model.Item;
import com.x5.promo.model.ItemGroup;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = TestReferenceDataService.TestConfiguration.class, webEnvironment = WebEnvironment.NONE)
@ActiveProfiles({ "logging", "TestReferenceDataService" })
@DisplayName("Caching reference data")
class TestReferenceDataService {
  @Configuration
  @EnableConfigurationProperties(ApplicationConfiguration.ReferenceDataProperties.class)
  public static class TestConfiguration {
    @Bean
    public CsvMapperFactory csvMapperFactory() {
      return new CsvMapperFactory();
    }

    @Bean
    public ReferenceDataService referenceDataService(ReferenceDataProperties properties) throws IOException {
      return new ReferenceDataService(properties, csvMapperFactory());
    }
  }

  @Autowired
  private ReferenceDataService service;

  @Test
  @DisplayName("test items")
  void testGetItem() {
    final List<Item> expectedList = List.of( //
      Helpers.itemOf("1", "item1", "3", new BigDecimal("10.00")), //
      Helpers.itemOf("2", "item2", "1", new BigDecimal("1.50")), //
      Helpers.itemOf("3", "item3", "4", new BigDecimal("2.20")), //
      Helpers.itemOf("4", "item4", "1", new BigDecimal("109.00")), //
      Helpers.itemOf("5", "item5", "2", new BigDecimal("50.00")), //
      Helpers.itemOf("6", "item6", "1", new BigDecimal("208.90")), //
      Helpers.itemOf("7", "item7", "4", new BigDecimal("65.50")), //
      Helpers.itemOf("8", "item8", "1", new BigDecimal("76.00")), //
      Helpers.itemOf("9", "item9", "3", new BigDecimal("100.20")), //
      Helpers.itemOf("10", "item10", "2", new BigDecimal("37.60")) //
    );

    for (final Item expectedItem : expectedList) {
      final String itemId = expectedItem.getId();
      final Optional<Item> actualItem = service.getItem(itemId);
      assertThat(actualItem).isNotEmpty().hasValue(expectedItem);
    }
  }

  @Test
  @DisplayName("test item groups")
  void testGetItemGroup() {
    final List<ItemGroup> expectedList = List.of( //
      Helpers.groupOf("1", "group 1"), //
      Helpers.groupOf("2", "group 2"), //
      Helpers.groupOf("3", "group 3"), //
      Helpers.groupOf("4", "group 4") //
    );

    for (final ItemGroup expectedGroup : expectedList) {
      final String groupId = expectedGroup.getId();
      final Optional<ItemGroup> actualGroup = service.getItemGroup(groupId);
      assertThat(actualGroup).isNotEmpty().hasValue(expectedGroup);
    }
  }

  @Test
  @DisplayName("test group index")
  void testGetItemsByGroup() {
    final Map<String, List<String>> expectedMap = Map.of( //
      "1", List.of("2", "4", "6", "8"), //
      "2", List.of("5", "10"), //
      "3", List.of("1", "9"), //
      "4", List.of("3", "7") //
    );

    for (final Map.Entry<String, List<String>> expectedMapping : expectedMap.entrySet()) {
      final String groupId = expectedMapping.getKey();
      final List<Item> actualItems = service.getItemsByGroup(groupId);
      assertThat(actualItems).isNotEmpty().hasSameSizeAs(expectedMapping.getValue());

      final List<String> actualItemIds = actualItems.stream() //
        .peek(item -> assertThat(item).isNotNull()) //
        .map(Item::getId) //
        .collect(Collectors.toList());

      assertThat(actualItemIds).isNotEmpty().hasSameElementsAs(expectedMapping.getValue());
    }
  }

  @Test
  @DisplayName("test group index to never return null")
  void testGetItemsByGroupShouldNotReturnNull() {
    final List<Item> actualItems = service.getItemsByGroup("nonExistinId");
    assertThat(actualItems).isNotNull().hasSize(0);
  }
}
