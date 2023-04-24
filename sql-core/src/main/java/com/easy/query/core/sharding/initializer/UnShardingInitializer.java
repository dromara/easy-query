package com.easy.query.core.sharding.initializer;

import com.easy.query.core.metadata.EntityMetadata;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

/**
 * create time 2023/4/24 09:50
 * 文件说明
 *
 * @author xuejiaming
 */
public class UnShardingInitializer implements EasyShardingInitializer {
    @Override
    public Map<String, Collection<String>> getInitializeTables(EntityMetadata entityMetadata) {
        return Collections.emptyMap();
    }
}
