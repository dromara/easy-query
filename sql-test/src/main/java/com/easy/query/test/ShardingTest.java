package com.easy.query.test;

import com.easy.query.BaseTest;
import com.easy.query.entity.TopicSharding;

/**
 * create time 2023/4/23 23:22
 * 文件说明
 *
 * @author xuejiaming
 */
public class ShardingTest extends BaseTest {
    public void sharding1(){
        easyQuery.queryable(TopicSharding.class)
                .w
    }
}
