package com.easy.query.core.sharding.merge.context;

import com.easy.query.core.expression.executor.parser.SequenceOrderPrepareParseResult;
import com.easy.query.core.expression.parser.core.available.TableAvailable;

import java.util.Comparator;

/**
 * create time 2023/5/8 08:51
 * 文件说明
 *
 * @author xuejiaming
 */
public final class MergeSequenceOrder {
    private final SequenceOrderPrepareParseResult sequenceOrderPrepareParseResult;

    public MergeSequenceOrder(SequenceOrderPrepareParseResult sequenceOrderPrepareParseResult) {
        this.sequenceOrderPrepareParseResult = sequenceOrderPrepareParseResult;
    }

    public int getConnectionsLimit() {
        return sequenceOrderPrepareParseResult.getConnectionsLimit();
    }

}
