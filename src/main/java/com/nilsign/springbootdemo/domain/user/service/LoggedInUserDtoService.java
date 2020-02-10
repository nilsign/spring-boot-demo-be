package com.nilsign.springbootdemo.domain.user.service;

import com.nilsign.springbootdemo.domain.role.RoleType;
import com.nilsign.springbootdemo.domain.role.dto.RoleDto;
import com.nilsign.springbootdemo.domain.role.entity.RoleEntity;
import com.nilsign.springbootdemo.domain.user.dto.UserDto;
import com.nilsign.springbootdemo.domain.user.entity.UserEntity;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.representations.AccessToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static com.nilsign.springbootdemo.helper.KeycloakHelper.getLoggedInKeycloakUserAccessToken;


@Slf4j
@Service
public class LoggedInUserDtoService {

  @Autowired
  private UserEntityService userEntityService;

  public UserDto getLoggedInUserDto() {
    AccessToken token = getLoggedInKeycloakUserAccessToken();
    UserEntity userEntity = userEntityService.findByEmail(token.getEmail())
        .orElseThrow(() -> new IllegalStateException(String.format(
            "No user found with the email address '%s'.",
            token.getEmail())));
    if (!userEntity.getFirstName().equals(token.getGivenName())) {
      log.warn(String.format(
          "The user's ('%s') given name in the OAuth2 provider ('%s') differs from the given name "
              + "in JPA datasource ('%s').",
          token.getEmail(),
          token.getGivenName(),
          userEntity.getFirstName()));
    }
    if (!userEntity.getLastName().equals(token.getFamilyName())) {
      log.warn(String.format(
          "The user's ('%s') family name in the OAuth2 provider ('%s') differs from the family name"
              + " in JPA datasource ('%s').",
          token.getEmail(),
          token.getFamilyName(),
          userEntity.getLastName()));
    }
    Set<RoleDto> combinedRoleDtos = combineJpaRolesAndOAuth2ProviderRoles(userEntity);
    return UserDto.builder()
        .id(userEntity.getId())
        .email(userEntity.getEmail())
        .firstName(userEntity.getFirstName())
        .lastName(userEntity.getLastName())
        .roles(combinedRoleDtos)
        .customerId(userEntity.getCustomer() != null
            ? userEntity.getCustomer().getId()
            : null)
        .build();
  }

  private Set<RoleType> getLoggedInUserRoleTypes() {
    return SecurityContextHolder.getContext().getAuthentication()
        .getAuthorities()
        .stream()
        .filter(grantedAuthority -> grantedAuthority.getAuthority()
            .startsWith(RoleType.ROLE_TYPE_NAME_PREFIX))
        .map(grantedAuthority -> RoleType.valueOf(grantedAuthority.getAuthority()))
        .collect(Collectors.toSet());
  }

  private Set<RoleDto> combineJpaRolesAndOAuth2ProviderRoles(UserEntity userEntity) {
    Set<RoleType> jpaRoles = userEntity.getRoles()
        .stream()
        .map(RoleEntity::getRoleType)
        .collect(Collectors.toSet());
    Set<RoleDto> combinedRoleDtos = userEntity.getRoles()
        .stream()
        .map(RoleDto::create)
        .collect(Collectors.toSet());
    combinedRoleDtos.addAll(this.getLoggedInUserRoleTypes()
        .stream()
        .filter(Predicate.not(jpaRoles::contains))
        .map(roleType -> RoleDto.builder().roleType(roleType).build())
        .collect(Collectors.toSet()));
    return combinedRoleDtos;
  }
}
