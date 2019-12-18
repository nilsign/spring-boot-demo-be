package com.nilsign.springbootdemo.data.creator;

import com.nilsign.springbootdemo.entity.ProductEntity;
import com.nilsign.springbootdemo.service.ProductEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Service
public final class ProductDataCreator {

  @Autowired
  private ProductEntityService productEntityService;

  public void createProductIfNotExist(
      @NotNull @NotBlank @Positive Integer productNumber,
      @NotNull @NotBlank String productName,
      @NotNull @Positive BigDecimal price) {
    if (productEntityService.findByProductNumber(productNumber).isEmpty()) {
      productEntityService.save(ProductEntity.builder()
          .productNumber(productNumber)
          .productName(productName)
          .price(price)
          .build());
    }
  }
}
