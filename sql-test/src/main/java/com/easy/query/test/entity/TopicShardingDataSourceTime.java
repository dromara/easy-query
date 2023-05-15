package com.easy.query.test.entity;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.ShardingDataSourceKey;
import com.easy.query.core.annotation.ShardingTableKey;
import com.easy.query.core.annotation.Table;
import com.easy.query.test.sharding.DataSourceAndTableShardingInitializer;
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
@Table(value = "t_topic_sharding_ds_time",shardingInitializer = DataSourceAndTableShardingInitializer.class)
@ToString
public class TopicShardingDataSourceTime {

    @Column(primaryKey = true)
    private String id;
    private Integer stars;
    private String title;
    @ShardingTableKey
    @ShardingDataSourceKey
    private LocalDateTime createTime;
}
