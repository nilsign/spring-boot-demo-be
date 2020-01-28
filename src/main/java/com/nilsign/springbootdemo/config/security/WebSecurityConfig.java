package com.nilsign.springbootdemo.config.security;

import com.nilsign.springbootdemo.config.security.oauth2.OAuth2LogoutHandler;
import com.nilsign.springbootdemo.config.security.oauth2.OAuth2LoggedInUserLoader;
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
  private OAuth2LoggedInUserLoader keycloakOidcUserService;

  @Autowired
  private OAuth2LogoutHandler OAuth2LogoutHandler;

  @Override
  protected void configure(final HttpSecurity http) throws Exception {
    // By setting the SessionCreationPolicy to NEVER, it is ensured that Spring Security will never
    // create a session itself but will use one if it already exists, as the Spring Boot application
    // still may create sessions.
    http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.NEVER)
        .and().antMatcher("/**").authorizeRequests().anyRequest().authenticated()
        .and().csrf()
        .and().logout().addLogoutHandler(OAuth2LogoutHandler)
        .and().oauth2Login().userInfoEndpoint().oidcUserService(keycloakOidcUserService);
  }

  @Bean
  OAuth2LoggedInUserLoader keycloakOidcUserService(OAuth2ClientProperties oauth2ClientProperties) {
    JwtDecoder jwtDecoder = NimbusJwtDecoder
        .withJwkSetUri(oauth2ClientProperties.getProvider().get("keycloak").getJwkSetUri())
        .build();
    SimpleAuthorityMapper authoritiesMapper = new SimpleAuthorityMapper();
    return new OAuth2LoggedInUserLoader(jwtDecoder, authoritiesMapper);
  }

  @Bean
  OAuth2LogoutHandler keycloakLogoutHandler() {
    return new OAuth2LogoutHandler(new RestTemplate());
  }
}
