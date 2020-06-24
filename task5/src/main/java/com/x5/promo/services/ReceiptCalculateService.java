package com.x5.promo.services;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.stereotype.Service;

import com.x5.promo.model.FinalPriceReceipt;
import com.x5.promo.model.Item;
import com.x5.promo.model.ShoppingCart;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public abstract class ReceiptCalculateService {
  @Autowired
  private ValidationService validation;
  @Autowired
  private ReferenceDataService referenceData;

  @Lookup
  public abstract RulesResolver newResolver(Integer shopId);

  @Lookup
  public abstract CalculationSession newSession(RulesResolver resolver);

  public FinalPriceReceipt calculate(ShoppingCart cart) {
    if (log.isDebugEnabled()) {
      log.debug("calculate(cart = {})", cart);
    }

    validation.validate(cart);

    final List<PositionContext> positions = adjustCart(cart);
    final RulesResolver resolver = newResolver(cart.getShopId());

    final CalculationSession session = newSession(resolver);
    positions.forEach(session::applyPosition);

    final FinalPriceReceipt receipt = session.getPriceReceipt();
    if (log.isDebugEnabled()) {
      log.debug("calculate(): receipt", receipt);
    }

    return receipt;
  }

  private List<PositionContext> adjustCart(ShoppingCart cart) {
    if (log.isDebugEnabled()) {
      log.debug("adjustCart(cart = {})", cart);
    }

    final Map<String, Integer> quantities = Optional.ofNullable(cart.getPositions()) //
      .map(list -> list.stream() //
        .collect(Collectors.groupingBy(pos -> pos.getItemId(), Collectors.summingInt(pos -> pos.getQuantity()))))
      .orElseGet(Map::of);

    final List<PositionContext> positions = quantities.entrySet().stream() //
      .map(this::adjustPosition) //
      .collect(Collectors.toList());

    final Map<String, Long> groupMatches = positions.stream() //
      .map(PositionContext::getGroupId) //
      .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

    final boolean loyaltyCard = Optional.ofNullable(cart.getLoyaltyCard()).orElse(Boolean.FALSE).booleanValue();
    positions.forEach(pos -> adjustPosition(pos, groupMatches, loyaltyCard));

    if (log.isDebugEnabled()) {
      log.debug("adjustCart(): positions", positions);
    }

    return positions;
  }

  private PositionContext adjustPosition(Map.Entry<String, Integer> input) {
    final Item item = referenceData.getItem(input.getKey()) //
      .orElseThrow(() -> new IllegalArgumentException("Unknown item ID: " + input));
    return new PositionContext(item, input.getValue());
  }

  private void adjustPosition(PositionContext position, Map<String, Long> groupMatches, boolean loyaltyCard) {
    position.setGroupMatches(groupMatches.getOrDefault(position.getGroupId(), 0L).intValue());
    position.setLoyaltyCard(loyaltyCard);
  }
}
