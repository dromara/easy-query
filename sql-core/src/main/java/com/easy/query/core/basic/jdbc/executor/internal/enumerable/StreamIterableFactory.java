package com.easy.query.core.basic.jdbc.executor.internal.enumerable;

import com.easy.query.core.basic.jdbc.executor.ExecutorContext;
import com.easy.query.core.basic.jdbc.executor.ResultMetadata;
import com.easy.query.core.basic.jdbc.executor.internal.merge.result.StreamResultSet;

/**
 * create time 2025/12/11 08:59
 * 文件说明
 *
 * @author xuejiaming
 */
public interface StreamIterableFactory {
    <T> StreamIterable<T> create(ExecutorContext context, ResultMetadata<T> resultMetadata, StreamResultSet streamResultSet);
}
