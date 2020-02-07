package com.nilsign.springbootdemo.api.jsp;

import org.keycloak.representations.AccessToken;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static com.nilsign.springbootdemo.helper.KeycloakHelper.getLoggedInKeycloakUserAccessToken;

@Controller
public class LoginInfoController {

  @GetMapping("/")
  @PreAuthorize("isAuthenticated()")
  public ModelAndView index(Model model) {
    AccessToken token = getLoggedInKeycloakUserAccessToken();
    model.addAttribute("userName", token.getName());
    model.addAttribute("clientName", token.getIssuedFor());
    return new ModelAndView("index", model.asMap());
  }

  @GetMapping("/logged-in-user-info")
  @PreAuthorize("hasRole('REALM_SUPERADMIN') OR hasRole('REALM_CLIENT_ADMIN')")
  public ModelAndView userInfo(Model model) {
    AccessToken token = getLoggedInKeycloakUserAccessToken();
    Map<String, Object> userInfo = new HashMap<>();
    userInfo.put("userAttributes", getUserAttributes(token));
    userInfo.put("userKeycloakRealmRoles", getUserRealmRoles(token));
    userInfo.put("userKeycloakRealmClientRoles",
        getUserRealmClientRoles(token, token.issuedFor));
    userInfo.put("useSpringSecurityAuthorities", getSpringSecurityAuthorities());
    model.addAttribute("userInfo", userInfo);
    return new ModelAndView("logged-in-user-info", userInfo);
  }

  private Map<String, ?> getUserAttributes(AccessToken token) {
    return Map.of(
        "Subject", token.getSubject(),
        "Name", token.getName(),
        "Email", token.getEmail(),
        "Preferred Username", token.getPreferredUsername());
  }

  private Set<String> getUserRealmRoles(AccessToken token) {
    return token.getRealmAccess().getRoles();
  }

  private Set<String> getUserRealmClientRoles(AccessToken token, String clientName) {
    return token.getResourceAccess(clientName).getRoles();
  }

  private Set<String> getSpringSecurityAuthorities() {
    return AuthorityUtils.authorityListToSet(
        SecurityContextHolder.getContext().getAuthentication().getAuthorities());
  }
}
