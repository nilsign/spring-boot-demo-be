package com.nilsign.springbootdemo.config.security.keycloak;

import org.keycloak.adapters.springboot.KeycloakSpringBootConfigResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Ensures that Keycloak reads its configuration from the application.yaml. Note, this must be a
 * separated @Configuration annotated class.
 */
@Configuration
public class KeycloakConfig {

  @Bean
  public KeycloakSpringBootConfigResolver keycloakConfigResolver() {
    return new KeycloakSpringBootConfigResolver();
  }
}
