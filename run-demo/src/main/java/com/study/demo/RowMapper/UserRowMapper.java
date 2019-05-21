package com.study.demo.RowMapper;

import com.study.demo.bean.User;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.data.hadoop.hbase.RowMapper;

/**
 * @Author: Lon
 * @Date: 2019/5/20 17:29
 * @Description: 存放用户信息的Hbase接口
 */
public class  UserRowMapper implements RowMapper<User> {

    private static byte[] COLUMN_FAMILY = "info".getBytes();
    private static byte[] ID = "id".getBytes();
    private static byte[] USERNAME = "username".getBytes();
    private static byte[] PASSWORD = "password".getBytes();

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
