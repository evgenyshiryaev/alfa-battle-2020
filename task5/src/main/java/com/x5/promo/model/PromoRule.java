package com.x5.promo.model;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@ApiModel(description = "Базовое описание правил применения промо-механик")
public abstract class PromoRule {
  public static final Integer RETAIL = Integer.valueOf(-1);

  @NotNull
  @ApiModelProperty("Номер магазина, -1 для акции сети")
  Integer shopId;

  @Data
  @EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
  @ApiModel(description = "Параметры скидки при предъявлении пенсионного удостоверения или социальной карты")
  public static class LoyaltyCardRule extends PromoRule {
    @NotNull
    @ApiModelProperty("Размер скидки в процентах")
    Double discount;

    public static LoyaltyCardRule empty() {
      final LoyaltyCardRule rule = new LoyaltyCardRule();
      rule.setDiscount(0D);
      rule.setShopId(RETAIL);
      return rule;
    }
  }

  @Data
  @EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
  @ApiModel(description = "Параметры скидки формата \"2+1\" (N единиц товара по цене N-M единиц)")
  public static class ItemCountRule extends PromoRule {
    @NotNull
    @ApiModelProperty("ID товара")
    @EqualsAndHashCode.Include
    String itemId;
    @NotNull
    @ApiModelProperty("Количество единиц для применения скидки")
    Integer triggerQuantity;
    @NotNull
    @ApiModelProperty("Количество единиц товара в подарок")
    Integer bonusQuantity;
  }

  @Data
  @EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
  @ApiModel(description = "Параметры скидки при покупке связанных товаров")
  public static class ItemGroupRule extends PromoRule {
    @NotNull
    @ApiModelProperty("ID группы связанных товаров")
    @EqualsAndHashCode.Include
    String groupId;
    @NotNull
    @ApiModelProperty("Размер скидки в процентах")
    Double discount;
  }
}
