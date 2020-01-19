package com.nilsign.springbootdemo.service;

import com.nilsign.springbootdemo.entity.RoleEntity;
import com.nilsign.springbootdemo.entity.JpaRoleType;
import com.nilsign.springbootdemo.repository.RoleRepository;
import com.nilsign.springbootdemo.service.base.EntityService;
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
