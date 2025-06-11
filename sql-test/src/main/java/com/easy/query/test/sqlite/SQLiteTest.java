package com.easy.query.test.sqlite;

import com.easy.query.test.cache.JsonUtil;
import com.easy.query.test.entity.BlogEntity;
import org.junit.Test;

import java.time.LocalDateTime;
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
        System.out.println(list.size());
        for (BlogEntity blogEntity : list) {
            String s = JsonUtil.object2JsonStr(blogEntity);
            System.out.println(s);
        }
    }
    @Test
    public void testQuery1(){
        {

            List<BlogEntity> list = entityQuery.queryable(BlogEntity.class)
                    .where(t_blog -> {
                        t_blog.createTime().rangeClosed(LocalDateTime.of(2020,2,1,1,0,0),LocalDateTime.of(2021,3,1,1,0));
                    }).toList();
            System.out.println(list.size());
            for (BlogEntity blogEntity : list) {
                String s = JsonUtil.object2JsonStr(blogEntity);
                System.out.println(s);
            }
        }
    }
}
