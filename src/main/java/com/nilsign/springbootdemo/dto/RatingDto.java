package com.nilsign.springbootdemo.dto;

import com.nilsign.springbootdemo.dto.base.Dto;
import com.nilsign.springbootdemo.entity.RatingEntity;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Builder
@Data
public class RatingDto implements Dto {
  private Long id;

  @NotNull
  private UserDto user;

  @NotNull
  private ProductDto product;

  @Size(min = 0, max = 5)
  private float score;

  @NotBlank
  private String description;

  public static RatingDto create(RatingEntity ratingEntity) {
    return RatingDto.builder()
        .id(ratingEntity.getId())
        .user(UserDto.create(ratingEntity.getUser()))
        .product(ProductDto.create(ratingEntity.getProduct()))
        .score(ratingEntity.getScore())
        .description(ratingEntity.getDescription())
        .build();
  }
}
