package com.easy.query.core.basic.jdbc.types.handler;

import com.easy.query.core.basic.jdbc.executor.internal.props.JdbcProperty;
import com.easy.query.core.basic.jdbc.executor.internal.merge.result.StreamResultSet;
import com.easy.query.core.basic.jdbc.types.EasyParameter;

import java.sql.SQLException;

/**
 * @author xuejiaming
 * @FileName: DoubleTypeHandler.java
 * @Description: 文件说明
 * create time 2023/2/17 22:02
 */
public class DoubleTypeHandler implements JdbcTypeHandler {
    private static final double DEFAULT = 0d;

    @Override
    public Object getValue(JdbcProperty jdbcProperty, StreamResultSet streamResultSet) throws SQLException {

        double r = streamResultSet.getDouble(jdbcProperty.getJdbcIndex());
        if (r != DEFAULT) {
            return r;
        }
        if (streamResultSet.wasNull()) {//判断当前读取的列是否可以为null，因为基本类型存在默认值而包装类型存在null值
            if (jdbcProperty.isPrimitive()) {
                return DEFAULT;
            } else {
                return null;
            }
        }
        return r;
    }

    @Override
    public void setParameter(EasyParameter parameter) throws SQLException {
        parameter.getPs().setDouble(parameter.getIndex(), (Double) parameter.getValue());
    }
}
