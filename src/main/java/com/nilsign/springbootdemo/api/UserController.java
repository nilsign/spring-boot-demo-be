package com.nilsign.springbootdemo.api;

import com.nilsign.springbootdemo.api.base.Controller;
import com.nilsign.springbootdemo.dto.UserDto;
import com.nilsign.springbootdemo.entity.UserEntity;
import com.nilsign.springbootdemo.service.UserDtoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/user")
public class UserController extends Controller<UserDto, UserEntity, Long> {

  @Autowired
  private UserDtoService userDtoService;

  @Override
  protected UserDtoService getDtoService() {
    return userDtoService;
  }
}
