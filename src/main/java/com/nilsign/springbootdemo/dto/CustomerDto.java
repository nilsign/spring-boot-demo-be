package com.nilsign.springbootdemo.dto;

import com.nilsign.springbootdemo.entity.CustomerEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@ToString
@AllArgsConstructor()
public class CustomerDto implements AbstractDto {
  @Getter @Setter
  private Long id;

  @Getter @Setter
  @NotNull
  private UserDto user;

  @Getter @Setter
  @NotNull
  private boolean termsAndConditionsAccepted;

  @Getter @Setter
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
