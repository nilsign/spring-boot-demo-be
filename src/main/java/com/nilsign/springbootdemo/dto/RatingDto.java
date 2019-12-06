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

  // Bi- directional many-to-one dependency, so use the product id here instead of the actual
  // ProductDto in order to avoid stack overflows caused by this circular dependency.
  @NotNull
  private Long productId;

  @Size(min = 0, max = 5)
  private float score;

  @NotBlank
  private String description;

  public static RatingDto create(@NotNull RatingEntity ratingEntity) {
    return RatingDto.builder()
        .id(ratingEntity.getId())
        .user(UserDto.create(ratingEntity.getUser()))
        .productId(ratingEntity.getProduct().getId())
        .score(ratingEntity.getScore())
        .description(ratingEntity.getDescription())
        .build();
  }
}
