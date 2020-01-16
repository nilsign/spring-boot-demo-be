package com.nilsign.springbootdemo.api.login;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.ModelAndView;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
public class OAuth2LoginController {

  @Autowired
  private OAuth2AuthorizedClientService authorizedClientService;

  @GetMapping("/")
  public ModelAndView index(Model model, OAuth2AuthenticationToken authentication) {
    OAuth2AuthorizedClient authorizedClient = this.getAuthorizedClient(authentication);
    model.addAttribute("userName", authentication.getName());
    model.addAttribute(
        "clientName",
        authorizedClient.getClientRegistration().getClientName());
    return new ModelAndView("index", model.asMap());
  }

  @PreAuthorize("hasRole('REALM_SUPERADMIN') OR hasRole('CLIENT_ADMIN')")
  @GetMapping("/user-info")
  public ModelAndView userInfo(Model model, OAuth2AuthenticationToken authentication) {
    Map<String, Object> userInfo = new HashMap<>();
    userInfo.put("userAttributes", getUserAttributes(authentication));
    userInfo.put("userAuthorities", getUserAuthorities(authentication));
    model.addAttribute("userInfo", userInfo);
    return new ModelAndView("user-info", userInfo);
  }

  private OAuth2AuthorizedClient getAuthorizedClient(OAuth2AuthenticationToken authentication) {
    return this.authorizedClientService.loadAuthorizedClient(
        authentication.getAuthorizedClientRegistrationId(),
        authentication.getName());
  }

  private ExchangeFilterFunction oauth2Credentials(OAuth2AuthorizedClient authorizedClient) {
    return ExchangeFilterFunction.ofRequestProcessor(
        clientRequest -> {
          String accessToken = "Bearer " + authorizedClient.getAccessToken().getTokenValue();
          ClientRequest authorizedRequest = ClientRequest.from(clientRequest)
              .header(HttpHeaders.AUTHORIZATION, accessToken)
              .build();
          return Mono.just(authorizedRequest);
        });
  }

  private Map<String, ?> getUserAttributes(OAuth2AuthenticationToken authentication) {
    OAuth2AuthorizedClient authorizedClient = this.getAuthorizedClient(authentication);
    Map<String, ?> userAttributes = Collections.emptyMap();
    String userInfoEndpointUri = authorizedClient.getClientRegistration()
        .getProviderDetails().getUserInfoEndpoint().getUri();
    if (!StringUtils.isEmpty(userInfoEndpointUri)) {
      userAttributes = WebClient.builder()
          .filter(oauth2Credentials(authorizedClient))
          .build()
          .get()
          .uri(userInfoEndpointUri)
          .retrieve()
          .bodyToMono(Map.class)
          .block();
    }
    return userAttributes;
  }

  private List<String> getUserAuthorities(OAuth2AuthenticationToken authentication) {
    List<String> authorities = new ArrayList<>();
    authentication.getPrincipal().getAuthorities().forEach(
        authority -> authorities.add(authority.getAuthority()));
    return authorities;
  }
}
