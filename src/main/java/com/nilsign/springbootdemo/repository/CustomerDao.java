package com.nilsign.springbootdemo.repository;

import com.nilsign.springbootdemo.entity.CustomerEntity;

import java.util.List;
import java.util.Optional;

public interface CustomerDao {
  List<CustomerEntity> getAllCustomers();
  Optional<CustomerEntity> getCustomerById(long id);
  Optional<CustomerEntity> insertCustomer(CustomerEntity customer);
  Optional<CustomerEntity> updateCustomer(long id, CustomerEntity customer);
  Optional<CustomerEntity> deleteCustomerById(long id);
}
