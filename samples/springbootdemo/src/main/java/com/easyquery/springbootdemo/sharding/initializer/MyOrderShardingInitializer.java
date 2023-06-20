package com.easyquery.springbootdemo.sharding.initializer;

import com.easy.query.core.sharding.api.initializer.time.AbstractShardingMonthInitializer;
import com.easy.query.core.sharding.initializer.ShardingEntityBuilder;
import com.easyquery.springbootdemo.domain.MyOrderEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * create time 2023/6/19 22:16
 * 文件说明
 *
 * @author xuejiaming
 */
@Component
public class MyOrderShardingInitializer extends AbstractShardingMonthInitializer<MyOrderEntity> {
    @Override
    protected LocalDateTime getBeginTime() {
        return LocalDateTime.of(2020,1,1,0,0,0);
    }

    @Override
    public void configure0(ShardingEntityBuilder<MyOrderEntity> builder) {
    }
}
