package com.nilsign.springbootdemo.service;

import com.nilsign.springbootdemo.entity.RoleEntity;
import com.nilsign.springbootdemo.repository.RoleRepository;
import org.springframework.stereotype.Service;

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
}
