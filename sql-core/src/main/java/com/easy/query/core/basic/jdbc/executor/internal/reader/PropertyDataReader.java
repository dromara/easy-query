package com.easy.query.core.basic.jdbc.executor.internal.reader;

import com.easy.query.core.basic.extension.conversion.ColumnReader;
import com.easy.query.core.basic.extension.conversion.ValueConverter;
import com.easy.query.core.basic.jdbc.executor.ResultColumnMetadata;
import com.easy.query.core.basic.jdbc.executor.internal.merge.result.StreamResultSet;
import com.easy.query.core.basic.jdbc.executor.internal.props.JdbcProperty;
import com.easy.query.core.basic.jdbc.types.handler.JdbcTypeHandler;
import com.easy.query.core.basic.jdbc.types.handler.ObjectTypeHandler;
import com.easy.query.core.util.EasyJdbcExecutorUtil;
import com.easy.query.core.util.EasyObjectUtil;

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
    private final ColumnReader columnReader;

    public PropertyDataReader(ResultColumnMetadata resultColumnMetadata) {
        this(resultColumnMetadata, null);
    }

    public PropertyDataReader(ResultColumnMetadata resultColumnMetadata, ColumnReader columnReader) {
        this.columnReader = columnReader;
        Objects.requireNonNull(resultColumnMetadata, "resultColumnMetadata can not be null.");
        this.resultColumnMetadata = resultColumnMetadata;
    }

    @Override
    public <TBean> void readTo(TBean entity, StreamResultSet streamResultSet) throws SQLException {
        Object value = getValue(streamResultSet);
        resultColumnMetadata.setValue(entity, value);
    }

    private Object getValue(StreamResultSet streamResultSet) throws SQLException {
        if (columnReader != null) {
            return columnReader.read(resultColumnMetadata.getJdbcProperty().getJdbcIndex() - 1, streamResultSet);
        } else {

            JdbcTypeHandler handler = resultColumnMetadata.getJdbcTypeHandler();
            JdbcProperty jdbcProperty = resultColumnMetadata.getJdbcProperty();
            return EasyJdbcExecutorUtil.fromValue(resultColumnMetadata, handler.getValue(jdbcProperty, streamResultSet));
        }
    }
}
