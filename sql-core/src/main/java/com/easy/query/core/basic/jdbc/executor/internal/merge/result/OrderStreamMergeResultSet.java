package com.easy.query.core.basic.jdbc.executor.internal.merge.result;

import java.util.List;

/**
 * create time 2023/4/27 12:49
 * 文件说明
 *
 * @author xuejiaming
 */
public interface OrderStreamMergeResultSet extends ShardingStreamResultSet,Comparable<OrderStreamMergeResultSet> {
    List<Comparable<?>> compareValues();
}
