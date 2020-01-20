package com.nilsign.springbootdemo.domain.customer.service;

import com.nilsign.springbootdemo.domain.customer.dto.CustomerDto;
import com.nilsign.springbootdemo.domain.customer.entity.CustomerEntity;
import com.nilsign.springbootdemo.domain.DtoService;
import com.nilsign.springbootdemo.domain.EntityService;
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

  @Override
  protected CustomerEntity toEntity(@NotNull CustomerDto customerDto) {
    return CustomerEntity.create(customerDto);
  }

  @Override
  protected CustomerDto toDto(@NotNull CustomerEntity customerEntity) {
    return CustomerDto.create(customerEntity);
  }
}
