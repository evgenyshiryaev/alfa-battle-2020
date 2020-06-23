package com.x5.promo.model;

import java.math.BigDecimal;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "Результат расчета корзины")
public class FinalPriceReceipt {
  @ApiModelProperty("Полная сумма чека с применением скидок")
  BigDecimal total;
  @ApiModelProperty("Полная сумма скидки")
  BigDecimal discount;
  @ApiModelProperty("Результат расчета цен по позициям")
  private List<FinalPricePosition> positions;
}
