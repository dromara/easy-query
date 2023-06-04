package com.easyquery.springbootdemo.domain;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.ShardingTableKey;
import com.easy.query.core.annotation.Table;
import com.easyquery.springbootdemo.sharding.initializer.OrderByMonthShardingInitializer;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * create time 2023/5/25 08:29
 * 文件说明
 *
 * @author xuejiaming
 */
@Data
@Table(value = "t_order", shardingInitializer = OrderByMonthShardingInitializer.class)
public class OrderByMonthEntity {

    @Column(primaryKey = true)
    private String id;
    private String uid;
    private Integer orderNo;
    /**
     * 分片键改为时间
     */
    @ShardingTableKey
    private LocalDateTime createTime;
}
