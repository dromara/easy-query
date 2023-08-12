package com.easy.query.core.basic.jdbc.types.handler;

import com.easy.query.core.basic.jdbc.executor.DataReader;
import com.easy.query.core.basic.jdbc.executor.internal.merge.result.StreamResultSet;
import com.easy.query.core.basic.jdbc.types.EasyParameter;

import java.sql.SQLException;
import java.sql.Time;

/**
 * @FileName: TimeTypeHandler.java
 * @Description: 文件说明
 * @Date: 2023/2/18 08:36
 * @author xuejiaming
 */
public class TimeTypeHandler implements JdbcTypeHandler {
    @Override
    public Object getValue(DataReader dataReader, StreamResultSet streamResultSet) throws SQLException {
        return streamResultSet.getTime(dataReader.getJdbcIndex());
    }

    @Override
    public void setParameter(EasyParameter parameter) throws SQLException {
        parameter.getPs().setTime(parameter.getIndex(),(Time)parameter.getValue());
    }
}
