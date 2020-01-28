package com.nilsign.springbootdemo.domain.user.service;

import com.nilsign.springbootdemo.domain.role.RoleType;
import com.nilsign.springbootdemo.domain.role.dto.RoleDto;
import com.nilsign.springbootdemo.domain.user.dto.UserDto;
import com.nilsign.springbootdemo.domain.user.entity.UserEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Slf4j
@Service
public class LoggedInUserDtoService {

  @Autowired
  private UserEntityService userEntityService;

  public UserDto getLoggedInUserDto() {
    DefaultOidcUser loggedInUser = this.getLoggedInOidcUser();
    UserEntity userEntity = userEntityService.findByEmail(loggedInUser.getUserInfo().getEmail())
        .orElseThrow(() -> new IllegalStateException(String.format(
            "No user found with the email address '%s'.",
            loggedInUser.getUserInfo().getEmail())));
    if (!userEntity.getFirstName().equals(loggedInUser.getGivenName())) {
      log.warn(String.format(
          "The user's ('$s') given name in the OAuth2 provider ('%s') differs from the given name "
              + "in JPA datasource ('%s').",
          loggedInUser.getUserInfo().getEmail()),
          loggedInUser.getGivenName(),
          userEntity.getFirstName());
    }
    if (!userEntity.getLastName().equals(loggedInUser.getFamilyName())) {
      log.warn(String.format(
          "The user's ('$s') family name in the OAuth2 provider ('%s') differs from the family name"
              + " in JPA datasource ('%s').",
          loggedInUser.getUserInfo().getEmail()),
          loggedInUser.getFamilyName(),
          userEntity.getLastName());
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

  private DefaultOidcUser getLoggedInOidcUser() {
    return (DefaultOidcUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
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
        .map(RoleDto::create)
        .collect(Collectors.toSet())
        .stream()
        .map(roleDto -> RoleType.valueOf(roleDto.getRoleType().name()))
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
