package com.easy.query.core.basic.jdbc.executor.internal.stream;

import com.easy.query.core.basic.jdbc.executor.ExecutorContext;
import com.easy.query.core.basic.jdbc.executor.ResultMetadata;
import com.easy.query.core.basic.jdbc.executor.internal.merge.result.StreamResultSet;
import com.easy.query.core.basic.jdbc.types.EasyResultSet;
import com.easy.query.core.exception.EasyQueryException;
import com.easy.query.core.util.EasyStringUtil;

import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.NoSuchElementException;

/**
 * create time 2023/7/31 16:47
 * 文件说明
 *
 * @author xuejiaming
 */
public abstract class AbstractStreamIterator<T> implements StreamIterator<T> {

    protected final ExecutorContext context;
    protected final StreamResultSet streamResultSet;
    protected final ResultMetadata<T> resultMetadata;
    protected EasyResultSet easyResultSet;
    protected boolean isFirst = true;
    protected boolean hasNext;

    public AbstractStreamIterator(ExecutorContext context, StreamResultSet streamResult, ResultMetadata<T> resultMetadata) throws SQLException {
        this.context = context;
        this.streamResultSet = streamResult;
        this.resultMetadata = resultMetadata;
        init();
    }

    @Override
    public boolean hasNext() {
        return hasNext;
    }

    @Override
    public T next() {
        if (!hasNext) {
            throw new NoSuchElementException();
        }
        try {
            T t = next0();
            this.hasNext=easyResultSet.nextAndReset();
            return t;
        } catch (SQLException e) {
            throw new EasyQueryException(e);
        }
    }

    protected void init() throws SQLException {
        if (isFirst) {
            isFirst = false;
            this.hasNext = streamResultSet.next();
            if (hasNext) {
                init0();
                this.easyResultSet=new EasyResultSet(streamResultSet);
            }
        }
    }

    protected abstract void init0() throws SQLException;

    protected abstract T next0() throws SQLException;

    protected String getColName(ResultSetMetaData rsmd, int col) throws SQLException {
        String columnName = rsmd.getColumnLabel(col);
        if (EasyStringUtil.isEmpty(columnName)) {
            columnName = rsmd.getColumnName(col);
        }
        return columnName;
    }
}
