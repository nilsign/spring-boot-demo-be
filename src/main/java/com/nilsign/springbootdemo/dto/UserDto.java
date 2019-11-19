package com.nilsign.springbootdemo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@ToString
@AllArgsConstructor
public class UserDto implements AbstractDto {
  @Getter @Setter
  private Long id;

  @Getter @Setter @NotNull @NotEmpty
  private List<RoleDto> roles;

  @Getter @Setter @NotNull @NotBlank
  private String firstName;

  @Getter @Setter @NotNull @NotBlank
  private String lastName;

  @Getter @Setter @NotNull @NotBlank @Email
  private String email;
}
