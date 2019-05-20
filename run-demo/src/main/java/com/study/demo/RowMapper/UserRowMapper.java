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
    private static byte[] USERNAME = "username".getBytes();

    @Override
    public User mapRow(Result result, int rowNumi) {
        String username = Bytes.toString(result.getValue(COLUMN_FAMILY, USERNAME));
        User user = new User();
        user.setUsername(username);
        return user;
    }
}
