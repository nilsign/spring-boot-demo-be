package com.nilsign.springbootdemo.data;

import com.nilsign.springbootdemo.data.creator.UserDataCreator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Slf4j
@Service
public class MasterDataCreator {

  private static final String GLOBAL_ADMIN_FIRST_NAME = "Nils";
  private static final String GLOBAL_ADMIN_LAST_NAME = "Heumer";
  private static final String GLOBAL_ADMIN_EMAIL = "nilsign@gmail.com";

  @Autowired
  private UserDataCreator userDataCreator;

  @Transactional
  public void createMasterDataIfNotExist() {
    log.info("Master data creation...");
    userDataCreator.createGlobalAdminUserIfNotExists(
        GLOBAL_ADMIN_FIRST_NAME,
        GLOBAL_ADMIN_LAST_NAME,
        GLOBAL_ADMIN_EMAIL);
    }
}
