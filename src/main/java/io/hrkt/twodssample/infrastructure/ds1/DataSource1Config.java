package io.hrkt.twodssample.infrastructure.ds1;

import javax.sql.DataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.mapper.MapperFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@MapperScan("io.hrkt.twodssample.infrastructure.ds1")
public class DataSource1Config {

  @Bean
  @Primary
  @ConfigurationProperties(prefix = "spring.datasource.datasource1")
  public DataSourceProperties datasource1Properties() {
    return new DataSourceProperties();
  }

  @Bean(name = {"datasource1"})
  @Primary
  @Autowired
  public DataSource datasource1(
      @Qualifier("datasource1Properties") DataSourceProperties properties) {
    return properties.initializeDataSourceBuilder().build();
  }

  @Bean(name = {"txManager1"})
  @Primary
  public PlatformTransactionManager txManager1(DataSource dataSource1) {
    return new DataSourceTransactionManager(dataSource1);
  }

  @Bean(name = {"sqlSessionFactory1"})
  @Primary
  public SqlSessionFactory sqlSessionFactory(@Qualifier("datasource1") DataSource datasource1)
      throws Exception {
    SqlSessionFactoryBean sqlSessionFactory = new SqlSessionFactoryBean();
    sqlSessionFactory.setDataSource(datasource1);
    return (SqlSessionFactory) sqlSessionFactory.getObject();
  }

  @Bean(name = {"todoMapper1"})
  @Primary
  public MapperFactoryBean<TodoMapper1> todoMapper1(
      @Qualifier("datasource1") DataSource datasource1) throws Exception {
    MapperFactoryBean<TodoMapper1> factoryBean = new MapperFactoryBean<>(TodoMapper1.class);
    factoryBean.setSqlSessionFactory(sqlSessionFactory(datasource1));
    return factoryBean;
  }

}
