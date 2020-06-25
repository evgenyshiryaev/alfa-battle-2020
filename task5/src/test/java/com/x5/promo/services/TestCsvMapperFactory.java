package com.x5.promo.services;

import static org.assertj.core.api.Assertions.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.fasterxml.jackson.databind.MappingIterator;
import com.x5.promo.model.Item;
import com.x5.promo.model.ItemGroup;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = TestCsvMapperFactory.TestConfiguration.class, webEnvironment = WebEnvironment.NONE)
@ActiveProfiles({ "logging", "TestCsvMapperFactory" })
@DisplayName("CSV mapping reader")
class TestCsvMapperFactory {
  @Configuration
  public static class TestConfiguration {
    @Bean
    public CsvMapperFactory csvMapperFactory() {
      return new CsvMapperFactory();
    }

    @Bean
    public Function<String, Resource> sourceFactory() {
      return name -> new ClassPathResource("reference/" + name + "-TestCsvMapperFactory.csv");
    }
  }

  @Autowired
  private CsvMapperFactory mapperFactory;
  @Autowired
  private Function<String, Resource> sourceFactory;

  @Nested
  @DisplayName("Exceptions & validation")
  class TestExceptions {
    @Test
    @DisplayName("... 'source' argument validation")
    void shouldThrowIfSourceIsNull() {
      assertThatThrownBy(() -> mapperFactory.newIterator(null, String.class)) //
        .isInstanceOf(NullPointerException.class) //
        .hasMessageContaining("must not be null");
    }

    @Test
    @DisplayName("... 'dataType' argument validation")
    void shouldThrowIfDataTypeIsNull() {
      final Resource source = sourceFactory.apply("groups");
      assertThatThrownBy(() -> mapperFactory.newIterator(source, null)) //
        .isInstanceOf(NullPointerException.class) //
        .hasMessageContaining("must not be null");
    }
  }

  @Nested
  @DisplayName("Read data")
  class TestReading {

    @Test
    @DisplayName("... read item groups")
    void readItemGroups() {
      final Resource source = sourceFactory.apply("groups");
      final List<ItemGroup> expected = List.of( //
        Helpers.groupOf("1", "A"), //
        Helpers.groupOf("2", "B"), //
        Helpers.groupOf("3", "C"), //
        Helpers.groupOf("4", "D") //
      );

      final MappingIterator<ItemGroup> iterator = mapperFactory.newIterator(source, ItemGroup.class);
      assertThat(iterator).isNotNull().hasNext();

      final List<ItemGroup> actual = StreamSupport.stream( //
        Spliterators.spliteratorUnknownSize(iterator, Spliterator.ORDERED), false) //
        .collect(Collectors.toList());
      assertThat(actual).hasSize(4).isEqualTo(expected);
    }

    @Test
    @DisplayName("... read items")
    void readItems() {
      final Resource source = sourceFactory.apply("items");
      final List<Item> expected = List.of( //
        Helpers.itemOf("1", "A", "0", new BigDecimal("10.00")), //
        Helpers.itemOf("2", "B", "0", new BigDecimal("20.00")), //
        Helpers.itemOf("3", "C", "1", new BigDecimal("30.00")), //
        Helpers.itemOf("4", "D", "1", new BigDecimal("40.00")), //
        Helpers.itemOf("5", "E", "2", new BigDecimal("50.00")), //
        Helpers.itemOf("6", "F", "2", new BigDecimal("60.00")) //
      );

      final MappingIterator<Item> iterator = mapperFactory.newIterator(source, Item.class);
      assertThat(iterator).isNotNull().hasNext();

      final List<Item> actual = StreamSupport.stream( //
        Spliterators.spliteratorUnknownSize(iterator, Spliterator.ORDERED), false) //
        .collect(Collectors.toList());
      assertThat(actual).hasSize(6).isEqualTo(expected);
    }
  }
}
