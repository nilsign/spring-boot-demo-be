package com.nilsign.springbootdemo;

import com.nilsign.springbootdemo.data.DevDataCreator;
import com.nilsign.springbootdemo.data.MasterDataCreator;
import com.nilsign.springbootdemo.property.EnvironmentProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

@Slf4j
@SpringBootApplication
public class SpringBootDemoApplication {

  private EnvironmentProperties environmentProperties;
  private MasterDataCreator masterDataCreator;
  private DevDataCreator devDataCreator;

  public SpringBootDemoApplication(
      EnvironmentProperties environmentProperties,
      MasterDataCreator masterDataCreator,
      DevDataCreator devDataCreator) {
    this.environmentProperties = environmentProperties;
    this.masterDataCreator = masterDataCreator;
    this.devDataCreator = devDataCreator;
  }

  @PostConstruct
  protected void initializeData() {
    masterDataCreator.createMasterDataIfNotExist();
    if (environmentProperties.isDev()) {
      devDataCreator.createDevDataIfNotExist();
    }
  }

  public static void main(String[] args) {
    SpringApplication.run(SpringBootDemoApplication.class, args);
  }
}
