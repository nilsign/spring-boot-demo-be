package com.nilsign.springbootdemo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.List;

@ToString
@AllArgsConstructor
public class ProductDto implements AbstractDto {
  @Getter @Setter
  private Long id;

  @Getter @Setter @NotNull @NotBlank
  private String name;

  @Getter @Setter @NotNull @Positive
  private BigDecimal price;

  @Getter @Setter @NotNull
  private List<RatingDto> ratings;

  @Getter @Setter @NotNull
  private List<OrderDto> orders;
}
