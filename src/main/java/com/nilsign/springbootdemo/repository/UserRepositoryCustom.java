package com.nilsign.springbootdemo.repository;

import com.nilsign.springbootdemo.entity.UserEntity;
import java.util.Optional;

public interface UserRepositoryCustom {
   // TODO(nilsheumer): Check whether Hibernate automatically implements the correct function, when
   // the below function signature is just added to the plain UserRepository.java interface.
   Optional<UserEntity> findByEmail(String email);
}
