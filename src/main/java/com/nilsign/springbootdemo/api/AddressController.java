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
}
