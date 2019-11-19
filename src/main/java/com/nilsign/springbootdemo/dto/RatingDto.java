package com.nilsign.springbootdemo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@ToString
@AllArgsConstructor
public class RatingDto implements AbstractDto {
  @Getter @Setter
  private Long id;

  @Getter @Setter @NotNull
  private UserDto user;

  @Getter @Setter @NotNull
  private ProductDto product;

  @Getter @Setter @Size(min = 0, max = 5)
  private float score;

  @Getter @Setter @NotBlank
  private String description;
}
