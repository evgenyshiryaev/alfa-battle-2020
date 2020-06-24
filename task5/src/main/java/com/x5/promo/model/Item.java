package com.x5.promo.model;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Value;

@Value
@Builder(builderClassName = "Builder")
@ApiModel(description = "Описание товара")
public class Item {
  @NotNull
  @ApiModelProperty(value = "ID товара")
  String id;
  @NotNull
  @ApiModelProperty("Наименование товара")
  String name;
  @NotNull
  @ApiModelProperty("ID группы связанных товаров")
  String groupId;
  @NotNull
  @ApiModelProperty("Цена товара")
  BigDecimal price;
}
