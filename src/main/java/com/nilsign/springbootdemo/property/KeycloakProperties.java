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

  @Value( "${keycloakRealmManagementClient}" )
  private String keycloakRealmManagementClient;

  @Value( "${keycloak.resource}" )
  private String keycloakBackendClient;

  @Value( "${keycloakAngularFrontendClient}" )
  private String keycloakAngularFrontendClient;

  private int connectionPoolSize;
}
