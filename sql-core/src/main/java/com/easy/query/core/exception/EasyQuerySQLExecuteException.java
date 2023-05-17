package com.easy.query.core.exception;

/**
 * @FileName: EasyQuerySQLException.java
 * @Description: 文件说明
 * @Date: 2023/3/11 23:23
 * @author xuejiaming
 */
public class EasyQuerySQLExecuteException extends RuntimeException{
    private final String sql;

    public EasyQuerySQLExecuteException(String sql, String msg){
        this(sql,msg,null);
    }
    public EasyQuerySQLExecuteException(String sql, Throwable e){
        this(sql,null,e);
    }

    public EasyQuerySQLExecuteException(String sql, String msg, Throwable e){
        super(msg,e);
        this.sql = sql;
    }

    public String getSQL() {
        return sql;
    }
}
