package com.easy.query.test.sqlite;

import com.easy.query.core.basic.api.database.CodeFirstCommand;
import com.easy.query.core.basic.api.database.DatabaseCodeFirst;
import com.easy.query.test.cache.JsonUtil;
import com.easy.query.test.entity.BlogEntity;
import com.easy.query.test.entity.MultiPrimaryEntity;
import com.easy.query.test.entity.Topic;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.Arrays;
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

    @Test
    public  void testDDL(){
        DatabaseCodeFirst databaseCodeFirst = entityQuery.getDatabaseCodeFirst();
        CodeFirstCommand codeFirstCommand = databaseCodeFirst.dropTableIfExistsCommand(Arrays.asList(Topic.class));
        codeFirstCommand.executeWithTransaction(s->s.commit());
        CodeFirstCommand codeFirstCommand1 = databaseCodeFirst.syncTableCommand(Arrays.asList(Topic.class));
        codeFirstCommand1.executeWithTransaction(s->s.commit());
    }
    @Test
    public  void testDDL2(){
        DatabaseCodeFirst databaseCodeFirst = entityQuery.getDatabaseCodeFirst();
        CodeFirstCommand codeFirstCommand = databaseCodeFirst.dropTableIfExistsCommand(Arrays.asList(MultiPrimaryEntity.class));
        codeFirstCommand.executeWithTransaction(s->s.commit());
        CodeFirstCommand codeFirstCommand1 = databaseCodeFirst.syncTableCommand(Arrays.asList(MultiPrimaryEntity.class));
        codeFirstCommand1.executeWithTransaction(s->s.commit());
    }
}
