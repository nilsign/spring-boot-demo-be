package com.nilsign.springbootdemo.api;

import com.nilsign.springbootdemo.dto.UserDto;
import com.nilsign.springbootdemo.entity.UserEntity;
import com.nilsign.springbootdemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/user")
public class UserController extends AbstractController<UserDto, UserEntity, Long> {

  @Autowired
  private UserService userService;

  @Override
  protected UserService getService() {
    return userService;
  }

  @Override
  protected UserEntity entityFromDto(UserDto dto) {
    return UserController.userEntityFromDto(dto);
  }

  @Override
  protected UserDto dtoFromEntity(UserEntity entity) {
    return UserController.userDtoFromEntity(entity);
  }

  public static UserEntity userEntityFromDto(UserDto dto) {
    UserEntity entity = new UserEntity();
    entity.setId(dto.getId());
    entity.setRoles(dto.getRoles()
        .stream()
        .map(RoleController::roleEntityFromDto)
        .collect(Collectors.toList()));
    entity.setFirstName(dto.getFirstName());
    entity.setLastName(dto.getLastName());
    entity.setEmail(dto.getEmail());
    return entity;
  }

  public static UserDto userDtoFromEntity(UserEntity entity) {
    return new UserDto(
        entity.getId(),
        entity.getRoles()
            .stream()
            .map(RoleController::roleDtoFromEntity)
            .collect(Collectors.toList()),
        entity.getFirstName(),
        entity.getLastName(),
        entity.getEmail());
  }
}
