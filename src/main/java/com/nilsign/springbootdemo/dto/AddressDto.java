package com.nilsign.springbootdemo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@ToString
@AllArgsConstructor
public class AddressDto implements AbstractDto {
  @Getter @Setter
  private Long id;

  @Getter @Setter @NotNull @NotBlank
  private String address;

  @Getter @Setter @NotNull @NotBlank
  private String city;

  @Getter @Setter @NotNull @NotBlank
  private String zip;

  @Getter @Setter @NotNull @NotBlank
  private String country;
}
