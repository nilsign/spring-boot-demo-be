package com.nilsign.springbootdemo.helper;

import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.representations.AccessToken;
import org.springframework.security.core.context.SecurityContextHolder;

public final class KeycloakHelper {

  private KeycloakHelper() {
  }

  public static KeycloakPrincipal<KeycloakSecurityContext> getLoggedInOidcUser() {
    return (KeycloakPrincipal<KeycloakSecurityContext>)
        SecurityContextHolder.getContext().getAuthentication().getPrincipal();
  }

  public static KeycloakSecurityContext getLoggedInOidcUserSecurityContext() {
    return getLoggedInOidcUser().getKeycloakSecurityContext();
  }

  public static AccessToken getLoggedInOidcUserToken() {
    return getLoggedInOidcUserSecurityContext().getToken();
  }
}
