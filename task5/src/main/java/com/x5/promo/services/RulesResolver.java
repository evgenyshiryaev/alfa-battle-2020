package com.x5.promo.services;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.x5.promo.model.PromoMatrix;
import com.x5.promo.model.PromoRule;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class RulesResolver {
  private final Integer shopId;

  private Optional<PromoMatrix> retailLevel;
  private Optional<PromoMatrix> storeLevel;

  @Autowired
  private PromoRuleService service;

  public RulesResolver(Integer shopId) {
    this.shopId = Objects.requireNonNull(shopId, () -> "shopId must not be null");
  }

  @PostConstruct
  public void postConstruct() {
    Objects.requireNonNull(service, () -> "service must not be null");

    retailLevel = service.getRetailMatrix();
    storeLevel = service.getShopMatrix(shopId);
  }

  public Optional<PromoRule.LoyaltyCardRule> resolveLoyaltyCardRule() {
    return findFirstRule(PromoMatrix::getLoyaltyCardRules);
  }

  public Optional<PromoRule.ItemCountRule> resolveItemCountRule(String itemId) {
    final Predicate<PromoRule.ItemCountRule> search = rule -> Objects.equals(itemId, rule.getItemId());
    return findFirstRule(PromoMatrix::getItemCountRules, search);
  }

  public Optional<PromoRule.ItemGroupRule> resolveItemGroupRule(String groupId) {
    final Predicate<PromoRule.ItemGroupRule> search = rule -> Objects.equals(groupId, rule.getGroupId());
    return findFirstRule(PromoMatrix::getItemGroupRules, search);
  }

  private <T> Optional<T> findFirstRule(Function<PromoMatrix, List<T>> rulesGetter) {
    final Function<List<T>, Optional<T>> search = list -> list.stream().findFirst();
    return storeLevel.map(rulesGetter).flatMap(search).or(() -> retailLevel.map(rulesGetter).flatMap(search));
  }

  private <T> Optional<T> findFirstRule(Function<PromoMatrix, List<T>> rulesGetter, Predicate<T> searchCondition) {
    final Function<List<T>, Optional<T>> search = list -> list.stream().filter(searchCondition).findFirst();
    return storeLevel.map(rulesGetter).flatMap(search).or(() -> retailLevel.map(rulesGetter).flatMap(search));
  }
}