package com.nilsign.springbootdemo.service;

import com.nilsign.springbootdemo.dto.RoleDto;
import com.nilsign.springbootdemo.entity.RoleEntity;
import com.nilsign.springbootdemo.service.base.DtoService;
import com.nilsign.springbootdemo.service.base.EntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleDtoService extends DtoService<RoleDto, RoleEntity, Long> {

  @Autowired
  private RoleEntityService roleEntityService;

  @Override
  protected EntityService<RoleEntity, Long> getEntityService() {
    return roleEntityService;
  }

  @Override
  protected RoleEntity toEntity(RoleDto roleDto) {
    return RoleEntity.create(roleDto);
  }

  @Override
  protected RoleDto toDto(RoleEntity roleEntity) {
    return RoleDto.create(roleEntity);
  }
}
