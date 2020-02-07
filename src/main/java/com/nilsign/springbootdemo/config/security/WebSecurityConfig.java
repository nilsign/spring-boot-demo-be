package com.nilsign.springbootdemo.config.security;

import com.nilsign.springbootdemo.config.security.keycloak.KeycloakAuthenticationProviderImpl;
import com.nilsign.springbootdemo.domain.user.service.UserEntityService;
import org.keycloak.adapters.springsecurity.authentication.KeycloakAuthenticationProvider;
import org.keycloak.adapters.springsecurity.config.KeycloakWebSecurityConfigurerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.authority.mapping.SimpleAuthorityMapper;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.web.authentication.session.RegisterSessionAuthenticationStrategy;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends KeycloakWebSecurityConfigurerAdapter {

  @Autowired
  private UserEntityService userEntityService;

  @Autowired
  public void configureGlobal(AuthenticationManagerBuilder authenticationManagerBuilder) {
    authenticationManagerBuilder.authenticationProvider(keycloakAuthenticationProvider());
  }

  @Override
  protected void configure(final HttpSecurity http) throws Exception {
    // By setting the SessionCreationPolicy to NEVER, it is ensured that Spring Security will never
    // create a session itself but will use one if it already exists, as the Spring Boot application
    // still may create sessions.
    super.configure(http);
    http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.NEVER)
        .and().antMatcher("/**").authorizeRequests().anyRequest()
        .authenticated()
        .and().csrf();
  }

  @Override
  protected SessionAuthenticationStrategy sessionAuthenticationStrategy() {
    return new RegisterSessionAuthenticationStrategy(new SessionRegistryImpl());
  }

  /**
   * Ensures that the Keycloak roles and the JPA roles of a logged in user are added to the Spring
   * Security Context Authorities. This is required in order to user the @PreAuthorize annotation
   * (activated via the @EnableGlobalMethodSecurity annotation) on controller level.
   */
  @Override
  protected KeycloakAuthenticationProvider keycloakAuthenticationProvider() {
    SimpleAuthorityMapper grantedAuthorityMapper = new SimpleAuthorityMapper();
    grantedAuthorityMapper.setPrefix("ROLE_");
    grantedAuthorityMapper.setConvertToUpperCase(true);
    return new KeycloakAuthenticationProviderImpl(grantedAuthorityMapper, userEntityService);
  }
}
