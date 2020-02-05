//package com.nilsign.springbootdemo.config.security.oauth2;
//
//import com.nilsign.springbootdemo.domain.user.entity.UserEntity;
//import com.nilsign.springbootdemo.domain.user.service.UserEntityService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.AuthorityUtils;
//import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
//import org.springframework.security.core.authority.mapping.SimpleAuthorityMapper;
//import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
//import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
//import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
//import org.springframework.security.oauth2.core.OAuth2Error;
//import org.springframework.security.oauth2.core.OAuth2ErrorCodes;
//import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
//import org.springframework.security.oauth2.core.oidc.user.OidcUser;
//import org.springframework.security.oauth2.jwt.Jwt;
//import org.springframework.security.oauth2.jwt.JwtDecoder;
//import org.springframework.security.oauth2.jwt.JwtException;
//import org.springframework.util.CollectionUtils;
//
//import javax.transaction.Transactional;
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.Collections;
//import java.util.LinkedHashSet;
//import java.util.List;
//import java.util.Map;
//import java.util.Optional;
//import java.util.Set;
//import java.util.stream.Collectors;
//
//@RequiredArgsConstructor
//public class OAuth2LoggedInUserLoader extends OidcUserService {
//
//  @Autowired
//  private UserEntityService userEntityService;
//
//  private static final OAuth2Error INVALID_REQUEST
//      = new OAuth2Error(OAuth2ErrorCodes.INVALID_REQUEST);
//
//  private final JwtDecoder jwtDecoder;
//  private final GrantedAuthoritiesMapper authoritiesMapper;
//
//  /**
//   * Extends the {@link OidcUserService#loadUser(OidcUserRequest)} to add the authorities provided
//   * by Keycloak via the send JWT token, as Spring Boot Security 5.2.1 does not automatically sets
//   * either the realm's authority roles resources nor the resource server's authority roles. A hook
//   * to provide custom authorities is unfortunately also missing.
//   */
//  @Transactional
//  @Override
//  public OidcUser loadUser(OidcUserRequest userRequest) {
//    OidcUser user = super.loadUser(userRequest);
//    Set<GrantedAuthority> authorities = new LinkedHashSet<>();
//    authorities.addAll(user.getAuthorities());
//    authorities.addAll(extractOAuth2ProviderAuthoritiesFromAccessToken(userRequest));
//    authorities.addAll(getClientRolesFromDataSource(user));
//    return new DefaultOidcUser(
//        authorities,
//        userRequest.getIdToken(),
//        user.getUserInfo(),
//        "preferred_username");
//  }
//
//  private Collection<? extends GrantedAuthority> extractOAuth2ProviderAuthoritiesFromAccessToken(
//      OidcUserRequest userRequest) {
//    Jwt token = parseJwt(userRequest.getAccessToken().getTokenValue());
//    String clientId = userRequest.getClientRegistration().getClientId();
//    List<String> roles = getRealmRoles(token);
//    roles.addAll(getClientRoles(token, clientId));
//    if (CollectionUtils.isEmpty(roles)) {
//      return Collections.emptyList();
//    }
//    return toGrantedAuthorities(roles);
//  }
//
//  private Collection<? extends GrantedAuthority> getClientRolesFromDataSource(OidcUser user) {
//    List<String> clientRoles = new ArrayList<>();
//    Optional<UserEntity> userEntity = userEntityService.findByEmail(user.getEmail());
//    if (userEntity.isPresent()) {
//      clientRoles.addAll(userEntity.get().getRoles()
//          .stream()
//          .map(role -> role.getRoleType().name())
//          .collect(Collectors.toList()));
//    }
//    return toGrantedAuthorities(clientRoles);
//  }
//
//  private List<String> getRealmRoles(Jwt token) {
//    List<String> realmRoles = new ArrayList<>();
//    Map<String, Object> resourceMap = (Map<String, Object>) token.getClaims().get("realm_access");
//    if (!CollectionUtils.isEmpty(resourceMap)) {
//      realmRoles = (List<String>) resourceMap.get("roles");
//    }
//    return realmRoles;
//  }
//
//  private List<String> getClientRoles(Jwt token, String clientId) {
//    List<String> clientRoles = new ArrayList<>();
//    Map<String, Object> resourceMap
//        = (Map<String, Object>) token.getClaims().get("resource_access");
//    if (!CollectionUtils.isEmpty(resourceMap)) {
//      Map<String, Map<String, Object>> clientResource
//          = (Map<String, Map<String, Object>>) resourceMap.get(clientId);
//      if (!CollectionUtils.isEmpty(clientResource)) {
//        clientRoles = (List<String>) clientResource.get("roles");
//      }
//    }
//    return clientRoles;
//  }
//
//  private Collection<? extends GrantedAuthority> toGrantedAuthorities(List<String> roles) {
//    Collection<? extends GrantedAuthority> authorities
//        = AuthorityUtils.createAuthorityList(roles.toArray(new String[0]));
//    if (authoritiesMapper == null) {
//      return authorities;
//    }
//    ((SimpleAuthorityMapper) authoritiesMapper).setConvertToUpperCase(true);
//    return authoritiesMapper.mapAuthorities(authorities);
//  }
//
//  private Jwt parseJwt(String accessTokenValue) {
//    try {
//      return jwtDecoder.decode(accessTokenValue);
//    } catch (JwtException e) {
//      throw new OAuth2AuthenticationException(INVALID_REQUEST, e);
//    }
//  }
//}
