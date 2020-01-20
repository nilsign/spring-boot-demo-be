package com.nilsign.springbootdemo.domain.role.entity;

import com.nilsign.springbootdemo.domain.role.JpaRoleType;
import com.nilsign.springbootdemo.domain.role.dto.RoleDto;
import com.nilsign.springbootdemo.domain.SequencedEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@SuperBuilder
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Entity
@Table(name = "tbl_role")
public class RoleEntity extends SequencedEntity {

  @Column(name = "role_type", nullable = false)
  @Enumerated(EnumType.STRING)
  private JpaRoleType roleType;

  @Column(name = "role_name", nullable = false)
  private String roleName;

  public static RoleEntity create(@NotNull RoleDto roleDto) {
    return RoleEntity.builder()
        .id(roleDto.getId())
        .roleType(roleDto.getRoleType())
        .roleName(roleDto.getRoleName())
        .build();
  }
}
