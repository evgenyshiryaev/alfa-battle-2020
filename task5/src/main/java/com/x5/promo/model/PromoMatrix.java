package com.x5.promo.model;

import java.util.List;

import javax.validation.Valid;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "Матрица промо-механик")
public class PromoMatrix {
  @Valid
  @ApiModelProperty("Список правил скидки при предъявлении пенсионного удостоверения или социальной карты")
  List<PromoRule.LoyaltyCardRule> loyaltyCardRules;
  @Valid
  @ApiModelProperty("Список правил при покупке определенного количества единиц конкретного товара")
  List<PromoRule.ItemCountRule> itemCountRules;
  @Valid
  @ApiModelProperty("Список правил при покупке связанных товаров")
  List<PromoRule.ItemGroupRule> itemGroupRules;
}
