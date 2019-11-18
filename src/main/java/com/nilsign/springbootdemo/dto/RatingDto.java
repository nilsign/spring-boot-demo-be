package com.nilsign.springbootdemo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@AllArgsConstructor
public class RatingDto implements AbstractDto {
  @Getter @Setter
  private Long id;

  @Getter @Setter
  private UserDto user;

  @Getter @Setter
  private float score;

  @Getter @Setter
  private String description;
}
