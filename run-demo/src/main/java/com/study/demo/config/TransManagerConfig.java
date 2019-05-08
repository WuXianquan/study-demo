package com.study.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;
import javax.annotation.Resource;
import javax.sql.DataSource;

/**
 * @Author: Lon
 * @Date: 2019/5/8 17:13
 * @Description: 事务管理配置类
 */
@Configuration
public class TransManagerConfig implements TransactionManagementConfigurer {

    @Resource(name = "druidDataSource")
    private DataSource dataSource;

    @Override
    @Bean
    public PlatformTransactionManager annotationDrivenTransactionManager() {
        DataSourceTransactionManager transactionManager =  new DataSourceTransactionManager();
        transactionManager.setDataSource(dataSource);
        return transactionManager;
    }
}