package com.nilsign.springbootdemo.repository.postgres;

import com.nilsign.springbootdemo.repository.CustomerDao;
import com.nilsign.springbootdemo.entity.CustomerEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("postgresCustomerRepository")
public class CustomerRepository implements CustomerDao {

  private final JdbcTemplate jdbcTemplate;

  @Autowired
  public CustomerRepository(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  @Override
  public List<CustomerEntity> getAllCustomers() {
    final String sql = "SELECT id, firstName, lastName, email FROM Customers";
    return jdbcTemplate.query(sql, (resultSet, i) -> {
      return new CustomerEntity(
          resultSet.getLong("id"),
          resultSet.getString("firstName"),
          resultSet.getString("lastName"),
          resultSet.getString("email"));
    });
  }

  @Override
  public Optional<CustomerEntity> getCustomerById(long id) {
    return Optional.empty();
  }

  @Override
  public Optional<CustomerEntity> insertCustomer(CustomerEntity customer) {
    final String sql = String.format(
        "INSERT INTO customers (firstName, lastName, email) VALUES ('%s', '%s', '%s')",
        customer.getFirstName(),
        customer.getLastName(),
        customer.getEmail());
    jdbcTemplate.update(sql);
    // TODO(nilsheumer): Return id of the inserted record.
    return Optional.empty();
  }

  @Override
  public Optional<CustomerEntity> updateCustomer(long id, CustomerEntity customer) {
    // TODO(nilsheumer): Return 0 when the customer hasn't been updated, otherwise 1 as one row has
    // been updated.
    return Optional.empty();
  }

  @Override
  public Optional<CustomerEntity> deleteCustomerById(long id) {
    // TODO(nilsheumer): Return 0 when the customer hasn't been deleted, otherwise 1 as one row has
    // been deleted.
    return Optional.empty();
  }
}
