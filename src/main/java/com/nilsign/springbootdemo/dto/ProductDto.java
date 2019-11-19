package com.nilsign.springbootdemo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.List;

@ToString
@AllArgsConstructor
public class ProductDto implements AbstractDto {
  @Getter @Setter
  private Long id;

  @Getter @Setter
  private String name;

  @Getter @Setter
  private BigDecimal price;

  @Getter @Setter
  private List<RatingDto> ratings;

  @Getter @Setter
  private List<OrderDto> orders;
}
