package org.easy.query.core.exception;

/**
 * @FileName: EasyQuerySQLException.java
 * @Description: 文件说明
 * @Date: 2023/3/11 23:23
 * @Created by xuejiaming
 */
public class EasyQuerySQLException  extends RuntimeException{
    private final String sql;

    public EasyQuerySQLException(String sql, String msg){
        this(sql,msg,null);
    }
    public EasyQuerySQLException(String sql,Throwable e){
        this(sql,null,e);
    }

    public EasyQuerySQLException(String sql,String msg, Throwable e){
        super(msg,e);
        this.sql = sql;
    }

    public String getSql() {
        return sql;
    }
}
