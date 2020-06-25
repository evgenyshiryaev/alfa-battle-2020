package com.x5.promo.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.x5.promo.model.Item;
import com.x5.promo.model.ItemGroup;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CsvMapperFactory {
  private static final int BUFSIZ = 1 << 20;
  private static final Charset CHARSET = StandardCharsets.UTF_8;

  private final CsvMapper mapper;

  public CsvMapperFactory() {
    log.debug("<init>()");
    mapper = newMapper();
  }

  public <T> MappingIterator<T> newIterator(Resource source, Class<T> dataType) {
    log.debug("newIterator(source = {}, dataType = {})", source, dataType);

    Objects.requireNonNull(source, () -> "source must not be null");
    Objects.requireNonNull(dataType, () -> "dataType must not be null");

    try {
      final CsvSchema schema = newSchema(dataType);
      final BufferedReader reader = newReader(source);
      return mapper.readerFor(dataType).with(schema).readValues(reader);
    } catch (final IOException e) {
      throw new IllegalStateException("Couldn't read \"" + dataType + "\" from \"" + source + "\"", e);
    }
  }

  private <T> CsvSchema newSchema(Class<T> dataType) {
    return mapper.typedSchemaFor(dataType) //
      .withComments() //
      .withStrictHeaders(true) //
      .withColumnReordering(true) //
      .withUseHeader(true) //
      .withColumnSeparator(',') //
      .withQuoteChar('"') //
      .withEscapeChar('\"');
  }

  private BufferedReader newReader(Resource source) throws IOException {
    return new BufferedReader(new InputStreamReader(source.getInputStream(), CHARSET), BUFSIZ);
  }

  private CsvMapper newMapper() {
    return CsvMapper.builder() //
      .addMixIn(Item.class, ItemMixIn.class) //
      .addMixIn(Item.Builder.class, ItemMixIn.Builder.class) //
      .addMixIn(ItemGroup.class, ItemGroupMixIn.class) //
      .addMixIn(ItemGroup.Builder.class, ItemGroupMixIn.Builder.class) //
      .build();
  }

  @JsonDeserialize(builder = Item.Builder.class)
  public static class ItemMixIn {
    @JsonPOJOBuilder(withPrefix = "")
    public static class Builder {}
  }

  @JsonDeserialize(builder = ItemGroup.Builder.class)
  public static class ItemGroupMixIn {
    @JsonPOJOBuilder(withPrefix = "")
    public static class Builder {}
  }
}
