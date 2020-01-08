package com.nilsign.springbootdemo.service;

import com.nilsign.springbootdemo.dto.CustomerDto;
import com.nilsign.springbootdemo.entity.CustomerEntity;
import com.nilsign.springbootdemo.service.base.DtoService;
import com.nilsign.springbootdemo.service.base.EntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;

@Service
public class CustomerDtoService extends DtoService<CustomerDto, CustomerEntity, Long> {

  @Autowired
  private CustomerEntityService customerEntityService;

  @Override
  protected EntityService<CustomerEntity, Long> getEntityService() {
    return customerEntityService;
  }

  // Here as well. See comment at AddressDtoService
  @Override
  protected CustomerEntity toEntity(@NotNull CustomerDto customerEntity) {
    return CustomerEntity.create(customerEntity);
  }

  @Override
  protected CustomerDto toDto(@NotNull CustomerEntity customerEntity) {
    return CustomerDto.create(customerEntity);
  }
}
