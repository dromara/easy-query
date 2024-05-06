package com.easy.query.core.basic.jdbc.types.handler;

import com.easy.query.core.basic.jdbc.executor.internal.props.JdbcProperty;
import com.easy.query.core.basic.jdbc.executor.internal.merge.result.StreamResultSet;
import com.easy.query.core.basic.jdbc.types.EasyParameter;

import java.sql.SQLException;

/**
 * create time 2023/2/17 22:02
 * 针对浮点型(float)类型进行处理
 * @author xuejiaming
 */
public class FloatTypeHandler implements JdbcTypeHandler {
    private static final float DEFAULT = 0f;

    /**
     * 获取值
     * @param jdbcProperty jdbc属性描述包含结果的索引是否基本类型等信息
     * @param streamResultSet easy-query提供的流式结果集
     * @return
     * @throws SQLException
     */
    @Override
    public Object getValue(JdbcProperty jdbcProperty, StreamResultSet streamResultSet) throws SQLException {

        float r = streamResultSet.getFloat(jdbcProperty.getJdbcIndex());
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
        parameter.getPs().setFloat(parameter.getIndex(), (Float) parameter.getValue());
    }
}
