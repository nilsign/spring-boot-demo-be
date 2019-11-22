package com.nilsign.springbootdemo.service;

import com.nilsign.springbootdemo.entity.RoleEntity;
import com.nilsign.springbootdemo.entity.RoleType;
import com.nilsign.springbootdemo.repository.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleService extends AbstractService<RoleEntity, Long> {
  private RoleRepository roleRepository;

  public RoleService(RoleRepository roleRepository) {
    this.roleRepository = roleRepository;
  }

  @Override
  protected RoleRepository getRepository() {
    return roleRepository;
  }

  public Optional<RoleEntity> findByRoleType(RoleType type) {
    return getRepository().findByRoleType(type);
  }
}
