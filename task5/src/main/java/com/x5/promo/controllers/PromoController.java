package com.x5.promo.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.x5.promo.model.PromoMatrix;
import com.x5.promo.services.PromoRuleService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = "promo", tags = "Конфигурация промо-механик")
@RestController("promo")
@RequestMapping(path = "/promo", produces = MediaType.APPLICATION_JSON_VALUE)
public class PromoController {
  @Autowired
  private PromoRuleService service;

  @ApiOperation(value = "Просмотр текущих промо-механик", nickname = "getMatrix")
  @GetMapping
  public PromoMatrix getMatrix() {
    return service.getMatrix();
  }

  @ApiOperation(value = "Загрузка новой матрицы промо-механик", nickname = "setMatrix")
  @ApiResponses(value = { //
    @ApiResponse(code = 200, message = "Правила успешно загружены"),
    @ApiResponse(code = 400, message = "Некорректный запрос") //
  })
  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
  public void setMatrix(
    @ApiParam(value = "Матрица промо-механик", required = true) @Valid @RequestBody PromoMatrix body) {
    service.setMatrix(body);
  }
}
