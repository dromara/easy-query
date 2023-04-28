package com.easy.query.core.sharding.merge;

import com.easy.query.core.basic.jdbc.executor.ExecutorContext;
import com.easy.query.core.expression.executor.parser.ExecutionContext;
import com.easy.query.core.expression.executor.parser.PrepareParseResult;
import com.easy.query.core.util.ArrayUtil;

/**
 * create time 2023/4/27 22:42
 * 文件说明
 *
 * @author xuejiaming
 */
public class EntityStreamMergeContext extends EasyStreamMergeContext{
    private final PrepareParseResult prepareParseResult;
    private final boolean isSharding;

    public EntityStreamMergeContext(ExecutorContext executorContext, ExecutionContext executionContext, PrepareParseResult prepareParseResult) {
        super(executorContext, executionContext);
        this.prepareParseResult = prepareParseResult;
        this.isSharding= ArrayUtil.isNotEmpty(prepareParseResult.getShardingEntities());
    }

    @Override
    public boolean isSharding() {
        return isSharding;
    }
}
