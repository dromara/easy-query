package com.easy.query.test.sharding;

import com.easy.query.core.enums.ExecuteMethodEnum;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.sharding.initializer.EntityShardingInitializer;
import com.easy.query.core.sharding.initializer.ShardingEntityBuilder;
import com.easy.query.test.entity.TopicShardingDataSource;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * create time 2023/5/19 13:38
 * 文件说明
 *
 * @author xuejiaming
 */
public class DataSourceShardingInitializer implements EntityShardingInitializer<TopicShardingDataSource> {
    @Override
    public void configure(ShardingEntityBuilder<TopicShardingDataSource> builder) {
        EntityMetadata entityMetadata = builder.getEntityMetadata();
        String tableName = entityMetadata.getTableName();
        List<String> tables = Collections.singletonList(tableName);
        LinkedHashMap<String, Collection<String>> initTables = new LinkedHashMap<String, Collection<String>>() {{
            put("ds2020", tables);
            put("ds2021", tables);
            put("ds2022", tables);
            put("ds2023", tables);
        }};
        builder.actualTableNameInit(initTables)
                .paginationReverse(0.5, 100L)
                .ascSequenceConfigure(new DataSourceAndTableComparator())
                .addPropertyDefaultUseDesc("createTime")
                .defaultAffectedMethod(false, ExecuteMethodEnum.LIST, ExecuteMethodEnum.ANY, ExecuteMethodEnum.FIRST, ExecuteMethodEnum.COUNT)
                .useMaxShardingQueryLimit(1, ExecuteMethodEnum.FIRST);


    }
}
