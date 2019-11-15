package com.nilsign.springbootdemo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@ToString
@AllArgsConstructor
public class ProductDto {
  @Getter @Setter
  private Long id;

  @Getter @Setter
  private String name;

  @Getter @Setter
  private BigDecimal price;
}
