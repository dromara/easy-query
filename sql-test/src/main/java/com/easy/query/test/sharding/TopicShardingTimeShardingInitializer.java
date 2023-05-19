package com.easy.query.test.sharding;

import com.easy.query.core.enums.ExecuteMethodEnum;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.sharding.common.TableNameStringComparator;
import com.easy.query.core.sharding.initializer.EntityShardingInitializer;
import com.easy.query.core.sharding.initializer.ShardingEntityBuilder;
import com.easy.query.test.entity.TopicShardingTime;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;

/**
 * create time 2023/5/15 12:40
 * 文件说明
 *
 * @author xuejiaming
 */
public class TopicShardingTimeShardingInitializer implements EntityShardingInitializer<TopicShardingTime> {
    @Override
    public void configure(ShardingEntityBuilder<TopicShardingTime> builder) {
        EntityMetadata entityMetadata = builder.getEntityMetadata();
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

       builder.actualTableNameInit(initTables)
                .paginationReverse(0.5,100)
                .ascSequenceConfigure(new TableNameStringComparator())
                .addPropertyDefaultUseDesc(TopicShardingTime::getCreateTime)
                .defaultAffectedMethod(false, ExecuteMethodEnum.LIST,ExecuteMethodEnum.ANY,ExecuteMethodEnum.COUNT,ExecuteMethodEnum.FIRST)
                .useMaxShardingQueryLimit(2,ExecuteMethodEnum.LIST,ExecuteMethodEnum.ANY,ExecuteMethodEnum.FIRST);

    }
}
//public class TopicShardingTimeShardingInitializer extends AbstractShardingMonthLocalDateTimeInitializer<TopicShardingTime> {
//
//    @Override
//    protected LocalDateTime getBeginTime() {
//        return LocalDateTime.of(2020, 1, 1, 1, 1);
//    }
//
//    @Override
//    protected String getTableSeparator() {
//        return "_";
//    }
//
//    @Override
//    public void configure0(ShardingEntityBuilder<TopicShardingTime> builder) {
//
//        builder.paginationReverse(0.5,100)
//                .ascSequenceConfigure(new TableNameStringComparator())
//                .addPropertyDefaultUseDesc(TopicShardingTime::getCreateTime)
//                .defaultAffectedMethod(false, ExecuteMethodEnum.LIST,ExecuteMethodEnum.ANY,ExecuteMethodEnum.COUNT,ExecuteMethodEnum.FIRST)
//                .useMaxShardingQueryLimit(2,ExecuteMethodEnum.LIST,ExecuteMethodEnum.ANY,ExecuteMethodEnum.FIRST);
//
//    }
//}
