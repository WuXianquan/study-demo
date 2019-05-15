package com.study.demo.config;

import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.hadoop.hbase.HbaseTemplate;
import java.io.IOException;
/**
 * @Author: Lon
 * @Date: 2019/5/15 16:30
 * @Description:
 */
@Configuration
@PropertySource("classpath:hbase.properties")
public class HbaseConfig {

    @Value("${hbase.zookeeper.quorum}")
    private String quorum;

    @Value("${hbase.zookeeper.port}")
    private String port;

    @Bean
    public HbaseTemplate hbaseTemplate() {
        HbaseTemplate hbaseTemplate = new HbaseTemplate();
        hbaseTemplate.setConfiguration(configuration());
        hbaseTemplate.setAutoFlush(true);
        return hbaseTemplate;
    }

    @Bean
    public HBaseAdmin hbaseAdmin() throws IOException {
        return new HBaseAdmin(hbaseTemplate().getConfiguration());
    }

    private org.apache.hadoop.conf.Configuration configuration() {
        org.apache.hadoop.conf.Configuration configuration = HBaseConfiguration.create();
        configuration.set("hbase.zookeeper.quorum", quorum);
        configuration.set("hbase.zookeeper.port", port);
        return configuration;
    }
}
