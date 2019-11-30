package com.nilsign.springbootdemo.dto;

import com.nilsign.springbootdemo.dto.base.Dto;
import com.nilsign.springbootdemo.entity.ProductEntity;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Builder
@Data
public class ProductDto implements Dto {
  private Long id;

  @NotNull
  @NotBlank
  private String name;

  @NotNull
  @Positive
  private BigDecimal price;

// TODO(nilsheumer): Reintroduce once the entities below are reintroduced.
//  @NotNull
//  private List<RatingDto> ratings;
//
//  @NotNull
//  private List<OrderDto> orders;

  public static ProductDto create(ProductEntity productEntity) {
    return ProductDto.builder()
        .id(productEntity.getId())
        .name(productEntity.getName())
        .price(productEntity.getPrice())
// TODO(nilsheumer): Reintroduce once the entities below are reintroduced.
//        .orders(productEntity.getOrders()
//            .stream()
//            .map(OrderDto::create)
//            .collect(Collectors.toList()))
//        .ratings(productEntity.getRatings()
//            .stream()
//            .map(RatingDto::create)
//            .collect(Collectors.toList()))
        .build();
  }
}
