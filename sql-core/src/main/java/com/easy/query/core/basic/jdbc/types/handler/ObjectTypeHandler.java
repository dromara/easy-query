package com.easy.query.core.basic.jdbc.types.handler;

import com.easy.query.core.basic.jdbc.types.EasyResultSet;
import com.easy.query.core.basic.jdbc.types.EasyParameter;

import java.sql.SQLException;
import java.sql.Types;

/**
 * @FileName: DefaultJdbcTypeHandler.java
 * @Description: 文件说明
 * @Date: 2023/2/17 21:17
 * @author xuejiaming
 */
public class ObjectTypeHandler implements JdbcTypeHandler{
    @Override
    public Object getValue(EasyResultSet resultSet) throws SQLException {
        return resultSet.getRs().getObject(resultSet.getIndex());
    }

    @Override
    public void setParameter(EasyParameter parameter) throws SQLException {
        parameter.setDefaultParameter();
    }

    @Override
    public int getJdbcType() {
        return Types.OTHER;
    }
}
