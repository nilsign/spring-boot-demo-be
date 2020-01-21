package com.nilsign.springbootdemo.api.rest;

import com.nilsign.springbootdemo.api.rest.base.Controller;
import com.nilsign.springbootdemo.domain.customer.dto.CustomerDto;
import com.nilsign.springbootdemo.domain.customer.entity.CustomerEntity;
import com.nilsign.springbootdemo.domain.customer.service.CustomerDtoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/customer")
public class CustomerController extends Controller<CustomerDto, CustomerEntity, Long> {

  @Autowired
  private CustomerDtoService customerDtoService;

  @Override
  protected CustomerDtoService getDtoService() {
    return customerDtoService;
  }
}
