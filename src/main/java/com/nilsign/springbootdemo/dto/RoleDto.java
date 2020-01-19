package com.nilsign.springbootdemo.dto;

import com.nilsign.springbootdemo.dto.base.Dto;
import com.nilsign.springbootdemo.entity.RoleEntity;
import com.nilsign.springbootdemo.entity.JpaRoleType;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Builder
@Data
public class RoleDto implements Dto {

  private Long id;

  @NotNull
  private JpaRoleType roleType;

  @NotNull
  @NotBlank
  private String roleName;

  public static RoleDto create(@NotNull RoleEntity roleEntity) {
    return RoleDto.builder()
        .id(roleEntity.getId())
        .roleType(roleEntity.getRoleType())
        .roleName(roleEntity.getRoleName())
        .build();
  }
}
