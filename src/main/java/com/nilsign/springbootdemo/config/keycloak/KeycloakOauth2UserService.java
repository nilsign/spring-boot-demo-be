package com.nilsign.springbootdemo.config.keycloak;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
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
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RequiredArgsConstructor
public class KeycloakOauth2UserService extends OidcUserService {

  private final static OAuth2Error INVALID_REQUEST
      = new OAuth2Error(OAuth2ErrorCodes.INVALID_REQUEST);

  private final JwtDecoder jwtDecoder;
  private final GrantedAuthoritiesMapper authoritiesMapper;

  /**
   * Extends the {@link OidcUserService#loadUser(OidcUserRequest)} to add the authorities provided
   * by Keycloak via the send JWT token, as Spring Boot Security 5.2.1 does not automatically sets
   * either the realm's authority roles resources nor the resource server's authority roles. A hook
   * to provide custom authorities is unfortunately also missing.
   */
  @Override
  public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
    OidcUser user = super.loadUser(userRequest);
    Set<GrantedAuthority> authorities = new LinkedHashSet<>();
    authorities.addAll(user.getAuthorities());
    authorities.addAll(extractKeycloakAuthoritiesFromAccessToken(userRequest));
    return new DefaultOidcUser(
        authorities,
        userRequest.getIdToken(),
        user.getUserInfo(),
        "preferred_username");
  }

  private Collection<? extends GrantedAuthority> extractKeycloakAuthoritiesFromAccessToken(
      OidcUserRequest userRequest) {
    Jwt token = parseJwt(userRequest.getAccessToken().getTokenValue());
    String clientId = userRequest.getClientRegistration().getClientId();

    // TODO(nilsheumer): Also add "realm_access" roles to the mapping. Ensure the client roles are
    // all named differently than the "resource_access" role.
    // Resource server client roles. User "realm_access" to request the realm roles.
    List<String> roles = getRealmRoles(token);
    roles.addAll(getClientRoles(token, clientId));
    if (CollectionUtils.isEmpty(roles)) {
      return Collections.emptyList();
    }
    Collection<? extends GrantedAuthority> authorities
        = AuthorityUtils.createAuthorityList(roles.toArray(new String[0]));
    if (authoritiesMapper == null) {
      return authorities;
    }
    return authoritiesMapper.mapAuthorities(authorities);
  }

  private List<String> getRealmRoles(Jwt token) {
    List<String> realmRoles = new ArrayList<>();
    Map<String, Object> resourceMap = (Map<String, Object>) token.getClaims().get("realm_access");
    if (!CollectionUtils.isEmpty(resourceMap)) {
      realmRoles = (List<String>) resourceMap.get("roles");
    }
    return realmRoles;
  }

  private List<String> getClientRoles(Jwt token, String clientId) {
    List<String> clientRoles = new ArrayList<>();
    Map<String, Object> resourceMap
        = (Map<String, Object>) token.getClaims().get("resource_access");
    if (!CollectionUtils.isEmpty(resourceMap)) {
      Map<String, Map<String, Object>> clientResource
          = (Map<String, Map<String, Object>>) resourceMap.get(clientId);
      if (!CollectionUtils.isEmpty(clientResource)) {
        clientRoles = (List<String>) clientResource.get("roles");
      }
    }
    return clientRoles;
  }

  private Jwt parseJwt(String accessTokenValue) {
    try {
      return jwtDecoder.decode(accessTokenValue);
    } catch (JwtException e) {
      throw new OAuth2AuthenticationException(INVALID_REQUEST, e);
    }
  }
}
