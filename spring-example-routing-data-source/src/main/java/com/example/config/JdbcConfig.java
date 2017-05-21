package com.example.config;

import com.example.datasource.RoutingDataSource;
import com.example.datasource.RoutingDataSourceAspect;
import com.example.datasource.RoutingDataSourceTransactionManager;
import com.example.datasource.RoutingDataSourceType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableAspectJAutoProxy
@EnableTransactionManagement
public class JdbcConfig {

    @Bean
    public DriverManagerDataSource writeDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/test");
        dataSource.setUsername("root");
        dataSource.setPassword("123456");
        return dataSource;
    }

    @Bean
    public DriverManagerDataSource readDataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/test2");
        dataSource.setUsername("root");
        dataSource.setPassword("123456");
        return dataSource;
    }

    @Bean
    public RoutingDataSource dataSource(DataSource writeDataSource, DataSource readDataSource) {
        Map<Object, Object> dataSourceMap = new HashMap<>();
        dataSourceMap.put(RoutingDataSourceType.WRITE, writeDataSource);
        dataSourceMap.put(RoutingDataSourceType.READ, readDataSource);

        RoutingDataSource dataSource = new RoutingDataSource();
        dataSource.setDefaultTargetDataSource(writeDataSource);
        dataSource.setTargetDataSources(dataSourceMap);
        return dataSource;
    }

    @Bean
    public RoutingDataSourceAspect routingDataSourceAspect() {
        return new RoutingDataSourceAspect();
    }

    @Bean
    public DataSourceTransactionManager transactionManager(DataSource dataSource) {
        DataSourceTransactionManager transactionManager = new RoutingDataSourceTransactionManager();
        transactionManager.setDataSource(dataSource);
        return transactionManager;
    }

}
