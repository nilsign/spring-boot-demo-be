package com.nilsign.springbootdemo.config;

import com.nilsign.springbootdemo.config.keycloak.KeycloakLogoutHandler;
import com.nilsign.springbootdemo.config.keycloak.KeycloakOauth2UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2ClientProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.mapping.SimpleAuthorityMapper;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired
  private KeycloakOauth2UserService keycloakOidcUserService;

  @Autowired
  private KeycloakLogoutHandler keycloakLogoutHandler;

  @Override
  protected void configure(final HttpSecurity http) throws Exception {
    // By setting the SessionCreationPolicy to NEVER, it is ensured that Spring Security will never
    // create a session itself but will use one if it already exists, as the Spring Boot application
    // still may create sessions.
    http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.NEVER)
        .and().antMatcher("/**").authorizeRequests().anyRequest().authenticated()
        .and().cors()
        .and().csrf()
        .and().logout().addLogoutHandler(keycloakLogoutHandler)
        .and().oauth2Login().userInfoEndpoint().oidcUserService(keycloakOidcUserService);
  }

  @Bean
  KeycloakOauth2UserService keycloakOidcUserService(OAuth2ClientProperties oauth2ClientProperties) {
    JwtDecoder jwtDecoder = NimbusJwtDecoder
        .withJwkSetUri(oauth2ClientProperties.getProvider().get("keycloak").getJwkSetUri())
        .build();
    SimpleAuthorityMapper authoritiesMapper = new SimpleAuthorityMapper();
    return new KeycloakOauth2UserService(jwtDecoder, authoritiesMapper);
  }

  @Bean
  KeycloakLogoutHandler keycloakLogoutHandler() {
    return new KeycloakLogoutHandler(new RestTemplate());
  }
}
