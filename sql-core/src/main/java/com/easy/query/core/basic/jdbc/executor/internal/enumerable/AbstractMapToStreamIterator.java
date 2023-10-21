package com.easy.query.core.basic.jdbc.executor.internal.enumerable;

import com.easy.query.core.basic.jdbc.executor.ExecutorContext;
import com.easy.query.core.basic.jdbc.executor.ResultMetadata;
import com.easy.query.core.basic.jdbc.executor.internal.merge.result.StreamResultSet;

import java.sql.SQLException;

/**
 * create time 2023/10/20 22:48
 * 文件说明
 *
 * @author xuejiaming
 */
public abstract class AbstractMapToStreamIterator<T> extends AbstractStreamIterator<T> {
    protected final boolean hasForEach;
    public AbstractMapToStreamIterator(ExecutorContext context, StreamResultSet streamResult, ResultMetadata<T> resultMetadata) throws SQLException {
        super(context, streamResult, resultMetadata);
        this.hasForEach = context.getExpressionContext().hasForEach();
    }

    @Override
    protected T next0() throws SQLException {
        return mapTo();
    }

    protected abstract T mapTo() throws SQLException;
}
