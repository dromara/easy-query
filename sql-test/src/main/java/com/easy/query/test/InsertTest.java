package com.easy.query.test;

import com.easy.query.api4j.insert.EntityInsertable;
import com.easy.query.core.exception.EasyQuerySQLCommandException;
import com.easy.query.core.exception.EasyQuerySQLStatementException;
import com.easy.query.test.entity.TopicAuto;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @FileName: InsertTest.java
 * @Description: 文件说明
 * @Date: 2023/3/16 21:25
 * @author xuejiaming
 */
public class InsertTest extends BaseTest {

    @Test
    public void insertTest(){
        List<TopicAuto> topicAutos = easyQuery.queryable(TopicAuto.class).where(o->o.lt(TopicAuto::getStars,999)).toList();
        Assert.assertEquals(10,topicAutos.size());
        int i=1;
        for (TopicAuto topicAuto : topicAutos) {
            Assert.assertNotNull(topicAuto.getId());
            Assert.assertEquals(0, topicAuto.getId().compareTo(i));
            i++;
        }
    }
    @Test
    public void insertTest1(){
        long l = easyQuery.insertable(null).executeRows();
        Assert.assertEquals(0,l);
        long l1 = easyQuery.insertable(null).insert(null).executeRows();
        Assert.assertEquals(0,l1);
        Object en=null;
        long l3 = easyQuery.insertable(null).insert(en).executeRows();
        Assert.assertEquals(0,l3);
        long l2 = easyQuery.insertable(null).useInterceptor().noInterceptor().useInterceptor("1").noInterceptor("1").executeRows();
        Assert.assertEquals(0,l2);
    }

    @Test
    public void insertTest2(){

        TopicAuto topicAuto = new TopicAuto();
        topicAuto.setStars(999);
        topicAuto.setTitle("title" + 999);
        topicAuto.setCreateTime(LocalDateTime.now().plusDays(99));
        Assert.assertNull(topicAuto.getId());
        long l = easyQuery.insertable(topicAuto).executeRows(true);
        Assert.assertEquals(1,l);
        Assert.assertNotNull(topicAuto.getId());
    }
    @Test
    public void insertTest3(){

        TopicAuto topicAuto = new TopicAuto();
        topicAuto.setStars(999);
        topicAuto.setTitle("title" + 999);
        topicAuto.setCreateTime(LocalDateTime.now().plusDays(99));
        Assert.assertNull(topicAuto.getId());
        EntityInsertable<TopicAuto> insertable = easyQuery.insertable(topicAuto).asTable("aaa").asSchema(o->"xxx");
        String sql = insertable.toSQL(topicAuto);
        Assert.assertEquals("INSERT INTO `xxx`.`aaa` (`stars`,`title`,`create_time`) VALUES (?,?,?)",sql);
    }
    @Test
    public void insertTest5(){

        TopicAuto topicAuto = new TopicAuto();
        topicAuto.setStars(999);
        topicAuto.setTitle("title" + 999);
        topicAuto.setCreateTime(LocalDateTime.now().plusDays(99));
        Assert.assertNull(topicAuto.getId());
        EntityInsertable<TopicAuto> insertable = easyQuery.insertable(topicAuto).asTable(o->o+"aaa").asSchema("xxx");
        String sql = insertable.toSQL(topicAuto);
        Assert.assertEquals("INSERT INTO `xxx`.`t_topic_autoaaa` (`stars`,`title`,`create_time`) VALUES (?,?,?)",sql);
    }
    @Test
    public void insertTest6(){

        try {

            TopicAuto topicAuto = new TopicAuto();
            topicAuto.setStars(999);
            topicAuto.setTitle("title" + 999);
            topicAuto.setCreateTime(LocalDateTime.now().plusDays(99));
            Assert.assertNull(topicAuto.getId());
            EntityInsertable<TopicAuto> insertable = easyQuery.insertable(topicAuto).useInterceptor("Topic1Interceptor").asTable(o->o+"aaa").asSchema("xxx");
            long l = insertable.executeRows();
        }catch (Exception ex){
            Assert.assertTrue(ex instanceof EasyQuerySQLCommandException);
            EasyQuerySQLCommandException ex1 = (EasyQuerySQLCommandException) ex;
            Assert.assertTrue(ex1.getCause() instanceof EasyQuerySQLStatementException);
            String sql = ((EasyQuerySQLStatementException) ex1.getCause()).getSQL();
            Assert.assertEquals("INSERT INTO `xxx`.`t_topic_autoaaa` (`stars`,`create_time`) VALUES (?,?)", sql);
        }
    }
}
