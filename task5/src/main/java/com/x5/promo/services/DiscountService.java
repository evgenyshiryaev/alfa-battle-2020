package com.x5.promo.services;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.x5.promo.model.PromoRule;
import com.x5.promo.model.PromoRule.ItemCountRule;
import com.x5.promo.model.PromoRule.ItemGroupRule;

@Service
public class DiscountService {

  public BigDecimal basePrice(PositionContext position) {
    return positionPrice(0D, position);
  }

  public BigDecimal positionPrice(PromoRule.LoyaltyCardRule rule, PositionContext position) {
    Double discountRatio;
    if (position.isLoyaltyCard()) {
      discountRatio = rule.getDiscount();
    } else {
      discountRatio = 0d;
    }
    return positionPrice(discountRatio, position);
  }

  public BigDecimal positionPrice(ItemCountRule rule, PositionContext position) {
    final int trigger = Optional.ofNullable(rule.getTriggerQuantity()).orElseGet(() -> Integer.MAX_VALUE);
    final int bonus = Optional.ofNullable(rule.getBonusQuantity()).orElseGet(() -> 0);
    final int discountGroup = trigger + bonus;

    int quantity = position.getQuantity();
    final int operations = quantity / discountGroup;
    final int remainder = quantity % discountGroup;
    quantity = (operations * trigger) + remainder;

    final BigDecimal itemPrice = position.getPrice();
    return itemPrice.multiply(new BigDecimal(quantity)).setScale(2, RoundingMode.HALF_EVEN);
  }

  public BigDecimal positionPrice(ItemGroupRule rule, PositionContext position) {
    Double discountRatio;
    if (position.getGroupMatches() > 1L) {
      discountRatio = rule.getDiscount();
    } else {
      discountRatio = 0d;
    }
    return positionPrice(discountRatio, position);
  }

  private BigDecimal positionPrice(Double discountRatio, PositionContext position) {
    final BigDecimal itemPrice = position.getPrice();

    final BigDecimal finalPrice;
    if (discountRatio != null) {
      finalPrice = itemPrice.multiply(new BigDecimal(1.0D - discountRatio.doubleValue()));
    } else {
      finalPrice = itemPrice;
    }

    return finalPrice.multiply(new BigDecimal(position.getQuantity())).setScale(2, RoundingMode.HALF_EVEN);
  }
}
