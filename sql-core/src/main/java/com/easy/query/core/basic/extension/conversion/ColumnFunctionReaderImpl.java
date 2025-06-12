package com.easy.query.core.basic.extension.conversion;


import com.easy.query.core.basic.jdbc.executor.impl.def.EntityResultColumnMetadata;
import com.easy.query.core.basic.jdbc.executor.internal.merge.result.StreamResultSet;
import com.easy.query.core.basic.jdbc.executor.internal.props.BasicJdbcProperty;
import com.easy.query.core.basic.jdbc.executor.internal.props.ColumnJdbcProperty;
import com.easy.query.core.basic.jdbc.executor.internal.props.JdbcProperty;
import com.easy.query.core.basic.jdbc.types.handler.JdbcTypeHandler;
import com.easy.query.core.metadata.ColumnMetadata;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.util.EasyJdbcExecutorUtil;
import com.easy.query.core.util.EasyObjectUtil;

import java.sql.SQLException;
import java.util.function.Function;

/**
 * create time 2025/6/12 22:49
 * 文件说明
 *
 * @author xuejiaming
 */
public class ColumnFunctionReaderImpl implements ColumnReader {
    private final Class<?> propType;
    private final JdbcTypeHandler jdbcTypeHandler;
    private final Function<?, ?> valueConverter;

    public ColumnFunctionReaderImpl(Class<?> propType, JdbcTypeHandler jdbcTypeHandler, Function<?,?> valueConverter) {
        this.propType = propType;
        this.jdbcTypeHandler = jdbcTypeHandler;
        this.valueConverter = valueConverter;
    }

    @Override
    public Object read(int index,StreamResultSet streamResultSet) throws SQLException {
        JdbcProperty jdbcProperty = new BasicJdbcProperty(index, propType);
        Object value = jdbcTypeHandler.getValue(jdbcProperty, streamResultSet);
        if (valueConverter != null) {
            value = valueConverter.apply(EasyObjectUtil.typeCastNotNull(value));
        }
        return value;
    }
}
