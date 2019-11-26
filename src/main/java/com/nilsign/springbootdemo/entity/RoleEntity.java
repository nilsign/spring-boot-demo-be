package com.nilsign.springbootdemo.entity;

import com.nilsign.springbootdemo.dto.RoleDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import java.util.StringJoiner;

@NoArgsConstructor
@SuperBuilder
@Getter
@Setter
@Entity
@Table(name = "tbl_role")
public class RoleEntity extends AbstractEntity {
  @Column(name = "role_type", nullable = false)
  @Enumerated(EnumType.STRING)
  private RoleType roleType;

  @Column(name = "role_name", nullable = false)
  private String roleName;

  @Override
  public RoleDto toDto() {
    return RoleDto.builder()
        .id(super.getId())
        .roleName(roleName)
        .roleType(roleType)
        .build();
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
