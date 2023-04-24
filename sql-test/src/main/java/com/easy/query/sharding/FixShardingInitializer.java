package com.easy.query.sharding;

import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.sharding.initializer.EasyShardingInitializer;
import com.easy.query.core.util.ArrayUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * create time 2023/4/24 10:14
 * 文件说明
 *
 * @author xuejiaming
 */
public class FixShardingInitializer implements EasyShardingInitializer {
    @Override
    public Map<String, Collection<String>> getInitializeTables(EntityMetadata entityMetadata) {
        String tableName = entityMetadata.getTableName();
        ArrayList<String> actualTableNames = new ArrayList<>(3);
        for (int i = 0; i < 3; i++) {
            actualTableNames.add(tableName+"_"+i);
        }
        return new LinkedHashMap<String, Collection<String>>(){{
            put("ds0",actualTableNames);
        }};
    }
}
