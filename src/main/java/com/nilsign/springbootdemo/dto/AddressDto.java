package com.nilsign.springbootdemo.dto;

import com.nilsign.springbootdemo.entity.AddressEntity;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Builder
@Data
public class AddressDto implements AbstractDto {
  private Long id;

  @NotNull
  @NotBlank
  private String address;

  @NotNull
  @NotBlank
  private String city;

  @NotNull
  @NotBlank
  private String zip;

  @NotNull
  @NotBlank
  private String country;

  @Override
  public AddressEntity toEntity() {
    return AddressEntity.builder()
        .id(id)
        .address(address)
        .city(city)
        .zip(zip)
        .country(country)
        .build();
  }
}
