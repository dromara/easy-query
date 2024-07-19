package com.easy.query.core.exception;

/**
 * create time 2024/7/19 17:11
 * 文件说明
 *
 * @author xuejiaming
 */
public class EasyQueryResultSizeLimitException extends EasyQueryException{
    private final long limit;

    public EasyQueryResultSizeLimitException(long limit, String msg) {
        super(msg);
        this.limit = limit;
    }

    public EasyQueryResultSizeLimitException(long limit,Throwable e) {
        super(e);
        this.limit = limit;
    }

    public EasyQueryResultSizeLimitException(long limit,String msg, Throwable e) {
        super(msg, e);
        this.limit = limit;
    }

    public long getLimit() {
        return limit;
    }
}