package com.easy.query.core.basic.jdbc.executor.internal.reader;

import com.easy.query.core.basic.jdbc.executor.ResultColumnMetadata;
import com.easy.query.core.basic.jdbc.executor.internal.enumerable.PartitionResult;
import com.easy.query.core.basic.jdbc.executor.internal.merge.result.StreamResultSet;
import com.easy.query.core.basic.jdbc.executor.internal.props.JdbcProperty;
import com.easy.query.core.basic.jdbc.types.handler.JdbcTypeHandler;
import com.easy.query.core.util.EasyJdbcExecutorUtil;
import com.easy.query.core.util.EasyStringUtil;

import java.sql.SQLException;
import java.util.Objects;

/**
 * create time 2023/8/27 23:15
 * 文件说明
 *
 * @author xuejiaming
 */
public class PartitionByPropertyDataReader implements DataReader {

    private final ResultColumnMetadata resultColumnMetadata;

    public PartitionByPropertyDataReader(ResultColumnMetadata resultColumnMetadata) {
        Objects.requireNonNull(resultColumnMetadata, "resultColumnMetadata can not be null.");
        this.resultColumnMetadata = resultColumnMetadata;
    }

    @Override
    public <TBean> void readTo(TBean entity, StreamResultSet streamResultSet) throws SQLException {
        JdbcTypeHandler handler = resultColumnMetadata.getJdbcTypeHandler();
        JdbcProperty jdbcProperty = resultColumnMetadata.getJdbcProperty();
        Object value = EasyJdbcExecutorUtil.fromValue(resultColumnMetadata, handler.getValue(jdbcProperty, streamResultSet));
        if(EasyStringUtil.startsWith(resultColumnMetadata.getPropertyName(), PartitionResult.PARTITION_PREFIX)){
            resultColumnMetadata.setValue(entity, value);
        }else{
            PartitionResult<?> partitionResult = (PartitionResult<?>) entity;
            resultColumnMetadata.setValue(partitionResult.getEntity(), value);
        }

    }
}
