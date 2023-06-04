package com.easyquery.springbootdemo.sharding.initializer;

import com.easy.query.core.sharding.api.initializer.time.AbstractShardingMonthInitializer;
import com.easy.query.core.sharding.initializer.ShardingEntityBuilder;
import com.easyquery.springbootdemo.domain.OrderByMonthEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * create time 2023/5/25 08:30
 * 文件说明
 *
 * @author xuejiaming
 */
@Component
public class OrderByMonthShardingInitializer extends AbstractShardingMonthInitializer<OrderByMonthEntity> {
    /**
     * 开始时间不可以使用LocalDateTime.now()因为会导致每次启动开始时间都不一样
     *
     * @return
     */
    @Override
    protected LocalDateTime getBeginTime() {
        return LocalDateTime.of(2022, 1, 1, 0, 0);
    }

    /**
     * 如果不设置那么就是当前时间,用于程序启动后自动计算应该有的表包括最后时间
     *
     * @return
     */
    @Override
    protected LocalDateTime getEndTime() {
        return LocalDateTime.of(2023, 5, 31, 0, 0);
    }

    @Override
    public void configure0(ShardingEntityBuilder<OrderByMonthEntity> builder) {
        //后续用来实现优化分表
    }
}
