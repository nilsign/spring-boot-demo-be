package com.nilsign.springbootdemo.repository;

import com.nilsign.springbootdemo.entity.RoleEntity;
import com.nilsign.springbootdemo.entity.RoleType;

import java.util.Optional;

public interface RoleRepositoryCustom {
  Optional<RoleEntity> findByRoleType(RoleType roleType);
}

