package com.x5.promo.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.x5.promo.model.FinalPriceReceipt;
import com.x5.promo.model.ShoppingCart;
import com.x5.promo.services.ReceiptCalculateService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = "receipt", tags = "Расчет чека")
@RestController("receipt")
@RequestMapping(path = "/receipt", produces = MediaType.APPLICATION_JSON_VALUE)
public class ReceiptController {
  @Autowired
  private ReceiptCalculateService service;

  @ApiOperation(value = "Расчитать стоимость позиций в чеке для указанной корзины", nickname = "calculate", response = FinalPriceReceipt.class)
  @ApiResponses(value = {
    @ApiResponse(code = 200, message = "Стоимость успешно расчитана", response = FinalPriceReceipt.class),
    @ApiResponse(code = 400, message = "Некорректный запрос") })
  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
  public FinalPriceReceipt calculate(
    @ApiParam(value = "Данное о магазине и товарах в корзине", required = true) @Valid @RequestBody ShoppingCart cart) {
    return service.calculate(cart);
  }
}
