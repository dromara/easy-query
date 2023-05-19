package com.easy.query.core.sharding.api.initializer.time;

import com.easy.query.core.util.EasyUtil;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * create time 2023/5/19 14:03
 * 文件说明
 *
 * @author xuejiaming
 */
public abstract class AbstractShardingYearInitializer<T> extends AbstractShardingTimeInitializer<T> {

    @Override
    protected LocalDateTime getBeginTimeToStart(LocalDateTime beginTime) {
        return EasyUtil.getYearStart(beginTime);
    }

    @Override
    protected LocalDateTime getNextTime(LocalDateTime currentTime) {
        return currentTime.plusYears(1);
    }

    @Override
    protected String formatTail(LocalDateTime time) {
        return time.format(DateTimeFormatter.ofPattern("yyyy"));
    }
}
