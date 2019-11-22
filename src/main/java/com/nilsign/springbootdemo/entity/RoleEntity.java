package com.nilsign.springbootdemo.entity;

import com.nilsign.springbootdemo.dto.RoleDto;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import java.util.StringJoiner;

@Entity
@Table(name = "tbl_role")
public class RoleEntity extends AbstractEntity {
  @Getter @Setter
  @Column(name = "role_type", nullable = false)
  @Enumerated(EnumType.STRING)
  private RoleType roleType;

  @Getter @Setter
  @Column(name = "role_name", nullable = false)
  private String roleName;

  public static RoleEntity fromDto(RoleDto dto) {
    RoleEntity entity = new RoleEntity();
    entity.setId(dto.getId());
    entity.setRoleType(dto.getRoleType());
    entity.setRoleName(dto.getRoleName());
    return entity;
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", "\n" + RoleEntity.class.getSimpleName() + "[", "\n]")
        .add("\n\t" + "id=" + super.getId())
        .add("\n\t" + "role_type=" + roleType)
        .add("\n\t" + "roleName='" + roleName + "'")
        .toString();
  }
}
