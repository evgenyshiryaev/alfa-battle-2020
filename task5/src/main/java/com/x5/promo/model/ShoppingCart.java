package com.x5.promo.model;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "Данные о магазине и товарах в корзине")
public class ShoppingCart {
  @NotNull
  @ApiModelProperty("Номер магазина")
  Integer shopId;

  @ApiModelProperty("Признак регистрации пенсионного удостоверения или социальной карты")
  Boolean loyaltyCard;

  @NotNull
  @Valid
  @ApiModelProperty("Позиции с товарами в корзине")
  List<ItemPosition> positions;
}
