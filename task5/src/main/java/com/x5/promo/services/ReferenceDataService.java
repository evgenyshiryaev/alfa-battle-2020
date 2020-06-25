package com.x5.promo.services;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.apache.commons.lang3.tuple.Pair;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.MappingIterator;
import com.x5.promo.ApplicationConfiguration.ReferenceDataProperties;
import com.x5.promo.model.Item;
import com.x5.promo.model.ItemGroup;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ReferenceDataService {
  private final Map<String, Item> items;
  private final Map<String, ItemGroup> groups;
  private final Map<String, List<String>> itemsByGroup;

  public ReferenceDataService(ReferenceDataProperties properties, CsvMapperFactory factory) throws IOException {
    log.debug("<init>()");

    Objects.requireNonNull(properties, () -> "properties must not be null");
    Objects.requireNonNull(factory, () -> "factory must not be null");

    items = readItems(properties, factory);
    groups = readItemGroup(properties, factory);

    itemsByGroup = groupItemsByGroup(items);
  }

  private Map<String, List<String>> groupItemsByGroup(Map<String, Item> items) {
    Objects.requireNonNull(items, () -> "items must not be null");
    return items.values().stream() //
      .map(item -> Pair.of(item.getId(), item.getGroupId())) //
      .collect(Collectors.groupingBy(Pair::getValue, Collectors.mapping(Pair::getKey, Collectors.toList())));
  }

  public Optional<Item> getItem(String itemId) {
    return Optional.ofNullable(itemId).map(items::get);
  }

  public Optional<ItemGroup> getItemGroup(String groupId) {
    return Optional.ofNullable(groupId).map(groups::get);
  }

  public List<Item> getItemsByGroup(String groupId) {
    return Optional.ofNullable(groupId) //
      .map(itemsByGroup::get) //
      .map(itemIds -> itemIds.stream().map(items::get).filter(Objects::nonNull).collect(Collectors.toList())) //
      .orElseGet(List::of);
  }

  public Set<String> getItemIds() {
    return Set.copyOf(items.keySet());
  }

  public Set<String> getItemGroupIds() {
    return Set.copyOf(groups.keySet());
  }

  private Map<String, Item> readItems(ReferenceDataProperties properties, CsvMapperFactory factory) {
    return readValues(factory, properties.getItemsPath(), Item.class, Item::getId);
  }

  private Map<String, ItemGroup> readItemGroup(ReferenceDataProperties properties, CsvMapperFactory factory) {
    return readValues(factory, properties.getItemGroupsPath(), ItemGroup.class, ItemGroup::getId);
  }

  private static <K, V> Map<K, V> readValues(CsvMapperFactory factory, Resource source, Class<V> dataType,
    Function<V, K> keyMapper) {
    Objects.requireNonNull(factory, () -> "factory must not be null");
    Objects.requireNonNull(source, () -> "source must not be null");
    Objects.requireNonNull(dataType, () -> "dataType must not be null");

    final String typeName = dataType.getName();
    final Map<K, V> output = new LinkedHashMap<>();
    final MappingIterator<V> iterator = factory.newIterator(source, dataType);

    log.info("reading {} data from {}", typeName, source);
    while (iterator.hasNext()) {
      final V value = iterator.next();
      Optional.ofNullable(value).map(keyMapper).ifPresent(key -> output.put(key, value));
    }
    log.info("{} line(s) of {} data read", output.size(), typeName);

    if (log.isTraceEnabled()) {
      log.trace(output.values().stream().map(Objects::toString).collect( //
        Collectors.joining(String.format("%n    "), String.format("loaded contents:%n    "), "")));
    }

    return output;
  }
}
