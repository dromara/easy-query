package com.easy.query.test;

import com.easy.query.test.BaseTest;
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
}
