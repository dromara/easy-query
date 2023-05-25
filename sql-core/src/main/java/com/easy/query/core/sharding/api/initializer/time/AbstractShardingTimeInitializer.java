package com.easy.query.core.sharding.api.initializer.time;

import com.easy.query.core.configuration.EasyQueryOption;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.sharding.initializer.EntityShardingInitializer;
import com.easy.query.core.sharding.initializer.ShardingEntityBuilder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * create time 2023/5/19 14:03
 * 文件说明
 *
 * @author xuejiaming
 */
public abstract class AbstractShardingTimeInitializer<T> implements EntityShardingInitializer<T> {
    protected abstract LocalDateTime getBeginTime();
    protected  LocalDateTime getEndTime(){
        return LocalDateTime.now();
    }
    protected abstract LocalDateTime getBeginTimeToStart(LocalDateTime beginTime);
    protected abstract LocalDateTime getNextTime(LocalDateTime currentTime);
    protected String getTableSeparator() {
        return "_";
    }
    protected abstract String formatTail(LocalDateTime time);
    @Override
    public void configure(ShardingEntityBuilder<T> builder) {
        EntityMetadata entityMetadata = builder.getEntityMetadata();
        EasyQueryOption easyQueryOption = builder.getEasyQueryOption();
        String tableName = entityMetadata.getTableName();
        LocalDateTime setBeginTime = getBeginTime();

        LocalDateTime beginTime = getBeginTimeToStart(setBeginTime);
        LocalDateTime endTime = getEndTime();
        if(beginTime.isAfter(endTime)){
            throw new IllegalArgumentException("begin time:"+beginTime+" is after end time:"+endTime);
        }
        String tableSeparator = getTableSeparator();

        ArrayList<String> actualTableNames = new ArrayList<>();
        //应该是不在endTime后可以等于endTime
        while(!beginTime.isAfter(endTime)){
            String tail =formatTail(beginTime);
            actualTableNames.add(tableName+tableSeparator+tail);
            beginTime=getNextTime(beginTime);
        }
        Map<String, Collection<String>> initTables = new LinkedHashMap<String, Collection<String>>() {{
            put(easyQueryOption.getDefaultDataSourceName(), actualTableNames);
        }};
        builder.actualTableNameInit(initTables);
        configure0(builder);
    }
    public abstract void configure0(ShardingEntityBuilder<T> builder);
}
