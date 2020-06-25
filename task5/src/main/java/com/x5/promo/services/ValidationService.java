package com.x5.promo.services;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import com.x5.promo.model.ItemPosition;
import com.x5.promo.model.ShoppingCart;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ValidationService {

  public void validate(ShoppingCart cart) {
    if (log.isDebugEnabled()) {
      log.debug("validate(cart = {})", cart);
    }

    if (cart == null) {
      throw new IllegalArgumentException("Shopping cart is empty.");
    }

    final Integer shopId = cart.getShopId();
    if (shopId == null) {
      throw new IllegalArgumentException("Shopping cart has missing shop ID: " + cart);
    }

    if (shopId.intValue() < 0) {
      throw new IllegalArgumentException("Shopping cart has illegal shop ID \"" + shopId + "\": " + cart);
    }

    final List<ItemPosition> positions = cart.getPositions();
    if (CollectionUtils.isEmpty(positions)) {
      throw new IllegalArgumentException("Shopping cart has missing positions: " + cart);
    }

    final List<ItemPosition> invalid = positions.stream() //
      .filter(position -> !valid(position)) //
      .collect(Collectors.toList());

    if (!invalid.isEmpty()) {
      throw new IllegalArgumentException("Shopping cart has invalid positions: " + positions);
    }
  }

  public boolean valid(ItemPosition position) {
    return position != null && position.getItemId() != null && position.getQuantity() != null;
  }
}
