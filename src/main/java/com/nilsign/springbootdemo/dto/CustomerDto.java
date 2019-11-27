package com.nilsign.springbootdemo.dto;

import com.nilsign.springbootdemo.dto.base.Dto;
import com.nilsign.springbootdemo.entity.CustomerEntity;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Builder
@Data
public class CustomerDto implements Dto {
  private Long id;

  @ToString.Exclude
  @NotNull
  private UserDto user;

  @NotNull
  private boolean termsAndConditionsAccepted;

  private AddressDto postalAddress;

  @Override
  public CustomerEntity toEntity() {
    return CustomerEntity.builder()
        .id(id)
        .user(user.toEntity())
        .termsAndConditionsAccepted(termsAndConditionsAccepted)
        .postalAddress(postalAddress.toEntity())
        .build();
  }
}
