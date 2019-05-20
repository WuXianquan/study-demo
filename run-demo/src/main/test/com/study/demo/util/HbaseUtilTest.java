package com.study.demo.util;

import com.study.demo.RowMapper.UserRowMapper;
import com.study.demo.bean.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.List;

/**
 * @Author: Lon
 * @Date: 2019/5/15 17:00
 * @Description:
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class HbaseUtilTest {

    private final static String TABLENAME = "testTableName";

    @Autowired
    private HbaseUtil hbaseUtil;

    @Test
    public void createTable() throws IOException {
        hbaseUtil.createTable(TABLENAME, "testFamilyName");
    }

    @Test
    public void createTable1() throws IOException {
        String[] familyNames = {"testFamilyName1", "testFamilyName2", "testFamilyName3"};
        hbaseUtil.createTable(TABLENAME, familyNames);
    }

    @Test
    public void insert() {
        hbaseUtil.insert("user", "0001", "info", "password", "123");
    }

    @Test
    public void find() {
        User user = hbaseUtil.find("user", "0002", new UserRowMapper());
        Assert.assertNotNull(user);
    }

    @Test
    public void find1() {
        List<String> list = hbaseUtil.find("user", "info", "username");
        Assert.assertNotSame(0, list.size());
    }

    @Test
    public void delete() {
    }

    @Test
    public void delete1() {
    }

    @Test
    public void dropTable() throws IOException {
        hbaseUtil.dropTable(TABLENAME);
    }
}