package com.nilsign.springbootdemo.dto;

import com.nilsign.springbootdemo.entity.AddressEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@Getter
@Setter
@ToString
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
    AddressEntity entity = new AddressEntity(
        address,
        city,
        zip,
        country);
    entity.setId(getId());
    return entity;
  }
}
