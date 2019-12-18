package com.nilsign.springbootdemo.service;

import com.nilsign.springbootdemo.entity.AddressEntity;
import com.nilsign.springbootdemo.repository.AddressRepository;
import com.nilsign.springbootdemo.service.base.EntityService;
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
