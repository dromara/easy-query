package com.easy.query.core.exception;

import java.sql.SQLException;

/**
 * @FileName: EasyQuerySQLException.java
 * @Description: 文件说明
 * @Date: 2023/3/11 23:23
 * @author xuejiaming
 */
public class EasyQuerySQLException extends SQLException {

    private final String sql;

    public String getSQL() {
        return sql;
    }
    public EasyQuerySQLException(String sql,Throwable cause) {
        super(cause);
        this.sql = sql;
    }
}
