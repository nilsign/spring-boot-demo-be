package com.nilsign.springbootdemo.service;

import com.nilsign.springbootdemo.entity.CustomerEntity;
import com.nilsign.springbootdemo.repository.CustomerRepository;
import com.nilsign.springbootdemo.service.base.EntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerEntityService extends EntityService<CustomerEntity, Long> {

  @Autowired
  private CustomerRepository customerRepository;

  @Override
  protected CustomerRepository getRepository() {
    return customerRepository;
  }
}
