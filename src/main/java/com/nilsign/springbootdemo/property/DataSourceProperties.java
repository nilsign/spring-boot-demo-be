package com.nilsign.springbootdemo.property;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:application.properties")
@ConfigurationProperties(prefix = "spring.datasource")
public class DataSourceProperties {
  @Getter @Setter
  private String url;

  @Getter @Setter
  private String userName;

  @Getter @Setter
  private String password;
}
