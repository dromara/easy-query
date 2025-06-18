package com.easy.query.core.basic.extension.conversion;


import com.easy.query.core.basic.jdbc.executor.impl.def.EntityResultColumnMetadata;
import com.easy.query.core.basic.jdbc.executor.internal.merge.result.StreamResultSet;
import com.easy.query.core.basic.jdbc.executor.internal.props.JdbcProperty;
import com.easy.query.core.basic.jdbc.types.handler.JdbcTypeHandler;
import com.easy.query.core.expression.lambda.ValueConvertFunction;
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
public class ColumnReaderImpl implements ColumnReader {
    private final EntityMetadata entityMetadata;
    private final ColumnMetadata columnMetadata;
    private final ValueConvertFunction<?, ?> valueConverter;

    public ColumnReaderImpl(EntityMetadata entityMetadata, ColumnMetadata columnMetadata, ValueConvertFunction<?, ?> valueConverter) {
        this.entityMetadata = entityMetadata;
        this.columnMetadata = columnMetadata;
        this.valueConverter = valueConverter;
    }

    @Override
    public Object read(int index,StreamResultSet streamResultSet) throws SQLException {
        EntityResultColumnMetadata resultColumnMetadata = new EntityResultColumnMetadata(index, entityMetadata, columnMetadata);
        JdbcTypeHandler handler = resultColumnMetadata.getJdbcTypeHandler();
        JdbcProperty jdbcProperty = resultColumnMetadata.getJdbcProperty();
        Object value = EasyJdbcExecutorUtil.fromValue(resultColumnMetadata, handler.getValue(jdbcProperty, streamResultSet));

        if (valueConverter != null) {
            value = valueConverter.convert(EasyObjectUtil.typeCastNullable(value));
        }
        return value;
    }
}
