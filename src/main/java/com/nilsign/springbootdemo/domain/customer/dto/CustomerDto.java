package com.nilsign.springbootdemo.domain.customer.dto;

import com.nilsign.springbootdemo.domain.Dto;
import com.nilsign.springbootdemo.domain.address.dto.AddressDto;
import com.nilsign.springbootdemo.domain.user.dto.UserDto;
import com.nilsign.springbootdemo.domain.customer.entity.CustomerEntity;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Builder
@Data
public class CustomerDto implements Dto {

  private Long id;

  // Bi-directional one-to-one dependency.
  @NotNull
  private UserDto user;

  @NotNull
  private boolean termsAndConditionsAccepted;

  private AddressDto postalAddress;

  public static CustomerDto create(@NotNull CustomerEntity customerEntity) {
    return CustomerDto.builder()
        .id(customerEntity.getId())
        .user(UserDto.create(customerEntity.getUser()))
        .termsAndConditionsAccepted(customerEntity.isTermsAndConditionsAccepted())
        .postalAddress(AddressDto.create(customerEntity.getPostalAddress()))
        .build();
  }
}
