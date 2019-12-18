package com.nilsign.springbootdemo.service;

import com.nilsign.springbootdemo.dto.UserDto;
import com.nilsign.springbootdemo.entity.UserEntity;
import com.nilsign.springbootdemo.service.base.DtoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;

@Service
public class UserDtoService extends DtoService<UserDto, UserEntity, Long> {

  @Autowired
  private UserEntityService userEntityService;

  @Autowired
  private CustomerEntityService customerEntityService;

  @Override
  protected UserEntityService getEntityService() {
    return this.userEntityService;
  }

  @Override
  protected UserEntity toEntity(@NotNull UserDto userDto) {
    return UserEntity.create(
        userDto,
        userDto.getCustomerId() == null
            ? null
            : customerEntityService.findById(userDto.getCustomerId()).get());
  }

  @Override
  protected UserDto toDto(@NotNull UserEntity userEntity) {
    return UserDto.create(userEntity);
  }
}
