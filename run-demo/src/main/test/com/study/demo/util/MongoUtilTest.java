package com.study.demo.util;

import com.mongodb.client.result.DeleteResult;
import com.study.demo.bean.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

/**
 * @Author: Lon
 * @Date: 2019/5/14 14:30
 * @Description:
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class MongoUtilTest {

    @Autowired
    private MongoUtil mongoUtil;

    @Test
    public void saveObject() {
        User user = new User();
        user.setId(Long.valueOf(1000));
        user.setUsername("Test");
        user.setPassword("666");
        Assert.assertNotNull(mongoUtil.saveObject(user));
    }

    @Test
    public void findAll() {
        List<User> userList = mongoUtil.findAll(User.class);
        Assert.assertNotSame(0, userList.size());
    }

    @Test
    public void find() {
        Query query = new Query();
        query.addCriteria(Criteria.where("username").is("Lon"));
        List<User> userList = mongoUtil.find(query, User.class);
        Assert.assertNotSame(0, userList.size());
    }

    @Test
    public void find1() {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").in(Arrays.asList(1, 2, 100)));
        List<User> userList = mongoUtil.find(query, User.class);
        Assert.assertNotSame(0, userList.size());
    }

    @Test
    public void find2() {
        Query query = new Query();
        query.addCriteria(Criteria.where("id").in(Arrays.asList(1, 2, 100)).and("password").is("2"));
        List<User> userList = mongoUtil.find(query, User.class);
        Assert.assertNotSame(0, userList.size());
    }

    @Test
    public void delete() {
        Query query = new Query();
        query.addCriteria(Criteria.where("username").is("Test"));
        DeleteResult result = mongoUtil.delete(query, User.class);
        Assert.assertNotSame(Long.valueOf(0), result.getDeletedCount());
    }
}