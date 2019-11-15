package com.nilsign.springbootdemo.service;

import com.nilsign.springbootdemo.repository.UserRepository;
import com.nilsign.springbootdemo.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService extends AbstractService<UserEntity, Long> {
  @Autowired
  private UserRepository userRepository;

  @Override
  protected UserRepository getRepository() {
    return userRepository;
  }
}
