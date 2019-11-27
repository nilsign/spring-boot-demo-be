package com.nilsign.springbootdemo.datacreator;

import com.nilsign.springbootdemo.entity.RoleEntity;
import com.nilsign.springbootdemo.entity.RoleType;
import com.nilsign.springbootdemo.entity.UserEntity;
import com.nilsign.springbootdemo.entity.helper.EntityArrayList;
import com.nilsign.springbootdemo.service.RoleService;
import com.nilsign.springbootdemo.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.Optional;

@Slf4j
@Service
@Transactional
public class MasterDataCreatorService {
  private static final String GLOBAL_ADMIN_FIRST_NAME = "Nils";
  private static final String GLOBAL_ADMIN_LAST_NAME = "Heumer";
  private static final String GLOBAL_ADMIN_EMAIL = "nilsign@gmail.com";

  @Autowired
  private EntityManager entityManager;

  @Autowired
  private UserService userService;

  @Autowired
  private RoleService roleService;

  @Transactional
  public void createIfNotExist() {
    log.info("Start master data creation...");
    this.createAdminUser();
  }

  private void createAdminUser() {
    Optional<UserEntity> user = userService.findByEmail(GLOBAL_ADMIN_EMAIL);
    if (user.isEmpty()) {
      Optional<RoleEntity> roleEntity = roleService.findByRoleType(RoleType.GLOBALADMIN);
      EntityArrayList<RoleEntity> roles = new EntityArrayList<>();
      roles.add(roleEntity.orElseThrow(
          () -> new RuntimeException("Illegal state. Missing global admin role.")));
      entityManager.persist(UserEntity.builder()
          .firstName(GLOBAL_ADMIN_FIRST_NAME)
          .lastName(GLOBAL_ADMIN_LAST_NAME)
          .email(GLOBAL_ADMIN_EMAIL)
          .roles(roles)
          .build());
    }
  }
}