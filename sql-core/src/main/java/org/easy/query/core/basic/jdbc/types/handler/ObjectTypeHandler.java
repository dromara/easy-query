package org.easy.query.core.basic.jdbc.types.handler;

import org.easy.query.core.basic.jdbc.types.EasyParameter;
import org.easy.query.core.basic.jdbc.types.EasyResultSet;

import java.sql.SQLException;
import java.sql.Types;

/**
 * @FileName: DefaultJdbcTypeHandler.java
 * @Description: 文件说明
 * @Date: 2023/2/17 21:17
 * @Created by xuejiaming
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
