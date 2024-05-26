package com.easy.query.core.exception;


import java.sql.SQLException;

/**
 * create time 2023/5/27 22:54
 * 文件说明
 *
 * @author xuejiaming
 */
public class EasyQuerySQLException extends SQLException {
    public EasyQuerySQLException(String reason, String SQLState, int vendorCode) {
        super(reason, SQLState, vendorCode);
    }

    public EasyQuerySQLException(String reason, String SQLState) {
        super(reason, SQLState);
    }

    public EasyQuerySQLException(String reason) {
        super(reason);
    }

    public EasyQuerySQLException() {
    }

    public EasyQuerySQLException(Throwable cause) {
        super(cause);
    }

    public EasyQuerySQLException(String reason, Throwable cause) {
        super(reason, cause);
    }

    public EasyQuerySQLException(String reason, String sqlState, Throwable cause) {
        super(reason, sqlState, cause);
    }

    public EasyQuerySQLException(String reason, String sqlState, int vendorCode, Throwable cause) {
        super(reason, sqlState, vendorCode, cause);
    }
}
