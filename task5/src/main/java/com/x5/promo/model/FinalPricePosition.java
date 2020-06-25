package com.x5.promo.model;

import java.math.BigDecimal;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "Результат расчета цены для одной позиции")
public class FinalPricePosition {
  @ApiModelProperty(value = "ID товара")
  private String id = null;
  @ApiModelProperty("Наименование товара")
  private String name = null;
  @ApiModelProperty("Цена после применения скидки")
  private BigDecimal price = null;
  @ApiModelProperty("Цена до применения скидки")
  private BigDecimal regularPrice = null;
}
