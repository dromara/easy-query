package com.easy.query.core.basic.jdbc.executor.internal.enumerable;

import com.easy.query.core.basic.jdbc.executor.ExecutorContext;
import com.easy.query.core.basic.jdbc.executor.ResultMetadata;
import com.easy.query.core.basic.jdbc.executor.internal.merge.result.StreamResultSet;

/**
 * create time 2025/12/11 09:00
 * 文件说明
 *
 * @author xuejiaming
 */
public class DefaultStreamIterableFactory implements StreamIterableFactory{
    @Override
    public <T> StreamIterable<T> create(ExecutorContext context, ResultMetadata<T> resultMetadata, StreamResultSet streamResultSet) {
        return new DefaultStreamIterable<>(context, resultMetadata, streamResultSet);
    }
}
