package com.x5.promo.services;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.springframework.stereotype.Service;

import com.x5.promo.model.PromoMatrix;
import com.x5.promo.model.PromoRule;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PromoRuleService {
  private final AtomicReference<Map<Integer, PromoMatrix>> table;

  public PromoRuleService() {
    table = new AtomicReference<>(Map.of());
  }

  public PromoMatrix getMatrix() {
    return flattenMatrix(table.get());
  }

  public void setMatrix(PromoMatrix matrix) {
    matrix = sanitizeMatrix(matrix);
    this.table.set(groupMatrixByStore(matrix));
  }

  public Optional<PromoMatrix> getRetailMatrix() {
    return getShopMatrix(PromoRule.RETAIL);
  }

  public Optional<PromoMatrix> getShopMatrix(Integer shopId) {
    final Map<Integer, PromoMatrix> table = this.table.get();
    return Optional.ofNullable(shopId).map(table::get);
  }

  private static PromoMatrix sanitizeMatrix(PromoMatrix input) {
    if (log.isDebugEnabled()) {
      log.debug("sanitize(input = {})", input);
    }

    final PromoMatrix output = new PromoMatrix();
    if (input != null) {
      output.setLoyaltyCardRules(sanitizeRules(input::getLoyaltyCardRules));
      output.setItemCountRules(sanitizeRules(input::getItemCountRules));
      output.setItemGroupRules(sanitizeRules(input::getItemGroupRules));
    } else {
      output.setLoyaltyCardRules(List.of());
      output.setItemCountRules(List.of());
      output.setItemGroupRules(List.of());
    }

    if (log.isDebugEnabled()) {
      log.debug("sanitize(): output = {}", output);
    }
    return output;
  }

  private static <T> List<T> sanitizeRules(Supplier<List<T>> input) {
    return Optional.ofNullable(input.get()) //
      .map(items -> items.stream().filter(Objects::nonNull).collect(Collectors.toList())) //
      .filter(Predicate.not(List::isEmpty)) //
      .orElseGet(List::of);
  }

  private static Map<Integer, PromoMatrix> groupMatrixByStore(PromoMatrix input) {
    if (log.isDebugEnabled()) {
      log.debug("groupMatrixByStore(input = {})", input);
    }

    Objects.requireNonNull(input, () -> "matrix must not be null");

    final Map<Integer, PromoMatrix> output = new LinkedHashMap<>();
    groupRulesByStore(input, output, PromoMatrix::getLoyaltyCardRules, PromoMatrix::setLoyaltyCardRules);
    groupRulesByStore(input, output, PromoMatrix::getItemCountRules, PromoMatrix::setItemCountRules);
    groupRulesByStore(input, output, PromoMatrix::getItemGroupRules, PromoMatrix::setItemGroupRules);

    if (log.isDebugEnabled()) {
      log.debug("groupMatrixByStore(): output = {}", output);
    }
    return output;
  }

  private static <T extends PromoRule> void groupRulesByStore(PromoMatrix input, Map<Integer, PromoMatrix> output,
    Function<PromoMatrix, List<T>> rulesGetter, BiConsumer<PromoMatrix, List<T>> rulesSetter) {
    Objects.requireNonNull(input, () -> "input must not be null");
    Objects.requireNonNull(output, () -> "output must not be null");
    Objects.requireNonNull(rulesGetter, () -> "rulesGetter must not be null");
    Objects.requireNonNull(rulesSetter, () -> "rulesSetter must not be null");

    final List<T> inputRules = rulesGetter.apply(input);
    if (inputRules != null) {
      for (final T rule : inputRules) {
        final Integer shopId = Optional.ofNullable(rule.getShopId()).orElseGet(() -> PromoRule.RETAIL);

        PromoMatrix matrix = output.get(shopId);
        if (matrix == null) {
          matrix = new PromoMatrix();
          output.put(shopId, matrix);
        }

        List<T> outputRules = rulesGetter.apply(matrix);
        if (outputRules == null) {
          outputRules = new ArrayList<>();
          rulesSetter.accept(matrix, outputRules);
        }

        outputRules.add(rule);
      }
    }
  }

  private static PromoMatrix flattenMatrix(Map<Integer, PromoMatrix> input) {
    if (log.isDebugEnabled()) {
      log.debug("flattenMatrix(input = {})", input);
    }

    final PromoMatrix output = new PromoMatrix();
    flattenRules(input, output, PromoMatrix::getLoyaltyCardRules, PromoMatrix::setLoyaltyCardRules);
    flattenRules(input, output, PromoMatrix::getItemCountRules, PromoMatrix::setItemCountRules);
    flattenRules(input, output, PromoMatrix::getItemGroupRules, PromoMatrix::setItemGroupRules);

    if (log.isDebugEnabled()) {
      log.debug("flattenMatrix(): output = {}", output);
    }
    return output;
  }

  private static <T extends PromoRule> void flattenRules(Map<Integer, PromoMatrix> input, final PromoMatrix output,
    Function<PromoMatrix, List<T>> rulesGetter, BiConsumer<PromoMatrix, List<T>> rulesSetter) {
    Objects.requireNonNull(input, () -> "input must not be null");
    Objects.requireNonNull(output, () -> "output must not be null");
    Objects.requireNonNull(rulesGetter, () -> "rulesGetter must not be null");
    Objects.requireNonNull(rulesSetter, () -> "rulesSetter must not be null");

    List<T> outputRules = rulesGetter.apply(output);
    if (outputRules == null) {
      outputRules = new ArrayList<>();
      rulesSetter.accept(output, outputRules);
    }

    if (MapUtils.isNotEmpty(input)) {
      for (final PromoMatrix inputMatrix : input.values()) {
        final List<T> inputRules = rulesGetter.apply(inputMatrix);

        if (CollectionUtils.isNotEmpty(inputRules)) {
          outputRules.addAll(inputRules);
        }
      }
    }
  }
}
