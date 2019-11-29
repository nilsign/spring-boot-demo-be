package com.nilsign.springbootdemo.dto;

import com.nilsign.springbootdemo.dto.base.Dto;
import com.nilsign.springbootdemo.entity.UserEntity;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Builder
@Data
public class UserDto implements Dto {

  private Long id;

  @NotNull
  @NotBlank
  @Email
  private String email;

  @NotNull
  @NotBlank
  private String firstName;

  @NotNull
  @NotBlank
  private String lastName;

  @NotNull
  @NotEmpty
  private List<RoleDto> roles;

  // Bi-directional one-to-one dependency, so use an id here instead of the actual CustomerDto.
  private Long customerId;

  public static UserDto create(UserEntity userEntity) {
    return UserDto.builder()
        .id(userEntity.getId())
        .email(userEntity.getEmail())
        .firstName(userEntity.getFirstName())
        .lastName(userEntity.getLastName())
        .roles(userEntity.getRoles()
            .stream()
            .map(RoleDto::create)
            .collect(Collectors.toList()))
        .customerId(userEntity.getCustomer() == null
            ? null
            : userEntity.getCustomer().getId())
        .build();
  }
}
