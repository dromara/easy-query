package com.easy.query.springshardingdemo.sharding;

import com.easy.query.core.sharding.api.route.time.AbstractMonthTableRoute;
import com.easy.query.springshardingdemo.domain.OrderEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * create time 2023/6/26 22:10
 * 文件说明
 *
 * @author xuejiaming
 */
@Component
public class OrderTableRoute extends AbstractMonthTableRoute<OrderEntity> {

    @Override
    protected LocalDateTime convertLocalDateTime(Object shardingValue) {
        return (LocalDateTime)shardingValue;
    }
}
