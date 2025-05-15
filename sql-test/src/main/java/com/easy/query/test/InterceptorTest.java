package com.easy.query.test;

import com.easy.query.test.entity.TopicInterceptor;
import com.easy.query.test.logicdel.CurrentUserHelper;
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

        CurrentUserHelper.setUserId("xiaoming");
        CurrentUserHelper.setTenantId("abc");
        easyEntityQuery.deletable(TopicInterceptor.class).whereById("123xx").disableLogicDelete().allowDeleteStatement(true).executeRows();
        TopicInterceptor topicInterceptor = new TopicInterceptor();
        topicInterceptor.setId("123xx");
        topicInterceptor.setTitle("123");
        topicInterceptor.setStars(123);
        long l = easyEntityQuery.insertable(topicInterceptor).executeRows();
        Assert.assertEquals(1,l);
        String s = easyEntityQuery.queryable(TopicInterceptor.class).whereById("123xx").noInterceptor("MyTenantInterceptor").toSQL();
        Assert.assertEquals("SELECT `id`,`stars`,`title`,`create_time`,`create_by`,`update_time`,`update_by`,`tenant_id` FROM `t_topic_interceptor` WHERE `id` = ?",s);
        TopicInterceptor topicInterceptor1 = easyEntityQuery.queryable(TopicInterceptor.class).whereById("123xx").firstOrNull();
        Assert.assertNotNull(topicInterceptor1);
        Assert.assertEquals(CurrentUserHelper.getUserId(),topicInterceptor1.getCreateBy());
        CurrentUserHelper.setUserId("xiaoming1");
        long l1 = easyEntityQuery.updatable(topicInterceptor1).executeRows();
        Assert.assertEquals(1,l1);
        TopicInterceptor topicInterceptor2 = easyEntityQuery.queryable(TopicInterceptor.class).whereById("123xx").firstOrNull();
        Assert.assertNotNull(topicInterceptor2);
        Assert.assertNotEquals(topicInterceptor1.getUpdateBy(),topicInterceptor1.getCreateBy());
        Assert.assertNotEquals(CurrentUserHelper.getUserId(),topicInterceptor1.getCreateBy());
        Assert.assertEquals(CurrentUserHelper.getUserId(),topicInterceptor2.getUpdateBy());
        long l2 = easyEntityQuery.updatable(TopicInterceptor.class).setColumns(t -> t.title().set(topicInterceptor2.getTitle()))
                .whereById(topicInterceptor2.getId()).executeRows();
        Assert.assertEquals(1,l2);
        easyEntityQuery.deletable(TopicInterceptor.class).whereById("123xx").disableLogicDelete().allowDeleteStatement(true).executeRows();
    }
    @Test
    public void test2(){
        boolean any = easyEntityQuery.queryable(TopicInterceptor.class).whereById("1234").any();
        if(any){
            easyEntityQuery.deletable(TopicInterceptor.class).whereById("1234").executeRows();
        }
        CurrentUserHelper.setUserId("xiaoming");
        CurrentUserHelper.setTenantId("abc");
        TopicInterceptor topicInterceptor = new TopicInterceptor();
        topicInterceptor.setId("1234");
        topicInterceptor.setTitle("1234");
        topicInterceptor.setStars(1234);
        long l = easyEntityQuery.insertable(topicInterceptor).executeRows();
        Assert.assertEquals(1,l);
        String s = easyEntityQuery.queryable(TopicInterceptor.class).whereById("1234").noInterceptor("MyTenantInterceptor").toSQL();
        Assert.assertEquals("SELECT `id`,`stars`,`title`,`create_time`,`create_by`,`update_time`,`update_by`,`tenant_id` FROM `t_topic_interceptor` WHERE `id` = ?",s);
        TopicInterceptor topicInterceptor1 = easyEntityQuery.queryable(TopicInterceptor.class).whereById("1234").firstOrNull();
        Assert.assertNotNull(topicInterceptor1);
        Assert.assertEquals(CurrentUserHelper.getUserId(),topicInterceptor1.getCreateBy());
        CurrentUserHelper.setUserId("xiaoming1");
        long l1 = easyEntityQuery.updatable(topicInterceptor1).executeRows();
        Assert.assertEquals(1,l1);
        TopicInterceptor topicInterceptor2 = easyEntityQuery.queryable(TopicInterceptor.class).whereById("1234").firstOrNull();
        Assert.assertNotNull(topicInterceptor2);
        Assert.assertNotEquals(topicInterceptor1.getUpdateBy(),topicInterceptor1.getCreateBy());
        Assert.assertNotEquals(CurrentUserHelper.getUserId(),topicInterceptor1.getCreateBy());
        Assert.assertEquals(CurrentUserHelper.getUserId(),topicInterceptor2.getUpdateBy());
        String s1 = easyEntityQuery.updatable(TopicInterceptor.class).noInterceptor("MyTenantInterceptor").setColumns(t -> t.title().set(topicInterceptor2.getTitle()))
                .whereById(topicInterceptor2.getId()).toSQL();
        Assert.assertEquals("UPDATE `t_topic_interceptor` SET `title` = ?,`update_by` = ?,`update_time` = ? WHERE `id` = ?",s1);
        long l2 = easyEntityQuery.updatable(TopicInterceptor.class).setColumns(t -> t.title().set(topicInterceptor2.getTitle()))
                .whereById(topicInterceptor2.getId()).executeRows();
        Assert.assertEquals(1,l2);
    }
    @Test
    public void test3(){
        boolean any = easyEntityQuery.queryable(TopicInterceptor.class).whereById("12345").any();
        if(any){
            long l = easyEntityQuery.deletable(TopicInterceptor.class).whereById("12345").executeRows();
            Assert.assertEquals(1,l);
        }
        CurrentUserHelper.setUserId("xiaoming");
        CurrentUserHelper.setTenantId("abc");
        TopicInterceptor topicInterceptor = new TopicInterceptor();
        topicInterceptor.setId("12345");
        topicInterceptor.setTitle("12345");
        topicInterceptor.setStars(12345);
        long l = easyEntityQuery.insertable(topicInterceptor).executeRows();
        Assert.assertEquals(1,l);
        String s = easyEntityQuery.queryable(TopicInterceptor.class).whereById("12345").toSQL();
        Assert.assertEquals("SELECT `id`,`stars`,`title`,`create_time`,`create_by`,`update_time`,`update_by`,`tenant_id` FROM `t_topic_interceptor` WHERE `tenant_id` = ? AND `id` = ?",s);
        TopicInterceptor topicInterceptor1 = easyEntityQuery.queryable(TopicInterceptor.class).whereById("12345").firstOrNull();
        Assert.assertNotNull(topicInterceptor1);
        Assert.assertEquals(CurrentUserHelper.getUserId(),topicInterceptor1.getCreateBy());
        CurrentUserHelper.setUserId("xiaoming1");
        long l1 = easyEntityQuery.updatable(topicInterceptor1).executeRows();
        Assert.assertEquals(1,l1);
        TopicInterceptor topicInterceptor2 = easyEntityQuery.queryable(TopicInterceptor.class).whereById("12345").firstOrNull();
        Assert.assertNotNull(topicInterceptor2);
        Assert.assertNotEquals(topicInterceptor1.getUpdateBy(),topicInterceptor1.getCreateBy());
        Assert.assertNotEquals(CurrentUserHelper.getUserId(),topicInterceptor1.getCreateBy());
        Assert.assertEquals(CurrentUserHelper.getUserId(),topicInterceptor2.getUpdateBy());
        String s1 = easyEntityQuery.updatable(TopicInterceptor.class).setColumns(t -> t.title().set(topicInterceptor2.getTitle()))
                .whereById(topicInterceptor2.getId()).toSQL();
        Assert.assertEquals("UPDATE `t_topic_interceptor` SET `title` = ?,`update_by` = ?,`update_time` = ? WHERE `tenant_id` = ? AND `id` = ?",s1);
        long l2 = easyEntityQuery.updatable(TopicInterceptor.class).setColumns(t -> t.title().set(topicInterceptor2.getTitle()))
                .whereById(topicInterceptor2.getId()).executeRows();
        Assert.assertEquals(1,l2);
        String s2 = easyEntityQuery.deletable(TopicInterceptor.class)
                .whereById(topicInterceptor2.getId()).toSQL();
        Assert.assertEquals("DELETE FROM `t_topic_interceptor` WHERE `tenant_id` = ? AND `id` = ?",s2);
        String s3 = easyEntityQuery.deletable(topicInterceptor2).toSQL();
        Assert.assertEquals("DELETE FROM `t_topic_interceptor` WHERE `tenant_id` = ? AND `id` = ?",s3);
        long l3 = easyEntityQuery.deletable(TopicInterceptor.class)
                .whereById(topicInterceptor2.getId()).executeRows();
        Assert.assertEquals(1,l3);
        long l4 = easyEntityQuery.deletable(topicInterceptor2).executeRows();
        Assert.assertEquals(0,l4);
    }
}
