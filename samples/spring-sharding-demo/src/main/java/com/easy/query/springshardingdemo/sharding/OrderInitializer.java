package com.easy.query.springshardingdemo.sharding;

import com.easy.query.core.sharding.api.initializer.time.AbstractShardingMonthInitializer;
import com.easy.query.core.sharding.initializer.ShardingEntityBuilder;
import com.easy.query.springshardingdemo.domain.OrderEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * create time 2023/6/26 21:52
 * 文件说明
 *
 * @author xuejiaming
 */
@Component
public class OrderInitializer extends AbstractShardingMonthInitializer<OrderEntity> {
    /**
     * 分片起始时间
     * @return
     */
    @Override
    protected LocalDateTime getBeginTime() {
        return LocalDateTime.of(2020,1,1,0,0,0);
    }

    @Override
    protected LocalDateTime getEndTime() {
        return LocalDateTime.of(2023,12,12,0,0);
    }

    /**
     * 格式化时间到数据源
     * @param time
     * @param defaultDataSource
     * @return
     */
    @Override
    protected String formatDataSource(LocalDateTime time, String defaultDataSource) {
        String year = DateTimeFormatter.ofPattern("yyyy").format(time);
        int i = Integer.parseInt(year)-2020;

        return "ds"+i;
    }

    @Override
    public void configure0(ShardingEntityBuilder<OrderEntity> builder) {

    }
}
