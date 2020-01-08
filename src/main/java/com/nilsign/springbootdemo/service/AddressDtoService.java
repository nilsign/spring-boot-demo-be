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

  // Update will never work. When you're trying to update the entity through the controller, this will fail,
  // as hibernate will try to persist a new entity, as the given one won't be bound to a hibernate session.
  // Fix -> When id in dto is not null, load entity from db, update the values and persist it again. Check all
  // other entities as well.
  @Override
  protected AddressEntity toEntity(@NotNull AddressDto dto) {
    return AddressEntity.create(dto);
  }

  // Rename Parameter from addressDto to addressEntity.
  @Override
  protected AddressDto toDto(@NotNull AddressEntity addressDto) {
    return AddressDto.create(addressDto);
  }
}
