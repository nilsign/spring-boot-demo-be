package com.nilsign.springbootdemo.api;

import com.nilsign.springbootdemo.dto.CustomerDto;
import com.nilsign.springbootdemo.entity.CustomerEntity;
import com.nilsign.springbootdemo.service.AbstractService;
import com.nilsign.springbootdemo.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/customer")
public class CustomerController extends AbstractController<CustomerDto, CustomerEntity, Long> {
  @Autowired
  private CustomerService orderService;

  @Override
  protected AbstractService<CustomerEntity, Long> getService() {
    return orderService;
  }
}
