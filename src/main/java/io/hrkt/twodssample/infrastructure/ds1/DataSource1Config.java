package io.hrkt.twodssample.infrastructure.ds1;

import javax.sql.DataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@MapperScan(basePackages = {"io.hrkt.twodssample.infrastructure.ds1"},
    sqlSessionFactoryRef = DataSource1Config.SQL_SESSION_FACTORY_1)
public class DataSource1Config {
  public static final String SQL_SESSION_FACTORY_1 = "sqlSessionFactory1";
  public static final String DATA_SOURCE_1 = "datasource1";
  public static final String TX_MANAGER_1 = "txManager1";
  
  @Bean
  @Primary
  @ConfigurationProperties(prefix = "spring.datasource.datasource1")
  public DataSourceProperties datasource1Properties() {
    return new DataSourceProperties();
  }

  @Bean(name = {DATA_SOURCE_1})
  @Primary
  public DataSource datasource1(
      @Qualifier("datasource1Properties") DataSourceProperties properties) {
    return properties.initializeDataSourceBuilder().build();
  }

  @Bean(name = {TX_MANAGER_1})
  @Primary
  public PlatformTransactionManager txManager1(@Qualifier(DATA_SOURCE_1) DataSource dataSource1) {
    return new DataSourceTransactionManager(dataSource1);
  }

  @Bean(name = {SQL_SESSION_FACTORY_1})
  @Primary
  public SqlSessionFactory sqlSessionFactory(@Qualifier(DATA_SOURCE_1) DataSource datasource1)
      throws Exception {
    SqlSessionFactoryBean sqlSessionFactory = new SqlSessionFactoryBean();
    sqlSessionFactory.setDataSource(datasource1);
    return (SqlSessionFactory) sqlSessionFactory.getObject();
  }
}
