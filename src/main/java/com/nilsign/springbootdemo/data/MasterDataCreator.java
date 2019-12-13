package com.nilsign.springbootdemo.data;

import com.nilsign.springbootdemo.data.creator.UserDataCreator;
import com.nilsign.springbootdemo.service.UserEntityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Slf4j
@Service
public class MasterDataCreator {

  private static final String GLOBAL_ADMIN_FIRST_NAME = "Nils";
  private static final String GLOBAL_ADMIN_LAST_NAME = "Heumer";
  private static final String GLOBAL_ADMIN_EMAIL = "nilsign@gmail.com";

  @Value("${user.globaladmin.default.password}")
  private String globalAdminPassword;

  @Autowired
  private UserDataCreator userDataCreator;

  @Autowired
  private UserEntityService userEntityService;

  @Transactional
  public void createMasterDataIfNotExist() {
    if (userEntityService.findByEmail(GLOBAL_ADMIN_EMAIL).isPresent()) {
      log.info("Master data already exists - skip master data creation.");
      return;
    }
    log.info("Creating master data... ");
    userDataCreator.createGlobalAdminUserIfNotExists(
        GLOBAL_ADMIN_FIRST_NAME,
        GLOBAL_ADMIN_LAST_NAME,
        GLOBAL_ADMIN_EMAIL,
        globalAdminPassword);
    log.info("Master data creation done.");
  }
}
