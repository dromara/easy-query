package com.easy.query.core.basic.jdbc.types.handler;

import com.easy.query.core.basic.jdbc.types.EasyParameter;
import com.easy.query.core.basic.jdbc.types.EasyResultSet;

import java.sql.Clob;
import java.sql.SQLException;
import java.sql.Types;

/**
 * @FileName: CLobTypeHandler.java
 * @Description: 文件说明
 * @Date: 2023/2/17 21:54
 * @Created by xuejiaming
 */
public class CLobTypeHandler implements JdbcTypeHandler{
    @Override
    public Object getValue(EasyResultSet resultSet) throws SQLException {
        return resultSet.getRs().getClob(resultSet.getIndex());
    }

    @Override
    public void setParameter(EasyParameter parameter) throws SQLException {
        parameter.getPs().setClob(parameter.getIndex(),(Clob) parameter.getValue());
    }

    @Override
    public int getJdbcType() {
        return Types.CLOB;
    }

}
