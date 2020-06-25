package com.x5.promo.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Value;

@Value
@Builder(builderClassName = "Builder")
@ApiModel(description = "Описание товара связанных товаров")
public class ItemGroup {
  @ApiModelProperty("ID группы")
  String id;
  @ApiModelProperty("Наименование группы")
  String name;
}
