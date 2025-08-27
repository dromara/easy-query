package com.easy.query.core.basic.jdbc.executor.internal.reader;

import com.easy.query.core.basic.jdbc.executor.ResultColumnMetadata;
import com.easy.query.core.basic.jdbc.executor.internal.merge.result.StreamResultSet;
import com.easy.query.core.basic.jdbc.executor.internal.props.JdbcProperty;
import com.easy.query.core.basic.jdbc.types.handler.JdbcTypeHandler;
import com.easy.query.core.metadata.TreeDeepItem;
import com.easy.query.core.util.EasyJdbcExecutorUtil;

import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

/**
 * create time 2023/8/27 23:15
 * 文件说明
 *
 * @author xuejiaming
 */
public class DeepColumnDataReader implements DataReader {

    private final ResultColumnMetadata resultColumnMetadata;

    public DeepColumnDataReader(ResultColumnMetadata resultColumnMetadata) {
        this.resultColumnMetadata = resultColumnMetadata;
    }

    @Override
    public <TBean> void readTo(TBean entity, StreamResultSet streamResultSet) throws SQLException {
        JdbcTypeHandler handler = resultColumnMetadata.getJdbcTypeHandler();
        JdbcProperty jdbcProperty = resultColumnMetadata.getJdbcProperty();
        Object value = EasyJdbcExecutorUtil.fromValue(resultColumnMetadata, handler.getValue(jdbcProperty, streamResultSet));
        resultColumnMetadata.setValue(entity, value);
    }
}
