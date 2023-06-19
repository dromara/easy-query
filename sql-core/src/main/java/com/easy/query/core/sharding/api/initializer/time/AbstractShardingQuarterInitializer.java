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
public abstract class AbstractShardingQuarterInitializer<T> extends AbstractShardingTimeInitializer<T> {


    @Override
    protected LocalDateTime getBeginTimeToStart(LocalDateTime beginTime) {
        return EasyUtil.getQuarterStart(beginTime);
    }

    @Override
    protected LocalDateTime getNextTime(LocalDateTime currentTime) {
        return EasyUtil.getQuarterStart(currentTime).plusMonths(3);
    }

    @Override
    protected String formatTail(LocalDateTime time) {

        return EasyUtil.getQuarterStart(time).format(DateTimeFormatter.ofPattern("'Q'Q"));
//        return time.format(DateTimeFormatter.ofPattern("yyyyMM"));
    }

    @Override
    public long calcNextTime() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime monthStart = EasyUtil.getQuarterStart(now.plusMonths(3));//获取下个季度月初
        return monthStart.toEpochSecond(ZoneOffset.ofHours(8)) * 1000;
    }

}
