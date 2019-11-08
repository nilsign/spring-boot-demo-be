package com.nilsign.springbootdemo.service;

import com.nilsign.springbootdemo.repository.CustomerDao;
import com.nilsign.springbootdemo.entity.CustomerEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

  private final CustomerDao customerDao;

  @Autowired
  public CustomerService(@Qualifier("postgresCustomerRepository") CustomerDao customerDao) {
    this.customerDao = customerDao;
  }

  public Optional<CustomerEntity> insertCustomer(CustomerEntity customer) {
    return customerDao.insertCustomer(customer);
  }

  public List<CustomerEntity> getAllCustomers() {
    return customerDao.getAllCustomers();
  }

  public Optional<CustomerEntity> getCustomerById(long id) {
    return customerDao.getCustomerById(id);
  }

  public Optional<CustomerEntity> updateCustomer(long id, CustomerEntity customer) {
    return customerDao.updateCustomer(id, customer);
  }

  public Optional<CustomerEntity> deleteCustomerById(long id) {
    return customerDao.deleteCustomerById(id);
  }
}
