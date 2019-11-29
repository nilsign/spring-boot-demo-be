package com.nilsign.springbootdemo.dto;

import com.nilsign.springbootdemo.dto.base.Dto;
import com.nilsign.springbootdemo.entity.CustomerEntity;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Builder
@Data
public class CustomerDto implements Dto {

  private Long id;

  // Bi-directional cyclic dependency.
  @NotNull
  private UserDto user;

  @NotNull
  private boolean termsAndConditionsAccepted;

  private AddressDto postalAddress;

  public static CustomerDto create(CustomerEntity customerEntity) {
    return CustomerDto.builder()
        .id(customerEntity.getId())
        .user(UserDto.create(customerEntity.getUser()))
        .termsAndConditionsAccepted(customerEntity.isTermsAndConditionsAccepted())
        .postalAddress(AddressDto.create(customerEntity.getPostalAddress()))
        .build();
  }
}
