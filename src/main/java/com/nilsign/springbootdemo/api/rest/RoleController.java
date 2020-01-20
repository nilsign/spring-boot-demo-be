package com.nilsign.springbootdemo.api.rest;

import com.nilsign.springbootdemo.api.rest.base.Controller;
import com.nilsign.springbootdemo.domain.role.dto.RoleDto;
import com.nilsign.springbootdemo.domain.role.entity.RoleEntity;
import com.nilsign.springbootdemo.domain.role.service.RoleDtoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/role")
public class RoleController extends Controller<RoleDto, RoleEntity, Long> {

  @Autowired
  private RoleDtoService roleDtoService;

  @Override
  protected RoleDtoService getDtoService() {
    return roleDtoService;
  }
}
