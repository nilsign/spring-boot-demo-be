package com.nilsign.springbootdemo.domain.role.service;

import com.nilsign.springbootdemo.domain.role.entity.RoleEntity;
import com.nilsign.springbootdemo.domain.role.JpaRoleType;
import com.nilsign.springbootdemo.domain.role.repository.RoleRepository;
import com.nilsign.springbootdemo.domain.EntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.Optional;

@Service
public class RoleEntityService extends EntityService<RoleEntity, Long> {

  @Autowired
  private RoleRepository roleRepository;

  @Override
  protected RoleRepository getRepository() {
    return roleRepository;
  }

  public Optional<RoleEntity> findByRoleType(@NotNull JpaRoleType type) {
    return getRepository().findByRoleType(type);
  }
}
