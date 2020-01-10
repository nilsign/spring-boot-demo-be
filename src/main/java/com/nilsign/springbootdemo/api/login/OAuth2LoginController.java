package com.nilsign.springbootdemo.api.login;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.ModelAndView;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.Map;

@Slf4j
@Controller
@PreAuthorize("isAuthenticated()")
public class OAuth2LoginController {

  @Autowired
  private OAuth2AuthorizedClientService authorizedClientService;

  @GetMapping("/")
  public ModelAndView index(Model model, OAuth2AuthenticationToken authentication) {
    OAuth2AuthorizedClient authorizedClient = this.getAuthorizedClient(authentication);

    // TODO(nilsheumer): Remove once no longer required for debugging purposes.
    log.info("AccessTokenValue: " + authorizedClient.getAccessToken().getTokenValue());

    model.addAttribute("userName", authentication.getName());
    model.addAttribute(
        "clientName",
        authorizedClient.getClientRegistration().getClientName());
    return new ModelAndView("index", model.asMap());
  }

  // TODO(nilsheumer): Rename to "/user-info"
  @GetMapping("/userinfo")
  public ModelAndView userInfo(Model model, OAuth2AuthenticationToken authentication) {
    OAuth2AuthorizedClient authorizedClient = this.getAuthorizedClient(authentication);
    Map userAttributes = Collections.emptyMap();
    String userInfoEndpointUri = authorizedClient.getClientRegistration()
        .getProviderDetails().getUserInfoEndpoint().getUri();
    if (!StringUtils.isEmpty(userInfoEndpointUri)) {	// userInfoEndpointUri is optional for OIDC Clients
      userAttributes = WebClient.builder()
          .filter(oauth2Credentials(authorizedClient))
          .build()
          .get()
          .uri(userInfoEndpointUri)
          .retrieve()
          .bodyToMono(Map.class)
          .block();
    }
    model.addAttribute("userAttributes", userAttributes);
    return new ModelAndView("user-info", userAttributes);
  }

  @Transactional(readOnly = true)
  public OAuth2AuthorizedClient getAuthorizedClient(OAuth2AuthenticationToken authentication) {
    // TODO(nilsheumer): Remove once no longer required for debugging purposes.
    authentication.getPrincipal().getAuthorities().forEach(authority ->
          log.warn("AUTHORITY: " + authority.getAuthority()));

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
}
