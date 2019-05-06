package com.study.demo.config;

import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.io.IOException;

/**
 * @Author: Lon
 * @Date: 2019/1/30 14:15
 * @Description: 整合mybatis配置类，定义SqlSessionFactoryBean
 */
@Configuration
@PropertySource(value={"classpath:mybatis.properties"})
public class MybatisConfig {

    @Value("${mapper.xml.config.path}")
    private String mapperXMLConfigPath;

    @Value("${mapper.package.path}")
    private String mapperPackagePath;

    @Resource(name="druidDataSource")
    private DataSource dataSource;

    @Bean
    @ConfigurationProperties(prefix = "mybatis.configuration")
    public org.apache.ibatis.session.Configuration configuration() {
        return new org.apache.ibatis.session.Configuration();
    }

    @Bean
    public SqlSessionFactoryBean createSqlSessionFactory() throws IOException {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        String packageXMLConfigPath = PathMatchingResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX + mapperXMLConfigPath;

        // 设置mapper 对应的XML 文件的路径
        sqlSessionFactoryBean.setMapperLocations(resolver.getResources(packageXMLConfigPath));
        // 设置数据源
        sqlSessionFactoryBean.setDataSource(dataSource);
        // 设置mapper 接口所在的包
        sqlSessionFactoryBean.setTypeAliasesPackage(mapperPackagePath);
        sqlSessionFactoryBean.setConfiguration(configuration());
        return sqlSessionFactoryBean;
    }
}
