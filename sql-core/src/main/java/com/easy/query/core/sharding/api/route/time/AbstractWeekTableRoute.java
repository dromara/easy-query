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
public abstract class AbstractWeekTableRoute<TEntity> extends AbstractTimeTableRoute<TEntity> {
    @Override
    protected String formatShardingValue(LocalDateTime time) {
        LocalDateTime weekEnd = EasyUtil.getWeekEnd(time);
        String dd = weekEnd.format(DateTimeFormatter.ofPattern("dd"));
        return EasyUtil.getWeekStart(time).format(DateTimeFormatter.ofPattern("yyyyMMdd")) + "_" + dd;
    }

    @Override
    public boolean lessThanTimeStart(LocalDateTime shardingValue) {
        LocalDateTime timeWeekFirstDay = EasyUtil.getWeekStart(shardingValue);
        return shardingValue.isEqual(timeWeekFirstDay);
    }
}
