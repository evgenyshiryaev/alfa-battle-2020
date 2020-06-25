package com.x5.promo.services;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.x5.promo.model.FinalPricePosition;
import com.x5.promo.model.FinalPriceReceipt;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class CalculationSession {
  private final RulesResolver resolver;
  private final FinalPriceReceipt output;

  @Autowired
  private DiscountService discount;

  public CalculationSession(RulesResolver resolver) {
    this.resolver = Objects.requireNonNull(resolver, () -> "resolver must not be null");
    this.output = new FinalPriceReceipt();
  }

  public FinalPriceReceipt getPriceReceipt() {
    return output;
  }

  public void applyPosition(PositionContext position) {
    Objects.requireNonNull(position, () -> "position must not be null");

    final List<BigDecimal> priceOptions = new ArrayList<>();

    final BigDecimal basePrice = discount.basePrice(position);
    priceOptions.add(basePrice);

    resolver.resolveLoyaltyCardRule() //
      .map(rule -> discount.positionPrice(rule, position)).ifPresent(priceOptions::add);
    resolver.resolveItemCountRule(position.getItemId()) //
      .map(rule -> discount.positionPrice(rule, position)).ifPresent(priceOptions::add);
    resolver.resolveItemGroupRule(position.getGroupId()) //
      .map(rule -> discount.positionPrice(rule, position)).ifPresent(priceOptions::add);

    priceOptions.stream() //
      .sorted() //
      .findFirst() //
      .ifPresent(price -> addPricePosition(price, basePrice, position));
  }

  private void addPricePosition(BigDecimal finalPrice, BigDecimal basePrice, PositionContext cardPos) {
    final BigDecimal discount = basePrice.subtract(finalPrice);

    final FinalPricePosition finalPos = new FinalPricePosition();
    finalPos.setId(cardPos.getItemId());
    finalPos.setName(cardPos.getItemName());
    finalPos.setPrice(finalPrice);
    finalPos.setRegularPrice(basePrice);

    List<FinalPricePosition> positions = output.getPositions();
    if (positions == null) {
      positions = new ArrayList<>();
      output.setPositions(positions);
    }

    positions.add(finalPos);

    output.setTotal(Optional.ofNullable(output.getTotal()).map(total -> total.add(finalPrice)).orElse(finalPrice));
    output.setDiscount(Optional.ofNullable(output.getDiscount()).map(value -> value.add(discount)).orElse(discount));
  }
}