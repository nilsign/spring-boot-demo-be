package com.nilsign.springbootdemo.repository.postgres;

import com.nilsign.springbootdemo.repository.CustomerDao;
import com.nilsign.springbootdemo.entity.CustomerEntity;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Repository("postgresCustomerRepository")
public class CustomerRepository implements CustomerDao {
  @Override
  public List<CustomerEntity> getAllCustomers() {
    return Collections.emptyList();
  }

  @Override
  public Optional<CustomerEntity> getCustomerById(long id) {
    return Optional.empty();
  }

  @Override
  public Optional<CustomerEntity> insertCustomer(CustomerEntity customer) {
    return Optional.empty();
  }

  @Override
  public Optional<CustomerEntity> updateCustomer(long id, CustomerEntity customer) {
    return Optional.empty();
  }

  @Override
  public Optional<CustomerEntity> deleteCustomerById(long id) {
    return Optional.empty();
  }
}
