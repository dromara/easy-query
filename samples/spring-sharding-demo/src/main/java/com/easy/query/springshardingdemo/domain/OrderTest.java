package com.easy.query.springshardingdemo.domain;

import com.easy.query.core.annotation.Column;
import com.easy.query.core.annotation.ShardingTableKey;
import com.easy.query.core.annotation.Table;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * create time 2023/11/13 22:10
 * 文件说明
 *
 * @author xuejiaming
 */
@Table("order_test")
@Data
public class OrderTest {
    @Column(primaryKey = true)
    private String id;
    private String name;
    @ShardingTableKey
    private LocalDateTime createTime;
}
