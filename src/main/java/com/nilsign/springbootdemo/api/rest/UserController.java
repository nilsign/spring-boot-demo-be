package com.nilsign.springbootdemo.api.rest;

import com.nilsign.springbootdemo.api.rest.base.Controller;
import com.nilsign.springbootdemo.domain.user.dto.UserDto;
import com.nilsign.springbootdemo.domain.user.entity.UserEntity;
import com.nilsign.springbootdemo.domain.user.service.LoggedInUserDtoService;
import com.nilsign.springbootdemo.domain.user.service.UserDtoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/user")
public class UserController extends Controller<UserDto, UserEntity, Long> {

  @Autowired
  private UserDtoService userDtoService;

  @Autowired
  private LoggedInUserDtoService loggedInUserDtoService;

  @Override
  protected UserDtoService getDtoService() {
    return userDtoService;
  }

  @PreAuthorize("isAuthenticated()")
  @GetMapping(path = "logged-in-user")
  public UserDto getLoggedInUser() {
    return loggedInUserDtoService.getLoggedInUserDto();

  }
}
