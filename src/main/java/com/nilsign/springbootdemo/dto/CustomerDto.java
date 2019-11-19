package com.nilsign.springbootdemo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@ToString
@AllArgsConstructor
public class CustomerDto implements AbstractDto {
  @Getter @Setter
  private Long id;

  @Getter @Setter @NotNull
  private UserDto user;

  @Getter @Setter @NotNull
  private boolean termsAndConditionsAccepted;

  @Getter @Setter
  private AddressDto postalAddress;
}
