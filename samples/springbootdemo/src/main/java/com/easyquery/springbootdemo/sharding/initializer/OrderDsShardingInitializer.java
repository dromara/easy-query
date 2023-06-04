package com.easyquery.springbootdemo.sharding.initializer;

import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.sharding.initializer.EntityShardingInitializer;
import com.easy.query.core.sharding.initializer.ShardingEntityBuilder;
import com.easyquery.springbootdemo.domain.OrderDsEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;

/**
 * create time 2023/6/4 21:06
 * 文件说明
 *
 * @author xuejiaming
 */
@Component
public class OrderDsShardingInitializer implements EntityShardingInitializer<OrderDsEntity> {
    @Override
    public void configure(ShardingEntityBuilder<OrderDsEntity> builder) {
        EntityMetadata entityMetadata = builder.getEntityMetadata();
        String tableName = entityMetadata.getTableName();
        ArrayList<String> actualTableNames = new ArrayList<>(3);
        LinkedHashMap<String, Collection<String>> initTables = new LinkedHashMap<String, Collection<String>>();
        for (int i = 0; i < 4; i++) {
            initTables.put("ds" + i, Collections.singleton(tableName));
        }
        builder.actualTableNameInit(initTables)
                .paginationReverse(0.5, 100L);
//                .ascSequenceConfigure(new DataSourceAndTableComparator())
//                .addPropertyDefaultUseDesc(TopicShardingDataSourceTime::getCreateTime)
//                .defaultAffectedMethod(false, ExecuteMethodEnum.LIST, ExecuteMethodEnum.ANY, ExecuteMethodEnum.FIRST, ExecuteMethodEnum.COUNT)
//                .useMaxShardingQueryLimit(1, ExecuteMethodEnum.FIRST);
    }
}
