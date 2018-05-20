package io.hrkt.twodssample.infrastructure.ds2;

import javax.sql.DataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class DataSource2Config {

  @Bean
  @ConfigurationProperties(prefix = "spring.datasource.datasource2")
  public DataSourceProperties datasource2Properties() {
    return new DataSourceProperties();
  }

  @Bean(name = {"datasource2"})
  @Autowired
  public DataSource datasource2(
      @Qualifier("datasource2Properties") DataSourceProperties properties) {
    return properties.initializeDataSourceBuilder().build();
  }

  @Bean(name = {"txManager2"})
  public PlatformTransactionManager txManager2(@Qualifier("datasource2") DataSource dataSource2) {
    return new DataSourceTransactionManager(dataSource2);
  }

  @Bean(name = {"sqlSessionFactory2"})
  public SqlSessionFactory sqlSessionFactory(@Qualifier("datasource2") DataSource datasource2)
      throws Exception {
    SqlSessionFactoryBean sqlSessionFactory = new SqlSessionFactoryBean();
    sqlSessionFactory.setDataSource(datasource2);
    return (SqlSessionFactory) sqlSessionFactory.getObject();
  }

  @Bean(name = {"todoMapper2"})
  public MapperFactoryBean<TodoMapper2> todoMapper2(
      @Qualifier("datasource2") DataSource datasource2) throws Exception {
    MapperFactoryBean<TodoMapper2> factoryBean = new MapperFactoryBean<>(TodoMapper2.class);
    factoryBean.setSqlSessionFactory(sqlSessionFactory(datasource2));
    return factoryBean;
  }
}
