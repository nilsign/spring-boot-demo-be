package com.nilsign.springbootdemo.dto;

import com.nilsign.springbootdemo.entity.RatingEntity;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@AllArgsConstructor
@Data
public class RatingDto implements AbstractDto {
  private Long id;

  @NotNull
  private UserDto user;

  @NotNull
  private ProductDto product;

  @Size(min = 0, max = 5)
  private float score;

  @NotBlank
  private String description;

  @Override
  public RatingEntity toEntity() {
    RatingEntity entity = new RatingEntity(
        user.toEntity(),
        product.toEntity(),
        score,
        description);
    entity.setId(id);
    return entity;
  }
}
