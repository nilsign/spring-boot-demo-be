package com.nilsign.springbootdemo.api;

import com.nilsign.springbootdemo.entity.CustomerEntity;
import com.nilsign.springbootdemo.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

  @GetMapping
  public List<CustomerEntity> findAll() {
    return customerService.findAll();
  }

  @GetMapping(path = "{id}")
  public Optional<CustomerEntity> getById(
      @NotNull @PathVariable long id) {
    return customerService.findById(id);
  }

  @PostMapping
  public Optional<CustomerEntity> insert(
      @NotNull @Valid @RequestBody CustomerEntity customer) {
    return customerService.save(customer);
  }

  @PutMapping
  public Optional<CustomerEntity> update(
      @NotNull @Valid @RequestBody CustomerEntity customer) {
    return customerService.save(customer);
  }

  @DeleteMapping(path = "{id}")
  public void deleteById(
      @NotNull @PathVariable("id") long id) {
    customerService.deleteById(id);
  }
}
