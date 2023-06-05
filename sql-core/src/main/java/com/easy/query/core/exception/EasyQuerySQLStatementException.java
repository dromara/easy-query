package com.easy.query.core.exception;

import java.sql.SQLException;

/**
 * @author xuejiaming
 * @FileName: EasyQuerySQLException.java
 * @Description: 文件说明
 * @Date: 2023/3/11 23:23
 */
public class EasyQuerySQLStatementException extends SQLException {

    private final String sql;

    public String getSQL() {
        return sql;
    }

    public EasyQuerySQLStatementException(String sql, Throwable cause) {
        super(cause);
        this.sql = sql;
    }
}
