package com.easy.query.core.sharding.merge.context;

import com.easy.query.core.expression.executor.parser.SequenceParseResult;

/**
 * create time 2023/5/8 08:51
 * 文件说明
 *
 * @author xuejiaming
 */
public final class MergeSequenceOrder {
    private final SequenceParseResult sequenceOrderPrepareParseResult;

    public MergeSequenceOrder(SequenceParseResult sequenceOrderPrepareParseResult) {
        this.sequenceOrderPrepareParseResult = sequenceOrderPrepareParseResult;
    }

    public int getConnectionsLimit() {
        return sequenceOrderPrepareParseResult.getConnectionsLimit();
    }

}
