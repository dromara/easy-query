package com.easy.query.test;

import com.easy.query.BaseTest;
import com.easy.query.entity.TopicInterceptor;
import com.easy.query.logicdel.CurrentUserHelper;
import org.junit.Assert;
import org.junit.Test;

/**
 * create time 2023/4/3 21:10
 * 文件说明
 *
 * @author xuejiaming
 */
public class InterceptorTest extends BaseTest {

    @Test
    public void test1(){
        boolean any = easyQuery.queryable(TopicInterceptor.class).whereById("123").any();
        if(any){
            easyQuery.deletable(TopicInterceptor.class).whereById("123").executeRows();
        }
        CurrentUserHelper.setUserId("xiaoming");
        CurrentUserHelper.setTenantId("abc");
        TopicInterceptor topicInterceptor = new TopicInterceptor();
        topicInterceptor.setId("123");
        topicInterceptor.setTitle("123");
        topicInterceptor.setStars(123);
        long l = easyQuery.insertable(topicInterceptor).executeRows();
        Assert.assertEquals(1,l);
        String s = easyQuery.queryable(TopicInterceptor.class).whereById("123").noInterceptor("MyTenantInterceptor").toSql();
        Assert.assertEquals("SELECT t.`id`,t.`stars`,t.`title`,t.`create_time`,t.`create_by`,t.`update_time`,t.`update_by`,t.`tenant_id` FROM `t_topic_interceptor` t WHERE t.`id` = ?",s);
        TopicInterceptor topicInterceptor1 = easyQuery.queryable(TopicInterceptor.class).whereById("123").firstOrNull();
        Assert.assertNotNull(topicInterceptor1);
        Assert.assertEquals(CurrentUserHelper.getUserId(),topicInterceptor1.getCreateBy());
        CurrentUserHelper.setUserId("xiaoming1");
        long l1 = easyQuery.updatable(topicInterceptor1).executeRows();
        Assert.assertEquals(1,l1);
        TopicInterceptor topicInterceptor2 = easyQuery.queryable(TopicInterceptor.class).whereById("123").firstOrNull();
        Assert.assertNotNull(topicInterceptor2);
        Assert.assertNotEquals(topicInterceptor1.getUpdateBy(),topicInterceptor1.getCreateBy());
        Assert.assertNotEquals(CurrentUserHelper.getUserId(),topicInterceptor1.getCreateBy());
        Assert.assertEquals(CurrentUserHelper.getUserId(),topicInterceptor2.getUpdateBy());
        long l2 = easyQuery.updatable(TopicInterceptor.class).set(TopicInterceptor::getTitle, topicInterceptor2.getTitle())
                .whereById(topicInterceptor2.getId()).executeRows();
        Assert.assertEquals(1,l2);
    }
    @Test
    public void test2(){
        boolean any = easyQuery.queryable(TopicInterceptor.class).whereById("1234").any();
        if(any){
            easyQuery.deletable(TopicInterceptor.class).whereById("1234").executeRows();
        }
        CurrentUserHelper.setUserId("xiaoming");
        CurrentUserHelper.setTenantId("abc");
        TopicInterceptor topicInterceptor = new TopicInterceptor();
        topicInterceptor.setId("1234");
        topicInterceptor.setTitle("1234");
        topicInterceptor.setStars(1234);
        long l = easyQuery.insertable(topicInterceptor).executeRows();
        Assert.assertEquals(1,l);
        String s = easyQuery.queryable(TopicInterceptor.class).whereById("1234").noInterceptor("MyTenantInterceptor").toSql();
        Assert.assertEquals("SELECT t.`id`,t.`stars`,t.`title`,t.`create_time`,t.`create_by`,t.`update_time`,t.`update_by`,t.`tenant_id` FROM `t_topic_interceptor` t WHERE t.`id` = ?",s);
        TopicInterceptor topicInterceptor1 = easyQuery.queryable(TopicInterceptor.class).whereById("1234").firstOrNull();
        Assert.assertNotNull(topicInterceptor1);
        Assert.assertEquals(CurrentUserHelper.getUserId(),topicInterceptor1.getCreateBy());
        CurrentUserHelper.setUserId("xiaoming1");
        long l1 = easyQuery.updatable(topicInterceptor1).executeRows();
        Assert.assertEquals(1,l1);
        TopicInterceptor topicInterceptor2 = easyQuery.queryable(TopicInterceptor.class).whereById("1234").firstOrNull();
        Assert.assertNotNull(topicInterceptor2);
        Assert.assertNotEquals(topicInterceptor1.getUpdateBy(),topicInterceptor1.getCreateBy());
        Assert.assertNotEquals(CurrentUserHelper.getUserId(),topicInterceptor1.getCreateBy());
        Assert.assertEquals(CurrentUserHelper.getUserId(),topicInterceptor2.getUpdateBy());
        String s1 = easyQuery.updatable(TopicInterceptor.class).noInterceptor("MyTenantInterceptor").set(TopicInterceptor::getTitle, topicInterceptor2.getTitle())
                .whereById(topicInterceptor2.getId()).toSql();
        Assert.assertEquals("UPDATE `t_topic_interceptor` SET `title` = ?,`update_by` = ?,`update_time` = ? WHERE `id` = ?",s1);
        long l2 = easyQuery.updatable(TopicInterceptor.class).set(TopicInterceptor::getTitle, topicInterceptor2.getTitle())
                .whereById(topicInterceptor2.getId()).executeRows();
        Assert.assertEquals(1,l2);
    }
    @Test
    public void test3(){
        boolean any = easyQuery.queryable(TopicInterceptor.class).whereById("12345").any();
        if(any){
            long l = easyQuery.deletable(TopicInterceptor.class).whereById("12345").executeRows();
            Assert.assertEquals(1,l);
        }
        CurrentUserHelper.setUserId("xiaoming");
        CurrentUserHelper.setTenantId("abc");
        TopicInterceptor topicInterceptor = new TopicInterceptor();
        topicInterceptor.setId("12345");
        topicInterceptor.setTitle("12345");
        topicInterceptor.setStars(12345);
        long l = easyQuery.insertable(topicInterceptor).executeRows();
        Assert.assertEquals(1,l);
        String s = easyQuery.queryable(TopicInterceptor.class).whereById("12345").toSql();
        Assert.assertEquals("SELECT t.`id`,t.`stars`,t.`title`,t.`create_time`,t.`create_by`,t.`update_time`,t.`update_by`,t.`tenant_id` FROM `t_topic_interceptor` t WHERE t.`tenant_id` = ? AND t.`id` = ?",s);
        TopicInterceptor topicInterceptor1 = easyQuery.queryable(TopicInterceptor.class).whereById("12345").firstOrNull();
        Assert.assertNotNull(topicInterceptor1);
        Assert.assertEquals(CurrentUserHelper.getUserId(),topicInterceptor1.getCreateBy());
        CurrentUserHelper.setUserId("xiaoming1");
        long l1 = easyQuery.updatable(topicInterceptor1).executeRows();
        Assert.assertEquals(1,l1);
        TopicInterceptor topicInterceptor2 = easyQuery.queryable(TopicInterceptor.class).whereById("12345").firstOrNull();
        Assert.assertNotNull(topicInterceptor2);
        Assert.assertNotEquals(topicInterceptor1.getUpdateBy(),topicInterceptor1.getCreateBy());
        Assert.assertNotEquals(CurrentUserHelper.getUserId(),topicInterceptor1.getCreateBy());
        Assert.assertEquals(CurrentUserHelper.getUserId(),topicInterceptor2.getUpdateBy());
        String s1 = easyQuery.updatable(TopicInterceptor.class).set(TopicInterceptor::getTitle, topicInterceptor2.getTitle())
                .whereById(topicInterceptor2.getId()).toSql();
        Assert.assertEquals("UPDATE `t_topic_interceptor` SET `title` = ?,`update_by` = ?,`update_time` = ? WHERE `tenant_id` = ? AND `id` = ?",s1);
        long l2 = easyQuery.updatable(TopicInterceptor.class).set(TopicInterceptor::getTitle, topicInterceptor2.getTitle())
                .whereById(topicInterceptor2.getId()).executeRows();
        Assert.assertEquals(1,l2);
        String s2 = easyQuery.deletable(TopicInterceptor.class)
                .whereById(topicInterceptor2.getId()).toSql();
        Assert.assertEquals("DELETE FROM `t_topic_interceptor` WHERE `tenant_id` = ? AND `id` = ?",s2);
        String s3 = easyQuery.deletable(topicInterceptor2).toSql();
        Assert.assertEquals("DELETE FROM `t_topic_interceptor` WHERE `tenant_id` = ? AND `id` = ?",s3);
        long l3 = easyQuery.deletable(TopicInterceptor.class)
                .whereById(topicInterceptor2.getId()).executeRows();
        Assert.assertEquals(1,l3);
        long l4 = easyQuery.deletable(topicInterceptor2).executeRows();
        Assert.assertEquals(0,l4);
    }
}
