package com.easy.query.core.basic.jdbc.types.handler;

import com.easy.query.core.basic.jdbc.executor.internal.props.JdbcProperty;
import com.easy.query.core.basic.jdbc.executor.internal.merge.result.StreamResultSet;
import com.easy.query.core.basic.jdbc.types.EasyParameter;

import java.sql.SQLException;

/**
 * @FileName: DoubleTypeHandler.java
 * @Description: 文件说明
 * @Date: 2023/2/17 22:02
 * @author xuejiaming
 */
public class LongTypeHandler implements JdbcTypeHandler {
    private static final long DEFAULT = 0L;

    @Override
    public Object getValue(JdbcProperty dataReader, StreamResultSet streamResultSet) throws SQLException {

        long r = streamResultSet.getLong(dataReader.getJdbcIndex());
        if (r != DEFAULT) {
            return r;
        }
        if (streamResultSet.wasNull()) {//判断当前读取的列是否可以为null，因为基本类型存在默认值而包装类型存在null值
            if (dataReader.isPrimitive()) {
                return DEFAULT;
            } else {
                return null;
            }
        }
        return r;
    }

    @Override
    public void setParameter(EasyParameter parameter) throws SQLException {
        parameter.getPs().setLong(parameter.getIndex(), (Long) parameter.getValue());
    }
}
