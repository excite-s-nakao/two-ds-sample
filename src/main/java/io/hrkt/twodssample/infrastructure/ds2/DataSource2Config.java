package io.hrkt.twodssample.infrastructure.ds2;

import javax.sql.DataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@MapperScan(basePackages = {"io.hrkt.twodssample.infrastructure.ds2"},
    sqlSessionFactoryRef = DataSource2Config.SQL_SESSION_FACTORY_2)
public class DataSource2Config {
  public static final String SQL_SESSION_FACTORY_2 = "sqlSessionFactory2";
  public static final String DATA_SOURCE_2 = "datasource2";
  public static final String TX_MANAGER_2 = "txManager2";

  @Bean
  @ConfigurationProperties(prefix = "spring.datasource.datasource2")
  public DataSourceProperties datasource2Properties() {
    return new DataSourceProperties();
  }

  @Bean(name = {DATA_SOURCE_2})
  public DataSource datasource2(
      @Qualifier("datasource2Properties") DataSourceProperties properties) {
    return properties.initializeDataSourceBuilder().build();
  }

  @Bean(name = {TX_MANAGER_2})
  public PlatformTransactionManager txManager2(@Qualifier(DATA_SOURCE_2) DataSource dataSource2) {
    return new DataSourceTransactionManager(dataSource2);
  }

  @Bean(name = {SQL_SESSION_FACTORY_2})
  public SqlSessionFactory sqlSessionFactory(@Qualifier(DATA_SOURCE_2) DataSource datasource2)
      throws Exception {
    SqlSessionFactoryBean sqlSessionFactory = new SqlSessionFactoryBean();
    sqlSessionFactory.setDataSource(datasource2);
    return (SqlSessionFactory) sqlSessionFactory.getObject();
  }
}
