package com.easy.query.core.basic.jdbc.types.handler;

import com.easy.query.core.basic.jdbc.executor.DataReader;
import com.easy.query.core.basic.jdbc.executor.internal.merge.result.StreamResultSet;
import com.easy.query.core.basic.jdbc.types.EasyParameter;

import java.sql.SQLException;

/**
 * @FileName: DoubleTypeHandler.java
 * @Description: 文件说明
 * @Date: 2023/2/17 22:02
 * @author xuejiaming
 */
public class IntegerTypeHandler implements JdbcTypeHandler {
    private static final int DEFAULT = 0;

    @Override
    public Object getValue(DataReader dataReader, StreamResultSet streamResultSet) throws SQLException {

        int r = streamResultSet.getInt(dataReader.getJdbcIndex());
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
        parameter.getPs().setInt(parameter.getIndex(), (Integer) parameter.getValue());
    }
}
