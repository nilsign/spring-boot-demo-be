package com.nilsign.springbootdemo.datacreator;

import com.nilsign.springbootdemo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DevDataCreatorComponent {
  private static final String GLOBAL_ADMIN_EMAIL = "nilsign@gmail.com";

  @Autowired
  private UserService userService;

  public void createIfNotExist() {
    log.info("DevDataCreatorComponent::createIfNotExist()");
    log.info("User: " + userService.findByEmail(GLOBAL_ADMIN_EMAIL).get());
  }
}
