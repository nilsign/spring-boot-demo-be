package com.nilsign.springbootdemo.api;

import com.nilsign.springbootdemo.api.base.Controller;
import com.nilsign.springbootdemo.dto.AddressDto;
import com.nilsign.springbootdemo.entity.AddressEntity;
import com.nilsign.springbootdemo.service.AddressDtoService;
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
