package com.nilsign.springbootdemo.dto;

import com.nilsign.springbootdemo.entity.RoleEntity;
import com.nilsign.springbootdemo.entity.RoleType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@Getter
@Setter
@ToString
public class RoleDto implements AbstractDto {
  private Long id;

  @NotNull
  private RoleType roleType;

  @NotNull
  @NotBlank
  private String roleName;

  @Override
  public RoleEntity toEntity() {
    RoleEntity entity = new RoleEntity(
        roleType,
        roleName);
    entity.setId(id);
    return entity;
  }
}
