package com.easy.query.test.pgsql;

import com.easy.query.api4j.insert.EntityInsertable;
import com.easy.query.core.exception.EasyQuerySQLCommandException;
import com.easy.query.core.exception.EasyQuerySQLStatementException;
import com.easy.query.test.entity.TopicAuto;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDateTime;

/**
 * create time 2023/7/6 22:20
 * 文件说明
 *
 * @author xuejiaming
 */
public class InsertTest extends PgSQLBaseTest {
    @Test
    public void insertTest1(){
        try {
            TopicAuto topicAuto = new TopicAuto();
            topicAuto.setStars(999);
            topicAuto.setTitle("title" + 999);
            topicAuto.setCreateTime(LocalDateTime.now().plusDays(99));
            Assert.assertNull(topicAuto.getId());
            EntityInsertable<TopicAuto> insertable = easyQuery.insertable(topicAuto).onConflictDoNothing().noInterceptor().asTable(o->o+"aaa").asSchema("xxx");
            long l = insertable.executeRows();
        }catch (Exception ex){
            Assert.assertTrue(ex instanceof EasyQuerySQLCommandException);
            EasyQuerySQLCommandException ex1 = (EasyQuerySQLCommandException) ex;
            Assert.assertTrue(ex1.getCause() instanceof EasyQuerySQLStatementException);
            String sql = ((EasyQuerySQLStatementException) ex1.getCause()).getSQL();
            Assert.assertEquals("INSERT INTO \"xxx\".\"t_topic_autoaaa\" (\"stars\",\"title\",\"create_time\") VALUES (?,?,?) ON CONFLICT DO NOTHING", sql);
        }
    }
}
