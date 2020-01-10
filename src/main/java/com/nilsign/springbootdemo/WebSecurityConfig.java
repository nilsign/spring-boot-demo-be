package com.nilsign.springbootdemo;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2ClientProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.authority.mapping.SimpleAuthorityMapper;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.OAuth2ErrorCodes;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoderJwkSupport;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Slf4j
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired
  KeycloakOauth2UserService keycloakOidcUserService;

  @Autowired
  KeycloakLogoutHandler keycloakLogoutHandler;

  protected void configure(final HttpSecurity http) throws Exception {
    http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
        .and().antMatcher("/**").authorizeRequests().anyRequest().permitAll()
        .and().logout().addLogoutHandler(keycloakLogoutHandler)
        .and().oauth2Login().userInfoEndpoint().oidcUserService(keycloakOidcUserService);
  }

  @Bean
  KeycloakOauth2UserService keycloakOidcUserService(OAuth2ClientProperties oauth2ClientProperties) {

    // TODO use default JwtDecoder - where to grab?
    NimbusJwtDecoderJwkSupport jwtDecoder = new NimbusJwtDecoderJwkSupport(
        oauth2ClientProperties.getProvider().get("keycloak").getJwkSetUri());

    SimpleAuthorityMapper authoritiesMapper = new SimpleAuthorityMapper();
    authoritiesMapper.setConvertToUpperCase(true);

    return new KeycloakOauth2UserService(jwtDecoder, authoritiesMapper);
  }

  @Bean
  KeycloakLogoutHandler keycloakLogoutHandler() {
    return new KeycloakLogoutHandler(new RestTemplate());
  }
}


@Slf4j
@RequiredArgsConstructor
class KeycloakOauth2UserService extends OidcUserService {

  private final OAuth2Error INVALID_REQUEST = new OAuth2Error(OAuth2ErrorCodes.INVALID_REQUEST);

  private final JwtDecoder jwtDecoder;

  private final GrantedAuthoritiesMapper authoritiesMapper;

  /**
   * Augments {@link OidcUserService#loadUser(OidcUserRequest)} to add authorities
   * provided by Keycloak.
   *
   * Needed because {@link OidcUserService#loadUser(OidcUserRequest)} (currently)
   * does not provide a hook for adding custom authorities from a
   * {@link OidcUserRequest}.
   */
  @Override
  public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {

    OidcUser user = super.loadUser(userRequest);

    Set<GrantedAuthority> authorities = new LinkedHashSet<>();
    authorities.addAll(user.getAuthorities());
    authorities.addAll(extractKeycloakAuthorities(userRequest));

    authorities.forEach(grantedAuthority ->
        log.error("LOAD USER: " + grantedAuthority.getAuthority().toString()));

    return new DefaultOidcUser(authorities, userRequest.getIdToken(), user.getUserInfo(), "preferred_username");
  }

  /**
   * Extracts {@link GrantedAuthority GrantedAuthorities} from the AccessToken in
   * the {@link OidcUserRequest}.
   *
   * @param userRequest
   * @return
   */
  private Collection<? extends GrantedAuthority> extractKeycloakAuthorities(OidcUserRequest userRequest) {

    Jwt token = parseJwt(userRequest.getAccessToken().getTokenValue());

    // Would be great if Spring Security would provide something like a plugable
    // OidcUserRequestAuthoritiesExtractor interface to hide the junk below...

    @SuppressWarnings("unchecked")
    Map<String, Object> resourceMap = (Map<String, Object>) token.getClaims().get("resource_access");
    String clientId = userRequest.getClientRegistration().getClientId();

    @SuppressWarnings("unchecked")
    Map<String, Map<String, Object>> clientResource = (Map<String, Map<String, Object>>) resourceMap.get(clientId);
    if (CollectionUtils.isEmpty(clientResource)) {
      return Collections.emptyList();
    }

    @SuppressWarnings("unchecked")
    List<String> clientRoles = (List<String>) clientResource.get("roles");
    if (CollectionUtils.isEmpty(clientRoles)) {
      return Collections.emptyList();
    }

    Collection<? extends GrantedAuthority> authorities = AuthorityUtils
        .createAuthorityList(clientRoles.toArray(new String[0]));
    if (authoritiesMapper == null) {
      return authorities;
    }

    return authoritiesMapper.mapAuthorities(authorities);
  }

  private Jwt parseJwt(String accessTokenValue) {
    try {
      // Token is already verified by spring security infrastructure
      return jwtDecoder.decode(accessTokenValue);
    } catch (JwtException e) {
      throw new OAuth2AuthenticationException(INVALID_REQUEST, e);
    }
  }
}

/**
 * Propagates logouts to Keycloak.
 *
 * Necessary because Spring Security 5 (currently) doesn't support
 * end-session-endpoints.
 */
@Slf4j
@RequiredArgsConstructor
class KeycloakLogoutHandler extends SecurityContextLogoutHandler {

  private final RestTemplate restTemplate;

  @Override
  public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
    super.logout(request, response, authentication);

    propagateLogoutToKeycloak((OidcUser) authentication.getPrincipal());
  }

  private void propagateLogoutToKeycloak(OidcUser user) {

    String endSessionEndpoint = user.getIssuer() + "/protocol/openid-connect/logout";

    UriComponentsBuilder builder = UriComponentsBuilder
        .fromUriString(endSessionEndpoint)
        .queryParam("id_token_hint", user.getIdToken().getTokenValue());

    ResponseEntity<String> logoutResponse
        = restTemplate.getForEntity(builder.toUriString(), String.class);
    if (logoutResponse.getStatusCode().is2xxSuccessful()) {
      log.info("Successfully logged out in Keycloak");
    } else {
      log.info("Could not propagate logout to Keycloak");
    }
  }
}
