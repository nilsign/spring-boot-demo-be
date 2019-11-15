package com.nilsign.springbootdemo.api;

import com.nilsign.springbootdemo.dto.AddressDto;
import com.nilsign.springbootdemo.entity.AddressEntity;
import com.nilsign.springbootdemo.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/address")
public class AddressController extends AbstractController<AddressDto, AddressEntity, Long> {
  @Autowired
  private AddressService addressService;

  @Override
  protected AddressService getService() {
    return addressService;
  }

  @Override
  protected AddressEntity entityFromDto(AddressDto dto) {
    return AddressController.addressEntityFromDto(dto);
  }

  @Override
  protected AddressDto dtoFromEntity(AddressEntity entity) {
    return AddressController.addressDtoFromEntity(entity);
  }

  public static AddressDto addressDtoFromEntity(AddressEntity entity) {
    return new AddressDto(
        entity.getId(),
        entity.getAddress(),
        entity.getCity(),
        entity.getZip(),
        entity.getCountry());
  }

  public static AddressEntity addressEntityFromDto(AddressDto dto) {
    AddressEntity entity = new AddressEntity();
    entity.setId(dto.getId());
    entity.setAddress(dto.getAddress());
    entity.setCity(dto.getCity());
    entity.setZip(dto.getZip());
    entity.setCountry(dto.getCountry());
    return entity;
  }
}
