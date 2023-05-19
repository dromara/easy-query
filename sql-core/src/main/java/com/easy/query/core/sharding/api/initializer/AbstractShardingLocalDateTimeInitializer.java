package com.easy.query.core.sharding.api.initializer;

import com.easy.query.core.configuration.EasyQueryOption;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.sharding.initializer.EntityShardingInitializer;
import com.easy.query.core.sharding.initializer.ShardingEntityBuilder;
import com.easy.query.core.util.EasyUtil;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
public abstract class AbstractShardingLocalDateTimeInitializer<T> implements EntityShardingInitializer<T> {
    protected abstract LocalDateTime getBeginTime();
    protected abstract LocalDateTime getBeginTimeToStart(LocalDateTime beginTime);
    protected abstract LocalDateTime getNextTime(LocalDateTime currentTime);
    protected abstract String getTableSeparator();
    protected abstract String formatTail(LocalDateTime time);
    @Override
    public void configure(ShardingEntityBuilder<T> builder) {
        EntityMetadata entityMetadata = builder.getEntityMetadata();
        EasyQueryOption easyQueryOption = builder.getEasyQueryOption();
        String tableName = entityMetadata.getTableName();
        LocalDateTime setBeginTime = getBeginTime();

        LocalDateTime beginTime = getBeginTimeToStart(setBeginTime);
        String tableSeparator = getTableSeparator();
        LocalDateTime endTime = LocalDateTime.now();

        ArrayList<String> actualTableNames = new ArrayList<>();
        while(beginTime.isBefore(endTime)){
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
