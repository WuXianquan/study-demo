package com.study.demo.util;

import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.hadoop.hbase.HbaseTemplate;
import org.springframework.data.hadoop.hbase.RowMapper;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.util.List;

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

    public void insert(String tableName, String rowName, String familyName, String qualifter, String value) {
        hbaseTemplate.put(tableName, rowName, familyName, qualifter, Bytes.toBytes(value));
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

    public <T> T find(String tableName, String rowName, RowMapper<T> mapper) {
        return hbaseTemplate.get(tableName, rowName, mapper);
    }

    public List<String> find(String tableName, String familyName, String qualifter) {
        return hbaseTemplate.find(tableName, familyName, qualifter, (result, i) -> Bytes.toString(result.getValue(familyName.getBytes(), qualifter.getBytes())));
    }

    public String find(String tableName, String rowName, String familyName, String qualifter) {
        return hbaseTemplate.get(tableName, rowName, familyName, qualifter, (result, i) -> Bytes.toString(result.getValue(familyName.getBytes(), qualifter.getBytes())));
    }
}
