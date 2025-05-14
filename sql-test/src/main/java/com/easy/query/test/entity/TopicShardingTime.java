package com.easy.query.test.entity;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.annotation.ShardingExtraTableKey;
import com.easy.query.core.annotation.ShardingTableKey;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.test.entity.proxy.TopicShardingTimeProxy;
import com.easy.query.test.sharding.TopicShardingTimeShardingInitializer;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * create time 2023/5/6 14:36
 * 文件说明
 *
 * @author xuejiaming
 */
@Data
@Table(value = "t_topic_sharding_time",shardingInitializer = TopicShardingTimeShardingInitializer.class)
@ToString
@EntityProxy
public class TopicShardingTime implements ProxyEntityAvailable<TopicShardingTime , TopicShardingTimeProxy> {

    @Column(primaryKey = true)
    @ShardingExtraTableKey
    private String id;
    private Integer stars;
    private String title;
    @ShardingTableKey
    private LocalDateTime createTime;
}
