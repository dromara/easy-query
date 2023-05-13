package com.easy.query.test.sharding;

import com.easy.query.core.enums.ExecuteMethodEnum;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.sharding.initializer.EasyShardingInitializer;
import com.easy.query.core.sharding.initializer.ShardingEntityBuilder;
import com.easy.query.test.entity.TopicSharding;
import com.easy.query.test.entity.TopicShardingTime;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;

/**
 * create time 2023/4/24 10:14
 * 文件说明
 *
 * @author xuejiaming
 */
public class FixShardingInitializer implements EasyShardingInitializer {
    @Override
    public void configure(ShardingEntityBuilder<?> builder) {
        EntityMetadata entityMetadata = builder.getEntityMetadata();
        if(TopicSharding.class.equals(entityMetadata.getEntityClass())){

            String tableName = entityMetadata.getTableName();
            ArrayList<String> actualTableNames = new ArrayList<>(3);
            for (int i = 0; i < 3; i++) {
                actualTableNames.add(tableName+"_"+i);
            }
            LinkedHashMap<String, Collection<String>> initTables = new LinkedHashMap<String, Collection<String>>() {{
                put("ds2020", actualTableNames);
            }};
           builder.actualTableNameInit(initTables);
        }else if(TopicShardingTime.class.equals(entityMetadata.getEntityClass())){

            String tableName = entityMetadata.getTableName();
            LocalDateTime beginTime = LocalDateTime.of(2020, 1, 1, 1, 1);
            LocalDateTime endTime = LocalDateTime.of(2023, 5, 1, 1, 1);

            ArrayList<String> actualTableNames = new ArrayList<>();
            while(beginTime.isBefore(endTime)){
                String month = beginTime.format(DateTimeFormatter.ofPattern("yyyyMM"));
                actualTableNames.add(tableName+"_"+month);
                beginTime=beginTime.plusMonths(1);
            }
            LinkedHashMap<String, Collection<String>> initTables = new LinkedHashMap<String, Collection<String>>() {{
                put("ds2020", actualTableNames);
            }};

            ((ShardingEntityBuilder<TopicShardingTime>) builder).actualTableNameInit(initTables)
                    .ascSequenceConfigure(String::compareToIgnoreCase)
                    .addPropertyDefaultUseDesc(TopicShardingTime::getCreateTime)
                    .defaultAffectedMethod(false,ExecuteMethodEnum.LIST,ExecuteMethodEnum.ANY,ExecuteMethodEnum.COUNT,ExecuteMethodEnum.FIRST)
                    .useMaxShardingQueryLimit(2);
        }else{
            throw new UnsupportedOperationException();
        }
    }
}
