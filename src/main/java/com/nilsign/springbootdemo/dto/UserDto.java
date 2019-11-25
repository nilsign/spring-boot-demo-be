package com.nilsign.springbootdemo.dto;

import com.nilsign.springbootdemo.dto.helper.DtoArrayList;
import com.nilsign.springbootdemo.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@ToString
@AllArgsConstructor
public class UserDto implements AbstractDto {
  @Getter @Setter
  private Long id;

  @Getter @Setter @NotNull @NotEmpty
  private DtoArrayList<RoleDto> roles;

  @Getter @Setter @NotNull @NotBlank
  private String firstName;

  @Getter @Setter @NotNull @NotBlank
  private String lastName;

  @Getter @Setter @NotNull @NotBlank @Email
  private String email;

  @Override
  public UserEntity toEntity() {
    UserEntity entity = new UserEntity();
    entity.setId(id);
    entity.setRoles(roles.toEntities());
    entity.setFirstName(firstName);
    entity.setLastName(lastName);
    entity.setEmail(email);
    return entity;
  }
}
