package com.study.demo.util;

import com.mongodb.client.result.DeleteResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;
import java.util.List;

/**
 * @Author: Lon
 * @Date: 2019/5/14 14:18
 * @Description: MongoDB存取操作工具类
 */
@Component
public class MongoUtil {

    @Autowired
    private MongoTemplate mongoTemplate;

    public <T> T saveObject(T objectToSave) {
        return mongoTemplate.save(objectToSave);
    }

    public <T> List<T> findAll(Class<T> entityClass) {
        return mongoTemplate.findAll(entityClass);
    }

    public <T> List<T> find(Query query, Class<T> entityClass) {
        return mongoTemplate.find(query, entityClass);
    }

    public DeleteResult delete(Query query, Class<?> entityClass) {
        return mongoTemplate.remove(query, entityClass);
    }
}
