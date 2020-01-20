package com.nilsign.springbootdemo.domain.role.service;

import com.nilsign.springbootdemo.domain.role.dto.RoleDto;
import com.nilsign.springbootdemo.domain.role.entity.RoleEntity;
import com.nilsign.springbootdemo.domain.DtoService;
import com.nilsign.springbootdemo.domain.EntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;

@Service
public class RoleDtoService extends DtoService<RoleDto, RoleEntity, Long> {

  @Autowired
  private RoleEntityService roleEntityService;

  @Override
  protected EntityService<RoleEntity, Long> getEntityService() {
    return roleEntityService;
  }

  @Override
  protected RoleEntity toEntity(@NotNull RoleDto roleDto) {
    return RoleEntity.create(roleDto);
  }

  @Override
  protected RoleDto toDto(@NotNull RoleEntity roleEntity) {
    return RoleDto.create(roleEntity);
  }
}
