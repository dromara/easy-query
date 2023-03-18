package com.easy.query.test;

import com.easy.query.BaseTest;
import com.easy.query.entity.BlogEntity;
import com.easy.query.entity.TopicAuto;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @FileName: InsertTest.java
 * @Description: 文件说明
 * @Date: 2023/3/16 21:25
 * @Created by xuejiaming
 */
public class InsertTest extends BaseTest {
    @Override
    public void customInit() {
        boolean any = easyQuery.queryable(TopicAuto.class).any();
        if(!any){
            List<TopicAuto> topicAutos=new ArrayList<>();
            for (int i = 0; i < 10; i++) {
                TopicAuto topicAuto = new TopicAuto();
                topicAuto.setStars(i);
                topicAuto.setTitle("title"+i);
                topicAuto.setCreateTime(LocalDateTime.now().plusDays(i));
                topicAutos.add(topicAuto);
            }
            long l = easyQuery.insertable(topicAutos).executeRows(true);
            System.out.println(l);
        }

    }

    @Test
    public void insertTest(){
        List<TopicAuto> topicAutos = easyQuery.queryable(TopicAuto.class).toList();
        Assert.assertEquals(10,topicAutos.size());
        int i=1;
        for (TopicAuto topicAuto : topicAutos) {
            Assert.assertNotNull(topicAuto.getId());
            Assert.assertEquals(0, topicAuto.getId().compareTo(i));
            i++;
        }
    }
}
