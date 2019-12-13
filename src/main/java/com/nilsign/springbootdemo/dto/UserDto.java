package com.nilsign.springbootdemo.dto;

import com.nilsign.springbootdemo.dto.base.Dto;
import com.nilsign.springbootdemo.entity.UserEntity;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Set;
import java.util.stream.Collectors;

@Builder
@Data
public class UserDto implements Dto {

  private Long id;

  @NotNull
  @NotBlank
  private String firstName;

  @NotNull
  @NotBlank
  private String lastName;

  @NotNull
  @NotBlank
  @Email
  private String email;

  private String password;

  @NotNull
  @NotEmpty
  private Set<RoleDto> roles;

  // Bi-directional one-to-one dependency, so use the customer id here instead of the actual
  // CustomerDto in order to avoid stack overflows caused by this circular dependency.
  private Long customerId;

  public static UserDto create(@NotNull UserEntity userEntity) {
    return UserDto.builder()
        .id(userEntity.getId())
        .email(userEntity.getEmail())
        .firstName(userEntity.getFirstName())
        .lastName(userEntity.getLastName())
        .roles(userEntity.getRoles()
            .stream()
            .map(RoleDto::create)
            .collect(Collectors.toSet()))
        .customerId(userEntity.getCustomer() == null
            ? null
            : userEntity.getCustomer().getId())
        .build();
  }
}
