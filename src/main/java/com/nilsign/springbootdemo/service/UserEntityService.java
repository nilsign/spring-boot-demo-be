package com.nilsign.springbootdemo.service;

import com.nilsign.springbootdemo.entity.UserEntity;
import com.nilsign.springbootdemo.repository.UserRepository;
import com.nilsign.springbootdemo.service.base.EntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Optional;

@Service
public class UserEntityService extends EntityService<UserEntity, Long> {

  @Autowired
  private UserRepository userRepository;

  @Override
  protected UserRepository getRepository() {
    return userRepository;
  }

  public Optional<UserEntity> findByEmail(@NotNull @NotBlank @Email String email) {
    return getRepository().findByEmail(email);
  }
}
