package com.nilsign.springbootdemo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@ToString
@AllArgsConstructor
public class UserDto implements AbstractDto {
  @Getter @Setter
  private Long id;

  @Getter @Setter
  private List<RoleDto> roles;

  @Getter @Setter
  private String firstName;

  @Getter @Setter
  private String lastName;

  @Getter @Setter
  private String email;
}
