package com.easyquery.springbootdemo.domain;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.ShardingTableKey;
import com.easy.query.core.annotation.Table;
import com.easyquery.springbootdemo.sharding.initializer.OrderShardingInitializer;
import lombok.Data;

/**
 * create time 2023/5/24 23:49
 * 文件说明
 *
 * @author xuejiaming
 */
@Data
@Table(value = "order", shardingInitializer = OrderShardingInitializer.class)
public class OrderEntity {
    @Column(primaryKey = true)
    @ShardingTableKey
    private String id;
    private String uid;
    private Integer orderNo;
}
