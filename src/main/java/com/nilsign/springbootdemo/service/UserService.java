package com.nilsign.springbootdemo.service;

import com.nilsign.springbootdemo.repository.UserRepository;
import com.nilsign.springbootdemo.entity.UserEntity;
import org.springframework.stereotype.Service;

@Service
public class UserService extends AbstractService<UserEntity, Long> {
  private UserRepository userRepository;

  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  protected UserRepository getRepository() {
    return userRepository;
  }
}
