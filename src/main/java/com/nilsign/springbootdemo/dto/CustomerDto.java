package com.nilsign.springbootdemo.dto;

import com.nilsign.springbootdemo.entity.CustomerEntity;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@Data
public class CustomerDto implements AbstractDto {
  private Long id;

  @NotNull
  private UserDto user;

  @NotNull
  private boolean termsAndConditionsAccepted;

  private AddressDto postalAddress;

  @Override
  public CustomerEntity toEntity() {
    CustomerEntity entity = new CustomerEntity(
        user.toEntity(),
        termsAndConditionsAccepted,
        postalAddress.toEntity());
    entity.setId(getId());
    return entity;
  }
}
