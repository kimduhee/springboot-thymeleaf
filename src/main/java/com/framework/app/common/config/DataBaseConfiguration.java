package com.framework.app.common.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.RequiredArgsConstructor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.*;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

@RequiredArgsConstructor
@Configuration
@Lazy
@PropertySource("classpath:/application-${spring.profiles.active}.yml")
public class DataBaseConfiguration {

    private final ApplicationContext applicationContext;

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.hikari")
    public HikariConfig hikariConfig() {
        return new HikariConfig();
    }

    @Bean(name="appDataSource")
    @Primary
    public DataSource appDataSource(HikariConfig config) {
        DataSource dataSource = new HikariDataSource(config);
        return dataSource;
    }

    @Bean
    @Primary
    public SqlSessionFactory appSessionFactory(@Autowired @Qualifier("appDataSource")DataSource dataSource) throws Exception {
        SqlSessionFactoryBean sessionFactoryBean = new SqlSessionFactoryBean();
        sessionFactoryBean.setDataSource(dataSource);
        sessionFactoryBean.setMapperLocations(applicationContext.getResources("classpath:/mybatis/mappers/*.xml"));
        sessionFactoryBean.setConfigLocation(applicationContext.getResource("classpath:/mybatis/mybatis-config.xml"));
        //sessionFactoryBean.setTypeAliasesPackage("com.framework.app");
        return sessionFactoryBean.getObject();
    }

    @Bean
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sessionFactory) {
        return new SqlSessionTemplate(sessionFactory);
    }

    @Bean
    @Primary
    public DataSourceTransactionManager appDataSourceTransactionManager(@Autowired @Qualifier("appDataSource")DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }
}
