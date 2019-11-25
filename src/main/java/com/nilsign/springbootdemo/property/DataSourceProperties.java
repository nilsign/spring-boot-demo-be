package com.nilsign.springbootdemo.property;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Getter
@Setter
@Configuration
@PropertySource("classpath:application.properties")
@ConfigurationProperties(prefix = "spring.datasource")
public class DataSourceProperties {

  private String url;

  private String userName;

  private String password;
}
