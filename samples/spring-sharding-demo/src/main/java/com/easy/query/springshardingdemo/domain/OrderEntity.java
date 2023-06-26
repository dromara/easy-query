package com.easy.query.springshardingdemo.domain;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.annotation.ShardingDataSourceKey;
import com.easy.query.core.annotation.ShardingTableKey;
import com.easy.query.core.annotation.Table;
import com.easy.query.springshardingdemo.sharding.OrderInitializer;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * create time 2023/6/26 21:51
 * 文件说明
 *
 * @author xuejiaming
 */
@Data
@Table(value = "t_order", shardingInitializer = OrderInitializer.class)
@EntityProxy
public class OrderEntity {
    @Column(primaryKey = true)
    private String id;
    private Integer orderNo;
    private String userId;
    @ShardingTableKey
    @ShardingDataSourceKey
    private LocalDateTime createTime;
}
