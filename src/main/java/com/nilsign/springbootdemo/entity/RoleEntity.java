package com.nilsign.springbootdemo.entity;

import com.nilsign.springbootdemo.dto.RoleDto;
import com.nilsign.springbootdemo.entity.base.SequencedEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

@NoArgsConstructor
@SuperBuilder
@Getter
@Setter
@ToString(callSuper = true)
@Entity
@Table(name = "tbl_role")
public class RoleEntity extends SequencedEntity {
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
}
