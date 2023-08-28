package com.easy.query.core.basic.jdbc.executor.internal.reader;

import com.easy.query.core.basic.jdbc.executor.ResultColumnMetadata;
import com.easy.query.core.basic.jdbc.executor.internal.merge.result.StreamResultSet;
import com.easy.query.core.basic.jdbc.executor.internal.props.JdbcProperty;
import com.easy.query.core.basic.jdbc.types.handler.JdbcTypeHandler;
import com.easy.query.core.util.EasyJdbcExecutorUtil;

import java.sql.SQLException;
import java.util.Objects;

/**
 * create time 2023/8/27 23:15
 * 文件说明
 *
 * @author xuejiaming
 */
public class PropertyDataReader implements DataReader {

    private final ResultColumnMetadata resultColumnMetadata;

    public PropertyDataReader(ResultColumnMetadata resultColumnMetadata) {
        Objects.requireNonNull(resultColumnMetadata, "resultColumnMetadata can not be null.");
        this.resultColumnMetadata = resultColumnMetadata;
    }

    @Override
    public <TBean> void readTo(TBean entity, StreamResultSet streamResultSet) throws SQLException {
        JdbcTypeHandler handler = resultColumnMetadata.getJdbcTypeHandler();
        JdbcProperty dataReader = resultColumnMetadata.getDataReader();
        Object value = EasyJdbcExecutorUtil.fromValue(resultColumnMetadata.getEntityClass(), resultColumnMetadata, handler.getValue(dataReader, streamResultSet));
        resultColumnMetadata.setValue(entity, value);
    }
}
