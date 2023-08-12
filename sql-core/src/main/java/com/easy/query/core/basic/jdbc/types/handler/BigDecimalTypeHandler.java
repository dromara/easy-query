package com.easy.query.core.basic.jdbc.types.handler;

import com.easy.query.core.basic.jdbc.executor.DataReader;
import com.easy.query.core.basic.jdbc.executor.internal.merge.result.StreamResultSet;
import com.easy.query.core.basic.jdbc.types.EasyParameter;

import java.math.BigDecimal;
import java.sql.SQLException;

/**
 * @FileName: BigDecimalTypeHandler.java
 * @Description: 文件说明
 * @Date: 2023/2/17 21:21
 * @author xuejiaming
 */
public class BigDecimalTypeHandler implements JdbcTypeHandler {

    @Override
    public Object getValue(DataReader dataReader, StreamResultSet streamResultSet) throws SQLException {
        return streamResultSet.getBigDecimal(dataReader.getJdbcIndex());
    }

    @Override
    public void setParameter(EasyParameter parameter) throws SQLException {
        parameter.getPs().setBigDecimal(parameter.getIndex(),(BigDecimal)parameter.getValue());
    }
}
