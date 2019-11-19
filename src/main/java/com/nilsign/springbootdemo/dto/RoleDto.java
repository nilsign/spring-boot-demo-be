package com.nilsign.springbootdemo.dto;

import com.nilsign.springbootdemo.entity.RoleType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@AllArgsConstructor
public class RoleDto implements AbstractDto {
  @Getter @Setter
  private Long id;

  @Getter @Setter
  private RoleType roleType;

  @Getter @Setter
  private String roleName;
}
