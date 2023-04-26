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
    public void sharding1() {
        TopicSharding topicSharding = easyQuery.queryable(TopicSharding.class)
                .whereById("123").firstOrNull();
        Assert.assertNull(topicSharding);
    }

    @Test
    public void sharding2() {
        String id = "999999";
        TopicSharding topicShardingTest = easyQuery.queryable(TopicSharding.class)
                .whereById(id).firstOrNull();
        if (topicShardingTest != null) {
            long l = easyQuery.deletable(topicShardingTest).executeRows();
            Assert.assertEquals(1, l);
        }

        TopicSharding topicSharding = new TopicSharding();
        topicSharding.setId(id);
        topicSharding.setTitle("title" + id);
        topicSharding.setStars(Integer.parseInt(id));
        topicSharding.setCreateTime(LocalDateTime.now());
        long l = easyQuery.insertable(topicSharding).executeRows();

        TopicSharding topicSharding1 = easyQuery.queryable(TopicSharding.class)
                .whereById(id).firstOrNull();
        Assert.assertNotNull(topicSharding1);
        long l1 = easyQuery.deletable(TopicSharding.class).whereById(id).executeRows();
        Assert.assertEquals(1, l1);
    }
}
