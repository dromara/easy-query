package com.easyquery.springbootdemo.domain;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.ShardingDataSourceKey;
import com.easy.query.core.annotation.Table;
import com.easyquery.springbootdemo.sharding.initializer.OrderDsShardingInitializer;
import lombok.Data;

/**
 * create time 2023/5/24 23:49
 * 文件说明
 *
 * @author xuejiaming
 */
@Data
@Table(value = "order_ds", shardingInitializer = OrderDsShardingInitializer.class)
public class OrderDsEntity {
    @Column(primaryKey = true)
    @ShardingDataSourceKey
    private String id;
    private String uid;
    private Integer orderNo;
}
