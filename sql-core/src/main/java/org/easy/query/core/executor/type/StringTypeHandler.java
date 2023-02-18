package org.easy.query.core.executor.type;

import org.easy.query.core.executor.EasyParameter;
import org.easy.query.core.executor.EasyResultSet;

import java.sql.SQLException;
import java.sql.Types;

/**
 * @FileName: BigDecimalTypeHandler.java
 * @Description: 文件说明
 * @Date: 2023/2/17 21:21
 * @Created by xuejiaming
 */
public class StringTypeHandler implements JdbcTypeHandler{
    @Override
    public Object getValue(EasyResultSet resultSet) throws SQLException {
        return resultSet.getRs().getString(resultSet.getIndex());
    }

    @Override
    public void setParameter(EasyParameter parameter) throws SQLException {
        parameter.getPs().setString(parameter.getIndex(),(String) parameter.getValue());
    }

    @Override
    public int getJdbcType() {
        return Types.VARCHAR;
    }
}
