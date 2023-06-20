package com.easyquery.springbootdemo.domain;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.ShardingDataSourceKey;
import com.easy.query.core.annotation.ShardingTableKey;
import com.easy.query.core.annotation.Table;
import com.easyquery.springbootdemo.sharding.initializer.MyOrderShardingInitializer;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * create time 2023/5/24 23:49
 * 文件说明
 *
 * @author xuejiaming
 */
@Data
@Table(value = "t_my_order", shardingInitializer = MyOrderShardingInitializer.class)
public class MyOrderEntity {
    @Column(primaryKey = true)
    private String id;
    private Integer orderNo;
    @ShardingTableKey
    @ShardingDataSourceKey
    private LocalDateTime createTime;
}
