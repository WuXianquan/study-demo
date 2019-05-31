package com.study.demo.RowMapper;

import com.study.demo.bean.User;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.data.hadoop.hbase.RowMapper;

import java.io.UnsupportedEncodingException;

/**
 * @Author: Lon
 * @Date: 2019/5/20 17:29
 * @Description: 存放用户信息的Hbase接口
 */
public class  UserRowMapper implements RowMapper<User> {

    private static byte[] COLUMN_FAMILY;
    private static byte[] ID;
    private static byte[] USERNAME;
    private static byte[] PASSWORD;

    static {
        try {
            COLUMN_FAMILY = "info".getBytes("UTF-8");
            ID = "id".getBytes("UTF-8");
            USERNAME = "username".getBytes("UTF-8");
            PASSWORD = "password".getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }



    @Override
    public User mapRow(Result result, int rowNum) {
        String id = Bytes.toString(result.getValue(COLUMN_FAMILY, ID));
        String username = Bytes.toString(result.getValue(COLUMN_FAMILY, USERNAME));
        String password = Bytes.toString(result.getValue(COLUMN_FAMILY, PASSWORD));
        User user = new User();
        user.setId(Long.valueOf(id));
        user.setUsername(username);
        user.setPassword(password);
        return user;
    }
}
