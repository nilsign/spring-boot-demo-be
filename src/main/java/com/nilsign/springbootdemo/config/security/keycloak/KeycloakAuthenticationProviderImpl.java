package com.nilsign.springbootdemo.config.security.keycloak;

import com.nilsign.springbootdemo.domain.user.entity.UserEntity;
import com.nilsign.springbootdemo.domain.user.service.UserEntityService;
import com.nilsign.springbootdemo.helper.KeycloakHelper;
import lombok.RequiredArgsConstructor;
import org.keycloak.adapters.springsecurity.authentication.KeycloakAuthenticationProvider;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.keycloak.representations.AccessToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.authority.mapping.SimpleAuthorityMapper;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Ensures that the Keycloak roles and the JPA roles of a logged in user are added to the Spring
 * Security Context Authorities. This is required in order to user the @PreAuthorize annotation
 * (activated via the @EnableGlobalMethodSecurity annotation) on controller level.
 */
@RequiredArgsConstructor
public class KeycloakAuthenticationProviderImpl extends KeycloakAuthenticationProvider {

  private final GrantedAuthoritiesMapper grantedAuthorityMapper;
  private final UserEntityService userEntityService;

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    KeycloakAuthenticationToken token = (KeycloakAuthenticationToken) authentication;
    Set<GrantedAuthority> authorities = new HashSet<>();
    authorities.addAll(getKeycloakRealmRolesToAuthorities());
    authorities.addAll(getKeycloakRealmClientRolesToAuthorities());
    authorities.addAll(getRolesFromJpaDataSource());
    return new KeycloakAuthenticationToken(
        token.getAccount(),
        token.isInteractive(),
        authorities);
  }

  private Collection<? extends GrantedAuthority> getKeycloakRealmRolesToAuthorities() {
    AccessToken accessToken = KeycloakHelper.getLoggedInOidcUserToken();
    Set<String> realmRoles = accessToken.getRealmAccess().getRoles();
    return toGrantedAuthorities(realmRoles);
  }

  private Collection<? extends GrantedAuthority> getKeycloakRealmClientRolesToAuthorities() {
    AccessToken accessToken = KeycloakHelper.getLoggedInOidcUserToken();
    Set<String> realmClientRoles = accessToken.getResourceAccess(accessToken.issuedFor).getRoles();
    return toGrantedAuthorities(realmClientRoles);
  }

  Collection<? extends GrantedAuthority> getRolesFromJpaDataSource() {
    AccessToken accessToken = KeycloakHelper.getLoggedInOidcUserToken();
    Optional<UserEntity> userEntity = userEntityService.findByEmail(accessToken.getEmail());
    return userEntity.isPresent()
        ? toGrantedAuthorities(userEntity.get().getRoles()
            .stream()
            .map(role -> role.getRoleType().name())
            .collect(Collectors.toSet()))
        : Collections.emptySet();
  }

  private Collection<? extends GrantedAuthority> toGrantedAuthorities(Set<String> roles) {
    Collection<GrantedAuthority> authorities = AuthorityUtils
        .createAuthorityList(roles.toArray(new String[0]));
    ((SimpleAuthorityMapper) grantedAuthorityMapper).setConvertToUpperCase(true);
    return grantedAuthorityMapper.mapAuthorities(authorities);
  }
}
