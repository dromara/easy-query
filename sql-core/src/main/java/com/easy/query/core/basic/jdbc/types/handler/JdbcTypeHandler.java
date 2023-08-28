package com.easy.query.core.basic.jdbc.types.handler;

import com.easy.query.core.basic.jdbc.executor.internal.props.JdbcProperty;
import com.easy.query.core.basic.jdbc.executor.internal.merge.result.StreamResultSet;
import com.easy.query.core.basic.jdbc.types.EasyParameter;

import java.sql.SQLException;

/**
 * @Description: 文件说明
 * @Date: 2023/2/17 12:46
 * @author xuejiaming
 */
public interface JdbcTypeHandler {
    Object getValue(JdbcProperty dataReader, StreamResultSet streamResultSet) throws SQLException;
    void setParameter(EasyParameter parameter) throws SQLException;
}
