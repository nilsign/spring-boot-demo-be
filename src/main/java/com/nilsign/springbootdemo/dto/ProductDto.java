package com.nilsign.springbootdemo.dto;

import com.nilsign.springbootdemo.dto.helper.DtoArrayList;
import com.nilsign.springbootdemo.entity.ProductEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

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
  private DtoArrayList<RatingDto> ratings;

  @Getter @Setter @NotNull
  private DtoArrayList<OrderDto> orders;

  @Override
  public ProductEntity toEntity() {
    ProductEntity entity = new ProductEntity(
        name,
        price,
        ratings.toEntities(),
        orders.toEntities());
    entity.setId(id);
    return entity;
  }
}
