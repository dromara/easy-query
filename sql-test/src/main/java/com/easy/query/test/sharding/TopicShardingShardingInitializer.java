package com.easy.query.test.sharding;

import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.sharding.initializer.EntityShardingInitializer;
import com.easy.query.core.sharding.initializer.ShardingEntityBuilder;
import com.easy.query.test.entity.TopicSharding;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;

/**
 * create time 2023/5/15 12:39
 * 文件说明
 *
 * @author xuejiaming
 */
public class TopicShardingShardingInitializer implements EntityShardingInitializer<TopicSharding> {
    @Override
    public void configure(ShardingEntityBuilder<TopicSharding> builder) {
        EntityMetadata entityMetadata = builder.getEntityMetadata();
        String tableName = entityMetadata.getTableName();
        ArrayList<String> actualTableNames = new ArrayList<>(3);
        for (int i = 0; i < 3; i++) {
            actualTableNames.add(tableName+"_"+i);
        }
        LinkedHashMap<String, Collection<String>> initTables = new LinkedHashMap<String, Collection<String>>() {{
            put("ds2020", actualTableNames);
        }};
        builder.actualTableNameInit(initTables);
    }
}
