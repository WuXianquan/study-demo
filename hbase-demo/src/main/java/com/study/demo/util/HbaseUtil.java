package com.study.demo.util;

import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.hadoop.hbase.HbaseTemplate;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @Author: Lon
 * @Date: 2019/5/15 16:41
 * @Description:
 */
@Component
public class HbaseUtil {

    @Autowired
    private HbaseTemplate hbaseTemplate;

    @Autowired
    private HBaseAdmin hBaseAdmin;

    public void createTable(String tableName, String familyName) throws IOException {
        if (hBaseAdmin.tableExists(tableName)) {
            throw new RuntimeException("table " + tableName + " is exists");
        }
        HTableDescriptor desc = new HTableDescriptor(TableName.valueOf(tableName));
        desc.addFamily(new HColumnDescriptor(familyName));
        hBaseAdmin.createTable(desc);
        hBaseAdmin.close();
    }

    public void createTable(String tableName, String[] familyNames) throws IOException {
        if (hBaseAdmin.tableExists(tableName)) {
            throw new RuntimeException("tableName " + tableName + " is exists");
        }
        HTableDescriptor desc = new HTableDescriptor(TableName.valueOf(tableName));
        int length = familyNames.length;
        for (int i = 0; i < length; i++) {
            desc.addFamily(new HColumnDescriptor(familyNames[i]));
        }
        hBaseAdmin.createTable(desc);
        hBaseAdmin.close();

    }

    public void insert(String tableName, String familyName, String rowName, String qualifter, String value) {
        hbaseTemplate.put(tableName, familyName, rowName, qualifter, value.getBytes());
    }

    public void delete(String tableName, String rowName, String familyName) {
        hbaseTemplate.delete(tableName, rowName, familyName);
    }

    public void delete(String tableName, String rowName, String familyName, String qualifter) {
        hbaseTemplate.delete(tableName, rowName, familyName, qualifter);
    }

    public void dropTable(String tableName) throws IOException {
        if (!hBaseAdmin.tableExists(tableName)) {
            throw new RuntimeException("table " + tableName + " is not exists");
        }
        hBaseAdmin.disableTable(tableName);
        hBaseAdmin.deleteTable(tableName);
    }
}
