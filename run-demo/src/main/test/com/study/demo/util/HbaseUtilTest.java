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

    private final static String TABLENAME = "user";

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
        hbaseUtil.insert(TABLENAME, "0003", "info", "username", "test111");
    }

    @Test
    public void find() {
        User user = hbaseUtil.find(TABLENAME, "0001", new UserRowMapper());
        Assert.assertNotNull(user);
    }

    @Test
    public void find1() {
        List<String> list = hbaseUtil.find(TABLENAME, "info", "username");
        Assert.assertNotSame(0, list.size());
    }

    @Test
    public void find2() {
        String string = hbaseUtil.find(TABLENAME, "0001", "info", "username");
        Assert.assertNotNull(string);
    }

    @Test
    public void delete() {
        hbaseUtil.delete(TABLENAME, "0001", "tttt");
    }

    @Test
    public void delete1() {
        hbaseUtil.delete(TABLENAME, "0003", "info", "username");
    }

    @Test
    public void dropTable() throws IOException {
        hbaseUtil.dropTable(TABLENAME);
    }
}