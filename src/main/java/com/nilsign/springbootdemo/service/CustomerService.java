package com.nilsign.springbootdemo.service;

import com.nilsign.springbootdemo.entity.CustomerEntity;
import com.nilsign.springbootdemo.repository.CustomerRepository;
import org.springframework.stereotype.Service;

@Service
public class CustomerService extends AbstractService<CustomerEntity, Long> {
  private CustomerRepository customerRepository;

  public CustomerService(CustomerRepository customerRepository) {
    this.customerRepository = customerRepository;
  }

  @Override
  protected CustomerRepository getRepository() {
    return customerRepository;
  }
}
