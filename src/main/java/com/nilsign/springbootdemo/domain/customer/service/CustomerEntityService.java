package com.nilsign.springbootdemo.domain.customer.service;

import com.nilsign.springbootdemo.domain.customer.entity.CustomerEntity;
import com.nilsign.springbootdemo.domain.customer.repository.CustomerRepository;
import com.nilsign.springbootdemo.domain.EntityService;
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
