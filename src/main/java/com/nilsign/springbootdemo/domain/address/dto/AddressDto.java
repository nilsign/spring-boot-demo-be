package com.nilsign.springbootdemo.domain.address.dto;

import com.nilsign.springbootdemo.domain.Dto;
import com.nilsign.springbootdemo.domain.address.entity.AddressEntity;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Builder
@Data
public class AddressDto implements Dto {

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

  public static AddressDto create(@NotNull AddressEntity addressEntity) {
    return AddressDto.builder()
        .id(addressEntity.getId())
        .address(addressEntity.getAddress())
        .zip(addressEntity.getZip())
        .city(addressEntity.getCity())
        .country(addressEntity.getCountry())
        .build();
  }
}
