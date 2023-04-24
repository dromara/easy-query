package com.easy.query.test;

import com.easy.query.BaseTest;
import com.easy.query.entity.TopicSharding;
import org.junit.Test;

import java.util.List;

/**
 * create time 2023/4/23 23:22
 * 文件说明
 *
 * @author xuejiaming
 */
public class ShardingTest extends BaseTest {
//    @Test
    public void sharding1(){
        TopicSharding topicSharding = easyQuery.queryable(TopicSharding.class)
                .whereById("123").firstOrNull();
        System.out.println("1");

    }
}
