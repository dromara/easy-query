package org.easy.query.core.executor.type;

import java.sql.SQLException;

/**
 * @FileName: CharArrayTypeHandler.java
 * @Description: 文件说明
 * @Date: 2023/2/17 21:53
 * @Created by xuejiaming
 */
public class CharArrayTypeHandler implements JdbcTypeHandler{
    @Override
    public Object getValue(EasyResultSet resultSet) throws SQLException {
        return resultSet.getRs().getString(resultSet.getIndex()).toCharArray();
    }

    @Override
    public void setParameter(EasyParameter parameter) throws SQLException {
        String str = new String((char[]) parameter.getValue());
        parameter.getPs().setString(parameter.getIndex(),str);
    }
}
