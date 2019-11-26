package com.nilsign.springbootdemo.dto;

import com.nilsign.springbootdemo.dto.helper.DtoArrayList;
import com.nilsign.springbootdemo.entity.UserEntity;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Builder
@Data
public class UserDto implements AbstractDto {
  private Long id;

  @NotNull
  @NotEmpty
  private DtoArrayList<RoleDto> roles;

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

  @Override
  public UserEntity toEntity() {
    return UserEntity.builder()
        .id(id)
        .firstName(firstName)
        .lastName(lastName)
        .email(email)
        .roles(roles.toEntities())
        .build();
  }
}
