package com.easy.query.test;

import com.easy.query.BaseTest;
import com.easy.query.core.exception.EasyQueryInvalidOperationException;
import com.easy.query.entity.Topic;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDateTime;

/**
 * @FileName: DeleteTest.java
 * @Description: 文件说明
 * @Date: 2023/3/21 12:54
 * @Created by xuejiaming
 */
public class DeleteTest  extends BaseTest {
    @Test
    public void deleteTest1(){
        Topic topic = easyQuery.queryable(Topic.class).whereId("999").firstOrNull();
        if(topic==null){
            topic=new Topic();
            topic.setId("999");
            topic.setTitle("title999");
            topic.setCreateTime(LocalDateTime.now());
            topic.setStars(999);
            long l = easyQuery.insertable(topic).executeRows();
            Assert.assertEquals(1,l);
        }
        String deleteSql = easyQuery.deletable(Topic.class).whereById("999").toSql();
        Assert.assertEquals("DELETE FROM t_topic WHERE `id` = ?",deleteSql);
        long l = easyQuery.deletable(Topic.class).whereById("999").executeRows();
        Assert.assertEquals(1,l);
    }
    @Test
    public void deleteTest2(){
        Topic topic = easyQuery.queryable(Topic.class).whereId("998").firstOrNull();
        if(topic==null){
            topic=new Topic();
            topic.setId("998");
            topic.setTitle("title998");
            topic.setCreateTime(LocalDateTime.now());
            topic.setStars(998);
            long l = easyQuery.insertable(topic).executeRows();
            Assert.assertEquals(1,l);
        }
        String deleteSql = easyQuery.deletable(Topic.class).where(o->o.eq(Topic::getTitle,"title998")).toSql();
        Assert.assertEquals("DELETE FROM t_topic WHERE `title` = ?",deleteSql);
        long l = easyQuery.deletable(Topic.class).where(o->o.eq(Topic::getTitle,"title998")).executeRows();
        Assert.assertEquals(1,l);
    }
    @Test
    public void deleteTest3(){
        Topic topic = easyQuery.queryable(Topic.class).whereId("997").firstOrNull();
        if(topic==null){
            topic=new Topic();
            topic.setId("997");
            topic.setTitle("title997");
            topic.setCreateTime(LocalDateTime.now());
            topic.setStars(997);
            long l = easyQuery.insertable(topic).executeRows();
            Assert.assertEquals(1,l);
        }
        String deleteSql = easyQuery.deletable(topic).toSql();
        Assert.assertEquals("DELETE FROM t_topic WHERE `id` = ?",deleteSql);
        long l = easyQuery.deletable(topic).executeRows();
        Assert.assertEquals(1,l);
    }
    @Test
    public void deleteTest4(){
        Topic topic = easyQuery.queryable(Topic.class).whereId("996").firstOrNull();
        if(topic==null){
            topic=new Topic();
            topic.setId("996");
            topic.setTitle("title996");
            topic.setCreateTime(LocalDateTime.now());
            topic.setStars(996);
            long l = easyQuery.insertable(topic).executeRows();
            Assert.assertEquals(1,l);
        }
        String deleteSql = easyQuery.deletable(topic).toSql();
        Assert.assertEquals("DELETE FROM t_topic WHERE `id` = ?",deleteSql);
        long l = easyQuery.deletable(topic).executeRows();
        Assert.assertEquals(1,l);
    }
    @Test
    public void deleteTest6(){
        Topic topic = easyQuery.queryable(Topic.class).whereId("995").firstOrNull();
        if(topic==null){
            topic=new Topic();
            topic.setId("995");
            topic.setTitle("title995");
            topic.setCreateTime(LocalDateTime.now());
            topic.setStars(995);
            long l = easyQuery.insertable(topic).executeRows();
            Assert.assertEquals(1,l);
        }
        try {
            long l = easyQuery.deletable(Topic.class).whereById("999").allowDeleteCommand(false).executeRows();
        }catch (Exception e){
            Assert.assertEquals(EasyQueryInvalidOperationException.class,e.getClass());
        }

    }
}
