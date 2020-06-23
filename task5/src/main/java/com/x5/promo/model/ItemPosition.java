package com.x5.promo.model;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "Позиция товара в корзине")
public class ItemPosition {
  @NotNull
  @ApiModelProperty(value = "ID товара")
  String itemId;
  @NotNull
  @ApiModelProperty("Количество товара в позиции")
  Integer quantity;
}
