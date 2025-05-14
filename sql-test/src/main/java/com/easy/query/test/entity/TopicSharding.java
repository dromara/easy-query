package com.easy.query.test.entity;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.annotation.ProxyProperty;
import com.easy.query.core.annotation.ShardingTableKey;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.test.entity.proxy.TopicShardingProxy;
import com.easy.query.test.sharding.TopicShardingShardingInitializer;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * @FileName: Topic.java
 * @Description: 文件说明
 * create time 2023/3/16 21:26
 * @author xuejiaming
 */
@Data
@Table(value = "t_topic_sharding",shardingInitializer = TopicShardingShardingInitializer.class)
@ToString
@EntityProxy
public class TopicSharding implements ProxyEntityAvailable<TopicSharding , TopicShardingProxy> {

    @Column(primaryKey = true)
    @ShardingTableKey
    private String id;
    private Integer stars;
    @ProxyProperty("_title")
    private String title;
    private LocalDateTime createTime;
}
