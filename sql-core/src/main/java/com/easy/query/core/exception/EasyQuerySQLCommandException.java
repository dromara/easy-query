package com.easy.query.core.exception;

/**
 * create time 2023/4/28 09:54
 * 文件说明
 *
 * @author xuejiaming
 */
public class EasyQuerySQLCommandException extends EasyQueryException{
    public EasyQuerySQLCommandException(String msg) {
        super(msg);
    }

    public EasyQuerySQLCommandException(Throwable e) {
        super(e);
    }

    public EasyQuerySQLCommandException(String msg, Throwable e) {
        super(msg, e);
    }
}
