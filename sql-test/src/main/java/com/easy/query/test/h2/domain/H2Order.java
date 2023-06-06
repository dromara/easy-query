package com.easy.query.test.h2.domain;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.ShardingTableKey;
import com.easy.query.core.annotation.Table;
import com.easy.query.test.h2.sharding.H2OrderShardingInitializer;
import lombok.Data;

/**
 * create time 2023/6/6 21:52
 * 文件说明
 *
 * @author xuejiaming
 */
@Data
@Table(value = "t_order", shardingInitializer = H2OrderShardingInitializer.class)
public class H2Order {
    @Column(primaryKey = true)
    @ShardingTableKey
    private Integer id;
    private Integer status;
    private String created;
}
