package com.nilsign.springbootdemo.service;

import com.nilsign.springbootdemo.dto.AddressDto;
import com.nilsign.springbootdemo.entity.AddressEntity;
import com.nilsign.springbootdemo.service.base.DtoService;
import com.nilsign.springbootdemo.service.base.EntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;

@Service
public class AddressDtoService extends DtoService<AddressDto, AddressEntity, Long> {

  @Autowired
  private AddressEntityService addressEntityService;

  @Override
  protected EntityService<AddressEntity, Long> getEntityService() {
    return addressEntityService;
  }

  @Override
  protected AddressEntity toEntity(@NotNull AddressDto dto) {
    return AddressEntity.create(dto);
  }

  @Override
  protected AddressDto toDto(@NotNull AddressEntity addressEntity) {
    return AddressDto.create(addressEntity);
  }
}
