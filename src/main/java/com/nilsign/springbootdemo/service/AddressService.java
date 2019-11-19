package com.nilsign.springbootdemo.service;

import com.nilsign.springbootdemo.entity.AddressEntity;
import com.nilsign.springbootdemo.repository.AddressRepository;
import org.springframework.stereotype.Service;

@Service
public class AddressService extends AbstractService<AddressEntity, Long> {
  private AddressRepository addressRepository;

  public AddressService(AddressRepository addressRepository) {
    this.addressRepository = addressRepository;
  }

  @Override
  protected AddressRepository getRepository() {
    return addressRepository;
  }
}
