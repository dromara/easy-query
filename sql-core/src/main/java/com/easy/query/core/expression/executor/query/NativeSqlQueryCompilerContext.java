package com.easy.query.core.expression.executor.query;

import com.easy.query.core.expression.executor.parser.PrepareParseResult;
import com.easy.query.core.util.EasyShardingUtil;

import java.util.Set;

/**
 * create time 2023/4/11 12:14
 * 文件说明
 *
 * @author xuejiaming
 */
public class NativeSqlQueryCompilerContext implements QueryCompilerContext {
    private final Set<Class<?>> shardingEntities;
    private final boolean shardingQuery;
    private final boolean singleShardingQuery;
    public NativeSqlQueryCompilerContext(PrepareParseResult prepareParseResult){
        shardingEntities=prepareParseResult.getShardingEntities();
        int shardingEntityCount = shardingEntities.size();
        shardingQuery=shardingEntityCount>0;
        singleShardingQuery=shardingEntityCount==1;
    }
    @Override
    public Set<Class<?>> getShardingEntities() {
        return shardingEntities;
    }

    @Override
    public boolean isShardingQuery() {
        return shardingQuery;
    }

    @Override
    public boolean isSingleShardingQuery() {
        return singleShardingQuery;
    }
}
