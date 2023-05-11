package com.easy.query.core.exception;

/**
 * create time 2023/5/11 13:40
 * 文件说明
 *
 * @author xuejiaming
 */
public class EasyQueryRouteNotMatchException extends EasyQueryException{
    public EasyQueryRouteNotMatchException(String msg) {
        super(msg);
    }

    public EasyQueryRouteNotMatchException(Throwable e) {
        super(e);
    }

    public EasyQueryRouteNotMatchException(String msg, Throwable e) {
        super(msg, e);
    }
}
