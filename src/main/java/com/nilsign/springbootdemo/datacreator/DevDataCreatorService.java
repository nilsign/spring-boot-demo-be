package com.nilsign.springbootdemo.datacreator;

import com.nilsign.springbootdemo.dto.CustomerDto;
import com.nilsign.springbootdemo.dto.UserDto;
import com.nilsign.springbootdemo.entity.AddressEntity;
import com.nilsign.springbootdemo.entity.CustomerEntity;
import com.nilsign.springbootdemo.entity.RoleEntity;
import com.nilsign.springbootdemo.entity.RoleType;
import com.nilsign.springbootdemo.entity.UserEntity;
import com.nilsign.springbootdemo.service.CustomerEntityService;
import com.nilsign.springbootdemo.service.RoleEntityService;
import com.nilsign.springbootdemo.service.UserEntityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class DevDataCreatorService {

  private static final String BUYER_1_EMAIL = "budbuyman@gmail.com";

  @Autowired
  private EntityManager entityManager;

  @Autowired
  private UserEntityService userService;

  @Autowired
  private RoleEntityService roleService;

  @Autowired
  private CustomerEntityService customerService;

  @Transactional
  public void createIfNotExist() {
    log.info("Create DEV environment data");
    if (userService.findByEmail(BUYER_1_EMAIL).isEmpty()) {
      Optional<RoleEntity> buyerRole = roleService.findByRoleType(RoleType.BUYER);
      List<RoleEntity> roles = new ArrayList<>();
      roles.add(buyerRole.orElseThrow(
          () -> new RuntimeException("Illegal state. Missing buyer role.")));
      AddressEntity address = AddressEntity.builder()
          .address("Buymanns Street 7")
          .zip("1337")
          .city("Buymans Creek")
          .country("Germany")
          .build();
      CustomerEntity customer = CustomerEntity.builder()
          .postalAddress(address)
          .termsAndConditionsAccepted(true)
          .build();
      UserEntity user = UserEntity.builder()
          .firstName("Bud")
          .lastName("Buymann")
          .email(BUYER_1_EMAIL)
          .roles(roles)
          .customer(customer)
          .build();
      customer.setUser(user);
      entityManager.persist(user);
    }
  }

  @Transactional
  public void check() {
    log. warn("CHECKING CROSS DEPENDENCIES...");
    List<CustomerEntity> customers = customerService.findAll();
    Optional<UserEntity> user = userService.findByEmail(BUYER_1_EMAIL);
    log.warn("Customer: " + customers.get(0).toString());
    log.warn("Customer.USER: " + customers.get(0).getUser());
    log.warn("User: " + user.get());
    log.warn("User.CUSTOMER: " + user.get().getCustomer());

     log.warn("UserDto: " + UserDto.create(user.get()));
     log.warn("CustomerDto: " + CustomerDto.create(customers.get(0)));
  }
}
