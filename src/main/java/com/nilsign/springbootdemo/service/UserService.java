package com.nilsign.springbootdemo.service;

import com.nilsign.springbootdemo.entity.UserEntity;
import com.nilsign.springbootdemo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService extends AbstractService<UserEntity, Long> {
  private final UserRepository userRepository;

  @Autowired
  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  protected UserRepository getRepository() {
    return userRepository;
  }

  public Optional<UserEntity> findByEmail(String email) {
    return getRepository().findByEmail(email);
  }
}
