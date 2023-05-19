package com.easy.query.core.sharding.api.initializer;

import com.easy.query.core.util.EasyUtil;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * create time 2023/5/19 14:03
 * 文件说明
 *
 * @author xuejiaming
 */
public abstract class AbstractShardingYearLocalDateTimeInitializer<T> extends AbstractShardingLocalDateTimeInitializer<T> {

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
