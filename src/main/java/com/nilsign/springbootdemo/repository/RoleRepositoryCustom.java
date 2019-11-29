package com.nilsign.springbootdemo.repository;

import com.nilsign.springbootdemo.entity.RoleEntity;
import com.nilsign.springbootdemo.entity.RoleType;

import java.util.Optional;

public interface RoleRepositoryCustom {

  // Note, that the implementation of this function will be automatically generated when this
  // signature is added directly to the UserRepository. There is actually no need to create a
  // UserRepositoryCustom interface and a UserRepositoryCustomImpl class for the findByEmail
  // database request, but for the sake of creating a database request with the query builder and
  // the entity manager this implementation is kept until there are more specific examples that can
  // not be auto-generated.
  Optional<RoleEntity> findByRoleType(RoleType roleType);
}
