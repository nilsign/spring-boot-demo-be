package com.nilsign.springbootdemo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@AllArgsConstructor
public class AddressDto implements AbstractDto {
  @Getter @Setter
  private Long id;

  @Getter @Setter
  private String address;

  @Getter @Setter
  private String city;

  @Getter @Setter
  private String zip;

  @Getter @Setter
  private String country;
}
