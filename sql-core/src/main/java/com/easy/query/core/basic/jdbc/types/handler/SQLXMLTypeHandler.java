package com.easy.query.core.basic.jdbc.types.handler;

import com.easy.query.core.basic.jdbc.executor.DataReader;
import com.easy.query.core.basic.jdbc.executor.internal.merge.result.StreamResultSet;
import com.easy.query.core.basic.jdbc.types.EasyParameter;

import java.sql.SQLException;

/**
 * @FileName: SQLXMLTypeHandler.java
 * @Description: 文件说明
 * @Date: 2023/2/17 21:56
 * @author xuejiaming
 */
public class SQLXMLTypeHandler implements JdbcTypeHandler {
    @Override
    public Object getValue(DataReader dataReader, StreamResultSet streamResultSet) throws SQLException {
        return streamResultSet.getSQLXML(dataReader.getJdbcIndex());
    }

    @Override
    public void setParameter(EasyParameter parameter) throws SQLException {
        parameter.setDefaultParameter();
    }
}
