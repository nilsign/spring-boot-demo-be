package com.nilsign.springbootdemo.api;

import com.nilsign.springbootdemo.entity.CustomerEntity;
import com.nilsign.springbootdemo.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/customer")
public class CustomerController {

  private final CustomerService customerService;

  @Autowired
  public CustomerController(CustomerService customerService) {
    this.customerService = customerService;
  }

  @PostMapping
  public Optional<CustomerEntity> insertCustomer(
      @NotNull @Valid @RequestBody CustomerEntity customer) {
    return customerService.insertCustomer(customer);
  }

  @GetMapping
  public List<CustomerEntity> getAllCustomers() {
    return customerService.getAllCustomers();
  }

  @GetMapping(path = "{id}")
  public Optional<CustomerEntity> getCustomerById(@NotNull @PathVariable long id) {
    return customerService.getCustomerById(id);
  }

  @PutMapping(path = "{id}")
  public Optional<CustomerEntity> updateCustomer(
      @NotNull @PathVariable("id") long id,
      @NotNull @Valid @RequestBody CustomerEntity customer) {
    return customerService.updateCustomer(id, customer);
  }

  @DeleteMapping(path = "{id}")
  public Optional<CustomerEntity> deleteCustomerById(@NotNull @PathVariable("id") long id) {
    return customerService.deleteCustomerById(id);
  }
}
