package com.nilsign.springbootdemo.api;

import com.nilsign.springbootdemo.dto.RoleDto;
import com.nilsign.springbootdemo.entity.RoleEntity;
import com.nilsign.springbootdemo.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/role")
public class RoleController extends AbstractController<RoleDto, RoleEntity, Long> {
  @Autowired
  private RoleService roleService;

  @Override
  protected RoleService getService() {
    return roleService;
  }

  @Override
  protected RoleEntity entityFromDto(RoleDto dto) {
    return RoleController.roleEntityFromDto(dto);
  }

  @Override
  protected RoleDto dtoFromEntity(RoleEntity entity) {
    return null;
  }

  public static RoleEntity roleEntityFromDto(RoleDto dto) {
    RoleEntity entity = new RoleEntity();
    entity.setId(dto.getId());
    entity.setRoleType(dto.getRoleType());
    entity.setRoleName(dto.getRoleName());
    return entity;
  }

  public static RoleDto roleDtoFromEntity(RoleEntity entity) {
    return new RoleDto(
        entity.getId(),
        entity.getRoleType(),
        entity.getRoleName());
  }
}
