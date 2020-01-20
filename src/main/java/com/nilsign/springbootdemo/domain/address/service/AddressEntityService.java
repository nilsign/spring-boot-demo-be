package com.nilsign.springbootdemo.domain.address.service;

import com.nilsign.springbootdemo.domain.address.entity.AddressEntity;
import com.nilsign.springbootdemo.domain.address.repository.AddressRepository;
import com.nilsign.springbootdemo.domain.EntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressEntityService extends EntityService<AddressEntity, Long> {

  @Autowired
  private AddressRepository addressRepository;

  @Override
  protected AddressRepository getRepository() {
    return addressRepository;
  }
}
