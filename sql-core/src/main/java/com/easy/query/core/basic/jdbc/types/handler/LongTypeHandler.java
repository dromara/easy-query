package com.easy.query.core.basic.jdbc.types.handler;

import com.easy.query.core.basic.jdbc.types.EasyParameter;
import com.easy.query.core.basic.jdbc.types.EasyResultSet;
import com.easy.query.core.basic.jdbc.executor.internal.merge.result.StreamResultSet;

import java.sql.SQLException;
import java.sql.Types;

/**
 * @FileName: DoubleTypeHandler.java
 * @Description: 文件说明
 * @Date: 2023/2/17 22:02
 * @author xuejiaming
 */
public class LongTypeHandler implements JdbcTypeHandler {
    private static final long DEFAULT = 0L;

    @Override
    public Object getValue(EasyResultSet resultSet) throws SQLException {

        StreamResultSet rs = resultSet.getStreamResult();
        long r = rs.getLong(resultSet.getIndex());
        if (r != DEFAULT) {
            return r;
        }
        if (rs.wasNull()) {//判断当前读取的列是否可以为null，因为基本类型存在默认值而包装类型存在null值
            if (resultSet.isPrimitive()) {
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
