package com.x5.promo.services;

import java.math.BigDecimal;
import java.util.Objects;

import com.x5.promo.model.Item;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
class PositionContext {
  private final Item item;
  private final int quantity;
  private int groupMatches;
  private boolean loyaltyCard;

  public PositionContext(Item item, int quantity) {
    this.item = Objects.requireNonNull(item, "item must not be null");
    this.quantity = quantity;
  }

  public String getItemId() {
    return item.getId();
  }

  public String getItemName() {
    return item.getName();
  }

  public String getGroupId() {
    return item.getGroupId();
  }

  public BigDecimal getPrice() {
    return item.getPrice();
  }
}