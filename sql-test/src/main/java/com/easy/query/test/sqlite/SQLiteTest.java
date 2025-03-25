package com.easy.query.test.sqlite;

import com.easy.query.test.entity.BlogEntity;
import org.junit.Test;

import java.util.List;

/**
 * create time 2025/3/25 09:21
 * 文件说明
 *
 * @author xuejiaming
 */


public class SQLiteTest extends SQLiteBaseTest{
    @Test
    public void testQuery(){
        List<BlogEntity> list = entityQuery.queryable(BlogEntity.class).toList();
        for (BlogEntity blogEntity : list) {
            System.out.println(blogEntity);
        }
    }
}
