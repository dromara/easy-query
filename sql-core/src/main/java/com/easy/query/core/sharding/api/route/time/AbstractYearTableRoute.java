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
public abstract class AbstractYearTableRoute<TEntity> extends AbstractTimeTableRoute<TEntity> {
    @Override
    protected String formatShardingValue(LocalDateTime time) {
        return time.format(DateTimeFormatter.ofPattern("yyyy"));
    }

    @Override
    public boolean lessThanTimeStart(LocalDateTime shardingValue) {
        LocalDateTime timeYearFirstDay = EasyUtil.getYearStart(shardingValue);
        return shardingValue.isEqual(timeYearFirstDay);
    }
}
