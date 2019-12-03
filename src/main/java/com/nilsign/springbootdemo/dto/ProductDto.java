package com.nilsign.springbootdemo.dto;

import com.nilsign.springbootdemo.dto.base.Dto;
import com.nilsign.springbootdemo.entity.ProductEntity;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Builder
@Data
public class ProductDto implements Dto {

  private Long id;

  @NotNull
  @Positive
  private Integer productNumber;

  @NotNull
  @NotBlank
  private String productName;

  @NotNull
  @Positive
  private BigDecimal price;

  @NotNull
  private List<RatingDto> ratings;

  @NotNull
  private Set<OrderDto> orders;

  public static ProductDto create(ProductEntity productEntity) {
    return ProductDto.builder()
        .id(productEntity.getId())
        .productNumber(productEntity.getProductNumber())
        .productName(productEntity.getProductName())
        .price(productEntity.getPrice())
        .orders(productEntity.getOrders() == null
            ? new HashSet<>()
            : productEntity.getOrders()
              .stream()
              .map(OrderDto::create)
              .collect(Collectors.toSet()))
        .ratings(productEntity.getRatings() == null
            ? new ArrayList<>()
            : productEntity.getRatings()
              .stream()
              .map(RatingDto::create)
              .collect(Collectors.toList()))
        .build();
  }
}
