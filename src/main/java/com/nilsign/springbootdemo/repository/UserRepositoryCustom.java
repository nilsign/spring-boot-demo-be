package com.nilsign.springbootdemo.repository;

import com.nilsign.springbootdemo.entity.UserEntity;
import java.util.Optional;

public interface UserRepositoryCustom {
   Optional<UserEntity> findByEmail(String email);
}
