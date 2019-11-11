package com.nilsign.springbootdemo.service;

import com.nilsign.springbootdemo.repository.CustomerRepository;
import com.nilsign.springbootdemo.entity.CustomerEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

  @Autowired
  private CustomerRepository customerRepository;

  public Optional<CustomerEntity> save(CustomerEntity customer) {
    return Optional.of(customerRepository.save(customer));
  }

  public List<CustomerEntity> findAll() {
    return customerRepository.findAll();
  }

  public Optional<CustomerEntity> findById(Long id) {
    return customerRepository.findById(id);
  }

  public void deleteById(long id) {
    customerRepository.deleteById(id);
  }
}
