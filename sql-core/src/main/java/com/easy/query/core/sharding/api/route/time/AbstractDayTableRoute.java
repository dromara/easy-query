package com.easy.query.core.sharding.api.route.time;

import com.easy.query.core.util.EasyUtil;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * create time 2023/5/19 14:34
 * 文件说明
 *
 * @author xuejiaming
 */
public abstract class AbstractDayTableRoute<TEntity> extends AbstractTimeTableRoute<TEntity> {
    @Override
    protected String formatShardingValue(LocalDateTime time){
        return time.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
    }

    @Override
    public boolean lessThanTimeStart(LocalDateTime shardingValue) {
        LocalDateTime timeDayFirstDay = EasyUtil.getDayStart(shardingValue);
        return shardingValue.isEqual(timeDayFirstDay);
    }
}
