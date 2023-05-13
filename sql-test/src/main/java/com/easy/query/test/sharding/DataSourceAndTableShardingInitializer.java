package com.easy.query.test.sharding;

import com.easy.query.core.enums.ExecuteMethodEnum;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.sharding.initializer.EasyShardingInitializer;
import com.easy.query.core.sharding.initializer.ShardingEntityBuilder;
import com.easy.query.test.entity.TopicShardingDataSourceTime;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;

/**
 * create time 2023/5/11 22:03
 * 文件说明
 *
 * @author xuejiaming
 */
public class DataSourceAndTableShardingInitializer implements EasyShardingInitializer {
    @Override
    public void configure(ShardingEntityBuilder<?> builder) {
        EntityMetadata entityMetadata = builder.getEntityMetadata();

        String tableName = entityMetadata.getTableName();
        LinkedHashMap<String, Collection<String>> initTables = new LinkedHashMap<String, Collection<String>>();
        {

            LocalDateTime beginTime = LocalDateTime.of(2020, 1, 1, 1, 1);
            LocalDateTime endTime = LocalDateTime.of(2021, 1, 1, 1, 1);

            ArrayList<String> actualTableNames = new ArrayList<>();
            while(beginTime.isBefore(endTime)){
                String month = beginTime.format(DateTimeFormatter.ofPattern("yyyyMM"));
                actualTableNames.add(tableName+"_"+month);
                beginTime=beginTime.plusMonths(1);
            }
            initTables.put("ds2020", actualTableNames);
        }
        {

            LocalDateTime beginTime = LocalDateTime.of(2021, 1, 1, 1, 1);
            LocalDateTime endTime = LocalDateTime.of(2022, 1, 1, 1, 1);

            ArrayList<String> actualTableNames = new ArrayList<>();
            while(beginTime.isBefore(endTime)){
                String month = beginTime.format(DateTimeFormatter.ofPattern("yyyyMM"));
                actualTableNames.add(tableName+"_"+month);
                beginTime=beginTime.plusMonths(1);
            }
            initTables.put("ds2021", actualTableNames);
        }
        {

            LocalDateTime beginTime = LocalDateTime.of(2022, 1, 1, 1, 1);
            LocalDateTime endTime = LocalDateTime.of(2023, 1, 1, 1, 1);

            ArrayList<String> actualTableNames = new ArrayList<>();
            while(beginTime.isBefore(endTime)){
                String month = beginTime.format(DateTimeFormatter.ofPattern("yyyyMM"));
                actualTableNames.add(tableName+"_"+month);
                beginTime=beginTime.plusMonths(1);
            }
            initTables.put("ds2022", actualTableNames);
        }
        {

            LocalDateTime beginTime = LocalDateTime.of(2023, 1, 1, 1, 1);
            LocalDateTime endTime = LocalDateTime.of(2023, 5, 1, 1, 1);

            ArrayList<String> actualTableNames = new ArrayList<>();
            while(beginTime.isBefore(endTime)){
                String month = beginTime.format(DateTimeFormatter.ofPattern("yyyyMM"));
                actualTableNames.add(tableName+"_"+month);
                beginTime=beginTime.plusMonths(1);
            }
            initTables.put("ds2023", actualTableNames);
        }
        ((ShardingEntityBuilder<TopicShardingDataSourceTime>)builder).actualTableNameInit(initTables)
                .paginationReverse(0.5,100L)
                .ascSequenceConfigure(new DataSourceAndTableComparator())
                .addPropertyDefaultUseDesc(TopicShardingDataSourceTime::getCreateTime)
                .defaultAffectedMethod(false, ExecuteMethodEnum.LIST,ExecuteMethodEnum.ANY,ExecuteMethodEnum.FIRST,ExecuteMethodEnum.COUNT);
    }
}
