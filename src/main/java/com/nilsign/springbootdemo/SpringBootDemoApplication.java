package com.nilsign.springbootdemo;

import com.nilsign.springbootdemo.datacreator.DevDataCreatorService;
import com.nilsign.springbootdemo.datacreator.MasterDataCreatorService;
import com.nilsign.springbootdemo.property.DataSourceProperties;
import com.nilsign.springbootdemo.property.EnvironmentProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@Slf4j
@SpringBootApplication
public class SpringBootDemoApplication {
  private final EnvironmentProperties environmentProperties;
  private final MasterDataCreatorService masterDataCreator;
  private final DevDataCreatorService devDataCreator;

  public SpringBootDemoApplication(
      EnvironmentProperties environmentProperties,
      DataSourceProperties dataSourceProperties,
      MasterDataCreatorService masterDataCreator,
      DevDataCreatorService devDataCreator) {
    log.info("Environment: " + environmentProperties.getEnvironment());
    log.info("Datasource url: " + dataSourceProperties.getUrl());
    log.info("Datasource user: " + dataSourceProperties.getUserName());
    this.environmentProperties = environmentProperties;
    this.masterDataCreator = masterDataCreator;
    this.devDataCreator = devDataCreator;
  }

  public static void main(String[] args) {
    SpringApplication.run(SpringBootDemoApplication.class, args);
  }

  @PostConstruct
  protected void initializeData() {
    masterDataCreator.createIfNotExist();
    if (environmentProperties.isDev()) {
      devDataCreator.createIfNotExist();
      devDataCreator.check();
    }
  }
}
