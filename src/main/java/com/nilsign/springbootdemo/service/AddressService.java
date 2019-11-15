package com.nilsign.springbootdemo.service;

import com.nilsign.springbootdemo.entity.AddressEntity;
import com.nilsign.springbootdemo.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressService extends AbstractService<AddressEntity, Long> {
  @Autowired
  private AddressRepository addressRepository;

  @Override
  protected AddressRepository getRepository() {
    return addressRepository;
  }
}
