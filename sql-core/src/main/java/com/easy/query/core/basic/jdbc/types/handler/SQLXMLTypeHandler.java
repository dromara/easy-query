package com.easy.query.core.basic.jdbc.types.handler;

import com.easy.query.core.basic.jdbc.types.EasyParameter;
import com.easy.query.core.basic.jdbc.types.EasyResultSet;

import java.sql.SQLException;
import java.sql.Types;

/**
 * @FileName: SQLXMLTypeHandler.java
 * @Description: 文件说明
 * @Date: 2023/2/17 21:56
 * @Created by xuejiaming
 */
public class SQLXMLTypeHandler implements JdbcTypeHandler {
    @Override
    public Object getValue(EasyResultSet resultSet) throws SQLException {
        return resultSet.getRs().getSQLXML(resultSet.getIndex());
    }

    @Override
    public void setParameter(EasyParameter parameter) throws SQLException {
        parameter.setDefaultParameter();
    }

    @Override
    public int getJdbcType() {
        return Types.SQLXML;
    }
}
