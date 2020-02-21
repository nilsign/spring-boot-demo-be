package com.nilsign.springbootdemo.property;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Getter
@Setter
@Configuration
@PropertySource("classpath:application.yaml")
@ConfigurationProperties(prefix = "keycloak")
public class KeycloakProperties {

  private String authServerUrl;

  private String realm;

  @Value( "${keycloak.resource}" )
  private String client;

  private int connectionPoolSize;
}
