package io.hrkt.twodssample.infrastructure.ds1;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class DataSource1Config {

  @Bean
  @Primary
  @ConfigurationProperties(prefix = "spring.datasource.datasource1")
  public DataSourceProperties datasource1Properties() {
    return new DataSourceProperties();
  }

  @Bean
  @Primary
  @Autowired
  public DataSource datasource1(@Qualifier("datasource1Properties")
      DataSourceProperties properties) {
    return properties.initializeDataSourceBuilder().build();
  }
}