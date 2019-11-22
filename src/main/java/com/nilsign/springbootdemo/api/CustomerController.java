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

  @Override
  protected CustomerEntity entityFromDto(CustomerDto dto) {
    return CustomerController.customerEntityFromDto(dto);
  }

  @Override
  protected CustomerDto dtoFromEntity(CustomerEntity entity) {
    return CustomerController.customerDtoFromEntity(entity);
  }

  public static CustomerEntity customerEntityFromDto(CustomerDto dto) {
    CustomerEntity entity = new CustomerEntity();
    entity.setId(dto.getId());
    entity.setUser(UserController.userEntityFromDto(dto.getUser()));
    entity.setTermsAndConditionsAccepted(dto.isTermsAndConditionsAccepted());
    entity.setPostalAddress(AddressController.addressEntityFromDto(dto.getPostalAddress()));
    return entity;
  }

  public static CustomerDto customerDtoFromEntity(CustomerEntity entity) {
    return new CustomerDto(
        entity.getId(),
        UserController.userDtoFromEntity(entity.getUser()),
        entity.isTermsAndConditionsAccepted(),
        AddressController.addressDtoFromEntity(entity.getPostalAddress()));
  }
}
