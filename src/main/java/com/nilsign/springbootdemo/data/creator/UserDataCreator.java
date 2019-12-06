package com.nilsign.springbootdemo.data.creator;

import com.nilsign.springbootdemo.entity.AddressEntity;
import com.nilsign.springbootdemo.entity.CustomerEntity;
import com.nilsign.springbootdemo.entity.RoleEntity;
import com.nilsign.springbootdemo.entity.RoleType;
import com.nilsign.springbootdemo.entity.UserEntity;
import com.nilsign.springbootdemo.service.RoleEntityService;
import com.nilsign.springbootdemo.service.UserEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public final class UserDataCreator {

  @Autowired
  private UserEntityService userEntityService;

  @Autowired
  private RoleEntityService roleEntityService;

  public void createBuyerUserIfNotExist(
      @NotNull @NotBlank String firstName,
      @NotNull @NotBlank String lastName,
      @NotNull @NotBlank @Email String email,
      @NotNull @NotBlank String address,
      @NotNull @NotBlank String zip,
      @NotNull @NotBlank String city,
      @NotNull @NotBlank String country) {
    if (userEntityService.findByEmail(email).isEmpty()) {
      Optional<RoleEntity> buyerRole = roleEntityService.findByRoleType(RoleType.BUYER);
      Set<RoleEntity> roles = new HashSet<>();
      roles.add(buyerRole.orElseThrow(()
          -> new RuntimeException("Illegal state. Missing buyer role.")));
      AddressEntity addressEntity = AddressEntity.builder()
          .address(address)
          .zip(zip)
          .city(city)
          .country(country)
          .build();
      CustomerEntity customerEntity = CustomerEntity.builder()
          .postalAddress(addressEntity)
          .termsAndConditionsAccepted(true)
          .build();
      UserEntity userEntity = UserEntity.builder()
          .firstName(firstName)
          .lastName(lastName)
          .email(email)
          .roles(roles)
          .customer(customerEntity)
          .build();
      customerEntity.setUser(userEntity);
      userEntityService.save(userEntity).get();
    }
  }

  public void createGlobalAdminUserIfNotExists(
      @NotNull @NotBlank String firstName,
      @NotNull @NotBlank String lastName,
      @NotNull @NotBlank @Email String email) {
    if (userEntityService.findByEmail(email).isEmpty()) {
      Optional<RoleEntity> roleEntity = roleEntityService.findByRoleType(RoleType.GLOBALADMIN);
      Set<RoleEntity> roles = new HashSet<>();
      roles.add(roleEntity.orElseThrow(()
          -> new RuntimeException("Illegal state. Missing global admin role.")));
      userEntityService.save(UserEntity.builder()
          .firstName(firstName)
          .lastName(lastName)
          .email(email)
          .roles(roles)
          .build());
    }
  }
}
