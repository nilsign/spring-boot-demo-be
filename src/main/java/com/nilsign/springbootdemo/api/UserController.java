package com.nilsign.springbootdemo.api;

import com.nilsign.springbootdemo.entity.UserEntity;
import com.nilsign.springbootdemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/user")
public class UserController extends AbstractController<UserEntity, Long> {

  @Autowired
  private UserService userService;

  @Override
  protected UserService getService() {
    return userService;
  }
}