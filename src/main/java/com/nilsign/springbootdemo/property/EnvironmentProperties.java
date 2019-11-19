package com.nilsign.springbootdemo.property;

import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;


@Configuration
@PropertySource("classpath:application.properties")
@ConfigurationProperties(prefix = "environment")
public class EnvironmentProperties {

  public static final String DEV = "DEV";
  public static final String QA = "QA";
  public static final String PROD = "PROD";

  public enum EnvironmentType {
    DEV,
    QA,
    PROD;

    public static EnvironmentType fromString(String environment) {
      switch(environment) {
        case EnvironmentProperties.DEV: return EnvironmentType.DEV;
        case EnvironmentProperties.QA: return EnvironmentType.QA;
        case EnvironmentProperties.PROD: return EnvironmentType.PROD;
      }
      throw new RuntimeException("Unknown environment or unsupported environment profile.");
    }
  }

  @Setter
  private String name;

  public EnvironmentType getEnvironment() {
    return EnvironmentType.fromString(name);
  }

  public boolean isDev() {
    return getEnvironment() == EnvironmentType.DEV;
  }

  public boolean isQa() {
    return getEnvironment() == EnvironmentType.QA;
  }

  public boolean isProd() {
    return getEnvironment() == EnvironmentType.PROD;
  }
}
