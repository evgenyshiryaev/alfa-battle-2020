package com.x5.promo.services;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.stream.Collectors;

import org.apache.commons.collections4.ListUtils;

import com.x5.promo.model.FinalPriceReceipt;
import com.x5.promo.model.Item;
import com.x5.promo.model.ItemGroup;
import com.x5.promo.model.ItemPosition;
import com.x5.promo.model.PromoMatrix;
import com.x5.promo.model.PromoRule;
import com.x5.promo.model.ShoppingCart;

import lombok.experimental.UtilityClass;

@UtilityClass
class Helpers {
  final Random random = new Random();

  ItemGroup groupOf(String id, String name) {
    return ItemGroup.builder().id(id).name(name).build();
  }

  Item itemOf(String id, String name, String groupId, BigDecimal price) {
    return Item.builder().id(id).name(name).groupId(groupId).price(price).build();
  }

  ShoppingCart cartOf(int shopId, Boolean loyaltyCard, ItemPosition pos1, ItemPosition... posN) {
    Objects.requireNonNull(pos1, "pos1 must not be null");
    Objects.requireNonNull(posN, "posN must not be null");

    final ShoppingCart cart = new ShoppingCart();
    cart.setShopId(shopId);
    cart.setLoyaltyCard(loyaltyCard);
    cart.setPositions(ListUtils.union(List.of(pos1), List.of(posN)));
    return cart;
  }

  ItemPosition positionOf(String itemId, int quantity) {
    final ItemPosition position = new ItemPosition();
    position.setItemId(itemId);
    position.setQuantity(quantity);
    return position;
  }

  FinalPriceReceipt receiptOf(String total, String discount) {
    final FinalPriceReceipt receipt = new FinalPriceReceipt();
    receipt.setTotal(new BigDecimal(total));
    receipt.setDiscount(new BigDecimal(discount));
    return receipt;
  }

  public static PromoMatrix matrixOf(List<PromoRule.LoyaltyCardRule> loyaltyCardRules,
    List<PromoRule.ItemCountRule> itemCountRules, List<PromoRule.ItemGroupRule> itemGroupRules) {
    final PromoMatrix matrix = new PromoMatrix();
    matrix.setLoyaltyCardRules(loyaltyCardRules);
    matrix.setItemCountRules(itemCountRules);
    matrix.setItemGroupRules(itemGroupRules);
    return matrix;
  }

  PromoRule.LoyaltyCardRule ruleOf(Integer shopId, Double discount) {
    final PromoRule.LoyaltyCardRule rule = new PromoRule.LoyaltyCardRule();
    rule.setShopId(shopId != null ? shopId : PromoRule.RETAIL);
    rule.setDiscount(discount);
    return rule;
  }

  PromoRule.ItemCountRule ruleOf(Integer shopId, String itemId, int triggerQuantity, int bonusQuantity) {
    final PromoRule.ItemCountRule rule = new PromoRule.ItemCountRule();
    rule.setShopId(shopId != null ? shopId : PromoRule.RETAIL);
    rule.setItemId(itemId);
    rule.setTriggerQuantity(triggerQuantity);
    rule.setBonusQuantity(bonusQuantity);
    return rule;
  }

  PromoRule.ItemGroupRule ruleOf(Integer shopId, String groupId, double discount) {
    final PromoRule.ItemGroupRule rule = new PromoRule.ItemGroupRule();
    rule.setShopId(shopId != null ? shopId : PromoRule.RETAIL);
    rule.setGroupId(groupId);
    rule.setDiscount(discount);
    return rule;
  }

  ShoppingCart randomCard(int cartLimit, int positionLimit, List<String> itemIds, int shopId, boolean loyaltyCard) {
    final ShoppingCart cart = new ShoppingCart();
    cart.setShopId(10);
    cart.setLoyaltyCard(loyaltyCard);
    cart.setPositions(random.ints(cartLimit, 0, itemIds.size()) //
      .mapToObj(itemIds::get) //
      .map(item -> Helpers.positionOf(item, 1 + Math.abs(random.nextInt(positionLimit - 1)))) //
      .collect(Collectors.toList()));
    return cart;
  }

  List<ShoppingCart> randomCards(ReferenceDataService referenceService, int shopId, int positionLimit) {
    Objects.requireNonNull(referenceService, "referenceService must not be null");

    final List<String> itemIds = new ArrayList<>(referenceService.getItemIds());
    itemIds.sort(Comparator.naturalOrder());

    return random.ints(10, 1, 10) //
      .mapToObj(cartLimit -> randomCard(cartLimit, positionLimit, itemIds, shopId, random.nextBoolean())) //
      .collect(Collectors.toList());
  }
}
