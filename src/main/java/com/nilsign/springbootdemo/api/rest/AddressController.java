package com.nilsign.springbootdemo.api.rest;

import com.nilsign.springbootdemo.api.rest.base.Controller;
import com.nilsign.springbootdemo.domain.address.dto.AddressDto;
import com.nilsign.springbootdemo.domain.address.entity.AddressEntity;
import com.nilsign.springbootdemo.domain.address.service.AddressDtoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/address")
public class AddressController extends Controller<AddressDto, AddressEntity, Long> {

  @Autowired
  private AddressDtoService addressDtoService;

  @Override
  protected AddressDtoService getDtoService() {
    return addressDtoService;
  }
}
