package com.nilsign.springbootdemo.dto;

import com.nilsign.springbootdemo.entity.ProductEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

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

  public static ProductDto fromEntity(ProductEntity entity) {
    return new ProductDto(
        entity.getId(),
        entity.getName(),
        entity.getPrice(),
        entity.getRatings()
            .stream()
            .map(RatingDto::fromEntity)
            .collect(Collectors.toList()),
        entity.getOrders()
            .stream()
            .map(OrderDto::fromEntity)
            .collect(Collectors.toList()));
  }
}
