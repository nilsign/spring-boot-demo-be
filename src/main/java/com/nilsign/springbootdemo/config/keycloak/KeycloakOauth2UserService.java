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

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RequiredArgsConstructor
public class KeycloakOauth2UserService extends OidcUserService {

  private final JwtDecoder jwtDecoder;
  private final OAuth2Error INVALID_REQUEST = new OAuth2Error(OAuth2ErrorCodes.INVALID_REQUEST);
  private final GrantedAuthoritiesMapper authoritiesMapper;

  /**
   * Extends the {@link OidcUserService#loadUser(OidcUserRequest)} to add authorities provided by
   * Keycloak via the JWT token as Spring Boot Security 5.2.1 does not automatically sets either the
   * claims authority roles resources nor the resource server clients authority roles. A hook to
   * provide custom authorities is unfortunately also missing.
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

    // TODO(nilsheumer): Also add "realm_access" roles to the mapping. Ensure the client roles are
    // all named differently than the "resource_access" role.
    // Resource server client roles. User "realm_access" to request the realm roles.
    @SuppressWarnings("unchecked")
    Map<String, Object> resourceMap
        = (Map<String, Object>) token.getClaims().get("resource_access");
    String clientId = userRequest.getClientRegistration().getClientId();

    @SuppressWarnings("unchecked")
    Map<String, Map<String, Object>> clientResource
        = (Map<String, Map<String, Object>>) resourceMap.get(clientId);
    if (CollectionUtils.isEmpty(clientResource)) {
      return Collections.emptyList();
    }
    @SuppressWarnings("unchecked")
    List<String> clientRoles = (List<String>) clientResource.get("roles");
    if (CollectionUtils.isEmpty(clientRoles)) {
      return Collections.emptyList();
    }
    Collection<? extends GrantedAuthority> authorities
        = AuthorityUtils.createAuthorityList(clientRoles.toArray(new String[0]));
    if (authoritiesMapper == null) {
      return authorities;
    }

    return authoritiesMapper.mapAuthorities(authorities);
  }

  private Jwt parseJwt(String accessTokenValue) {
    try {
      return jwtDecoder.decode(accessTokenValue);
    } catch (JwtException e) {
      throw new OAuth2AuthenticationException(INVALID_REQUEST, e);
    }
  }
}
