package com.nilsign.springbootdemo.service;

import com.nilsign.springbootdemo.dto.AddressDto;
import com.nilsign.springbootdemo.entity.AddressEntity;
import com.nilsign.springbootdemo.service.base.DtoService;
import com.nilsign.springbootdemo.service.base.EntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressDtoService extends DtoService<AddressDto, AddressEntity, Long> {

  @Autowired
  private AddressEntityService addressEntityService;

  @Override
  protected EntityService<AddressEntity, Long> getEntityService() {
    return addressEntityService;
  }

  @Override
  protected AddressEntity toEntity(AddressDto dto) {
    return AddressEntity.create(dto);
  }

  @Override
  protected AddressDto toDto(AddressEntity addressDto) {
    return AddressDto.create(addressDto);
  }
}
