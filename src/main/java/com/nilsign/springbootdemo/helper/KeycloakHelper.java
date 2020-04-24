package com.nilsign.springbootdemo.helper;

import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.representations.AccessToken;
import org.springframework.security.core.context.SecurityContextHolder;

public final class KeycloakHelper {

  private KeycloakHelper() {
  }

  public static KeycloakPrincipal<KeycloakSecurityContext> getLoggedInKeycloakUser() {
    return (KeycloakPrincipal<KeycloakSecurityContext>)
        SecurityContextHolder.getContext().getAuthentication().getPrincipal();
  }

  public static KeycloakSecurityContext getLoggedInKeycloakUserSecurityContext() {
    return getLoggedInKeycloakUser().getKeycloakSecurityContext();
  }

  public static AccessToken getLoggedInKeycloakUserAccessToken() {
    return getLoggedInKeycloakUserSecurityContext().getToken();
  }
}
