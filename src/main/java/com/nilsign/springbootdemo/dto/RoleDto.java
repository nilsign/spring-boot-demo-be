package com.nilsign.springbootdemo.dto;

import com.nilsign.springbootdemo.entity.RoleEntity;
import com.nilsign.springbootdemo.entity.RoleType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@ToString
@AllArgsConstructor
public class RoleDto implements AbstractDto {
  @Getter @Setter
  private Long id;

  @Getter @Setter @NotNull
  private RoleType roleType;

  @Getter @Setter @NotNull @NotBlank
  private String roleName;

  @Override
  public RoleEntity toEntity() {
    RoleEntity entity = new RoleEntity();
    entity.setId(id);
    entity.setRoleType(roleType);
    entity.setRoleName(roleName);
    return entity;
  }
}
