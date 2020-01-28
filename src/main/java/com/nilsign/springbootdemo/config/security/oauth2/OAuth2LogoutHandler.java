package com.nilsign.springbootdemo.config.security.oauth2;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * As Spring Boot Security 5.2.1 does not support Keycloak logouts via session endpoints, this
 * custom logout handler is required to properly redirect a logout to Keycloak.
 */
@Slf4j
@RequiredArgsConstructor
public class OAuth2LogoutHandler extends SecurityContextLogoutHandler {

  private final RestTemplate restTemplate;

  @Override
  public void logout(
      HttpServletRequest request,
      HttpServletResponse response,
      Authentication authentication) {
    super.logout(request, response, authentication);
    redirectLogoutToKeycloak((OidcUser) authentication.getPrincipal());
  }

  private void redirectLogoutToKeycloak(OidcUser user) {
    String endSessionEndpoint = user.getIssuer() + "/protocol/openid-connect/logout";
    UriComponentsBuilder builder = UriComponentsBuilder
        .fromUriString(endSessionEndpoint)
        .queryParam("id_token_hint", user.getIdToken().getTokenValue());
    ResponseEntity<String> logoutResponse
        = restTemplate.getForEntity(builder.toUriString(), String.class);
    if (!logoutResponse.getStatusCode().is2xxSuccessful()) {
      log.error("Failed to perform logout from keycloak session.");
    }
  }
}
