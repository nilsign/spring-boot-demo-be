package com.nilsign.springbootdemo.dto;

import com.nilsign.springbootdemo.dto.helper.DtoArrayList;
import com.nilsign.springbootdemo.entity.ProductEntity;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Builder
@Data
public class ProductDto implements AbstractDto {
  private Long id;

  @NotNull
  @NotBlank
  private String name;

  @NotNull
  @Positive
  private BigDecimal price;

  @NotNull
  private DtoArrayList<RatingDto> ratings;

  @NotNull
  private DtoArrayList<OrderDto> orders;

  @Override
  public ProductEntity toEntity() {
    return ProductEntity.builder()
        .id(id)
        .name(name)
        .price(price)
        .ratings(ratings.toEntities())
        .orders(orders.toEntities())
        .build();
  }
}
