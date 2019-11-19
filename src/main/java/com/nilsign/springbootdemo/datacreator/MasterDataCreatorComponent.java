package com.nilsign.springbootdemo.datacreator;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MasterDataCreatorComponent {
  public void createIfNotExist() {
    log. info("MasterDataCreatorComponent::createIfNotExist()");
  }
}
