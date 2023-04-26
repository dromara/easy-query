package com.easy.query.test;

import com.easy.query.BaseTest;
import com.easy.query.entity.TopicSharding;
import org.junit.Assert;
import org.junit.Test;

import java.time.LocalDateTime;

/**
 * create time 2023/4/23 23:22
 * 文件说明
 *
 * @author xuejiaming
 */
public class ShardingTest extends BaseTest {
    @Test
    public void sharding1(){
        TopicSharding topicSharding = easyQuery.queryable(TopicSharding.class)
                .whereById("123").firstOrNull();
        Assert.assertNull(topicSharding);
    }
    @Test
    public void sharding2(){

        TopicSharding topicShardingTest = easyQuery.queryable(TopicSharding.class)
                .whereById("999999").firstOrNull();
        if(topicShardingTest!=null){
            long l = easyQuery.deletable(topicShardingTest).executeRows();
            Assert.assertEquals(1,l);
        }

        TopicSharding topicSharding = new TopicSharding();
        topicSharding.setId("999999");
        topicSharding.setTitle("title999999");
        topicSharding.setStars(999999);
        topicSharding.setCreateTime(LocalDateTime.now());
        long l = easyQuery.insertable(topicSharding).executeRows();

        TopicSharding topicSharding1 = easyQuery.queryable(TopicSharding.class)
                .whereById("999999").firstOrNull();
        Assert.assertNotNull(topicSharding1);
    }
}
