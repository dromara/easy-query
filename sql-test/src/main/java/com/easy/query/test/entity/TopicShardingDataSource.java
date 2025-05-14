package com.easy.query.test.entity;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.annotation.ShardingDataSourceKey;
import com.easy.query.core.annotation.ShardingTableKey;
import com.easy.query.core.annotation.Table;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.test.entity.proxy.TopicShardingDataSourceProxy;
import com.easy.query.test.sharding.DataSourceAndTableShardingInitializer;
import com.easy.query.test.sharding.DataSourceShardingInitializer;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * create time 2023/5/11 16:32
 * 文件说明
 *
 * @author xuejiaming
 */
@Data
@Table(value = "t_topic_sharding_ds",shardingInitializer = DataSourceShardingInitializer.class)
@ToString
@EntityProxy
public class TopicShardingDataSource implements ProxyEntityAvailable<TopicShardingDataSource , TopicShardingDataSourceProxy> {

    @Column(primaryKey = true)
    private String id;
    private Integer stars;
    private String title;
    @ShardingDataSourceKey
    private LocalDateTime createTime;
}
