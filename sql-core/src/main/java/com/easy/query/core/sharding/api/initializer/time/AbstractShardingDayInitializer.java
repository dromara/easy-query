package com.easy.query.core.sharding.api.initializer.time;

import com.easy.query.core.util.EasyUtil;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

/**
 * create time 2023/5/19 14:03
 * 文件说明
 *
 * @author xuejiaming
 */
public abstract class AbstractShardingDayInitializer<T> extends AbstractShardingTimeInitializer<T> {

    @Override
    protected LocalDateTime getBeginTimeToStart(LocalDateTime beginTime) {
        return beginTime.toLocalDate().atStartOfDay();
    }

    @Override
    protected LocalDateTime getNextTime(LocalDateTime currentTime) {
        return currentTime.plusDays(1);
    }

    @Override
    protected String formatTail(LocalDateTime time) {
        return time.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
    }

    @Override
    public long calcNextTime() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime dayStart = EasyUtil.getDayStart(now.plusDays(1));
        return dayStart.toEpochSecond(ZoneOffset.ofHours(8)) * 1000;
    }
}
