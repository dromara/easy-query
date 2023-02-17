package org.easy.query.core.executor.type;

import java.sql.Clob;
import java.sql.SQLException;

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

}
