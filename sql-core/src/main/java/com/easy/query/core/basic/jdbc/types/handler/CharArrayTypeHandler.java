package com.easy.query.core.basic.jdbc.types.handler;

import com.easy.query.core.basic.jdbc.executor.internal.props.JdbcProperty;
import com.easy.query.core.basic.jdbc.executor.internal.merge.result.StreamResultSet;
import com.easy.query.core.basic.jdbc.types.EasyParameter;

import java.sql.SQLException;

/**
 * @FileName: CharArrayTypeHandler.java
 * @Description: 文件说明
 * create time 2023/2/17 21:53
 * @author xuejiaming
 */
public class CharArrayTypeHandler implements JdbcTypeHandler {
    @Override
    public Object getValue(JdbcProperty jdbcProperty, StreamResultSet streamResultSet) throws SQLException {
        return streamResultSet.getString(jdbcProperty.getJdbcIndex()).toCharArray();
    }

    @Override
    public void setParameter(EasyParameter parameter) throws SQLException {
        String str = new String((char[]) parameter.getValue());
        parameter.getPs().setString(parameter.getIndex(),str);
    }
}
