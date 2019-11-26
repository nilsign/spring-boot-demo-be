package com.nilsign.springbootdemo.dto;

import com.nilsign.springbootdemo.dto.helper.DtoArrayList;
import com.nilsign.springbootdemo.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
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
    UserEntity entity = new UserEntity(
        firstName,
        lastName,
        email,
        roles.toEntities(),
        null);
    entity.setId(id);
    return entity;
  }
}
