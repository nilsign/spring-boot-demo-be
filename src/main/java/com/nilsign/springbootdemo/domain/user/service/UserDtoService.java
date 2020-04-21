package com.nilsign.springbootdemo.domain.user.service;

import com.nilsign.springbootdemo.domain.DtoService;
import com.nilsign.springbootdemo.domain.customer.service.CustomerEntityService;
import com.nilsign.springbootdemo.domain.user.dto.UserDto;
import com.nilsign.springbootdemo.domain.user.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.Optional;

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

  public Optional<UserDto> findByEmail(String email) {
    Optional<UserEntity> result = getEntityService().findByEmail(email);
    return result.isEmpty()
        ? Optional.empty()
        : Optional.of(toDto(result.get()));
  }

  @Override
  protected UserEntity toEntity(@NotNull UserDto userDto) {
    return UserEntity.create(
        userDto,
        userDto.getCustomerId() == null
            ? null
            : customerEntityService.findById(userDto.getCustomerId())
                .orElseThrow(()-> new IllegalStateException(String.format(
                    "UserDto has an unknown customer id '%d'. CustomerEntity can not be null.",
                    userDto.getCustomerId()))));
  }

  @Override
  protected UserDto toDto(@NotNull UserEntity userEntity) {
    return UserDto.create(userEntity);
  }
}
