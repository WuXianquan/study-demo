package com.study.demo.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

/**
 * @Author: Lon
 * @Date: 2019/5/8 17:13
 * @Description: 事务管理配置类
 */
@Configuration
@EnableTransactionManagement
public class TransManagerConfig {

    @Bean
    public PlatformTransactionManager annotationDrivenTransactionManager(@Qualifier("druidDataSource") DataSource dataSource) {
        DataSourceTransactionManager dataSourceTransactionManager =  new DataSourceTransactionManager(dataSource);
        return dataSourceTransactionManager;
    }
}