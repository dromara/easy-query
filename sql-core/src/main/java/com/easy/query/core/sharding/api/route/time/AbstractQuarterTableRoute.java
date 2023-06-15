package com.easy.query.core.sharding.api.route.time;

import com.easy.query.core.util.EasyUtil;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * create time 2023/5/19 14:34
 * 分片按季度格式化表后缀Q1,Q2,Q3,Q4......
 *
 * @author xuejiaming
 */
public abstract class AbstractQuarterTableRoute<TEntity> extends AbstractTimeTableRoute<TEntity> {
    @Override
    protected String formatShardingValue(LocalDateTime time) {
        return EasyUtil.getQuarterStart(time).format(DateTimeFormatter.ofPattern("'Q'Q"));
    }

    @Override
    public boolean lessThanTimeStart(LocalDateTime shardingValue) {
        LocalDateTime quarterFirstDay = EasyUtil.getQuarterStart(shardingValue);
        return shardingValue.isEqual(quarterFirstDay);
    }
}
