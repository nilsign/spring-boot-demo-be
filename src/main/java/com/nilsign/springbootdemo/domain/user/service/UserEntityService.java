package com.nilsign.springbootdemo.domain.user.service;

import com.nilsign.springbootdemo.domain.user.entity.UserEntity;
import com.nilsign.springbootdemo.domain.user.repository.UserRepository;
import com.nilsign.springbootdemo.domain.EntityService;
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
