package com.nilsign.springbootdemo.property;

import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Configuration
@PropertySource("classpath:application.yaml")
@ConfigurationProperties(prefix = "environment")
public class EnvironmentProperties {

  private static final String DEV = "DEV";
  private static final String QA = "QA";
  private static final String PROD = "PROD";

  @Setter
  private String name;

  public EnvironmentType getEnvironment() {
    return EnvironmentType.get(name);
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

  public enum EnvironmentType {
    DEV,
    QA,
    PROD;

    private static EnvironmentType get(@NotNull @NotBlank String environment) {
      switch (environment) {
        case EnvironmentProperties.DEV:
          return EnvironmentType.DEV;
        case EnvironmentProperties.QA:
          return EnvironmentType.QA;
        case EnvironmentProperties.PROD:
          return EnvironmentType.PROD;
      }
      throw new RuntimeException("Unknown environment or unsupported environment profile.");
    }
  }
}
