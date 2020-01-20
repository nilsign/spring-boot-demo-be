package com.nilsign.springbootdemo.domain.address.service;

import com.nilsign.springbootdemo.domain.address.dto.AddressDto;
import com.nilsign.springbootdemo.domain.address.entity.AddressEntity;
import com.nilsign.springbootdemo.domain.DtoService;
import com.nilsign.springbootdemo.domain.EntityService;
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
