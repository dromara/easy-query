package com.easy.query.core.exception;

/**
 * create time 2023/12/1 13:23
 * 请使用 {@link EasyQueryFirstNotNullException}
 *
 * @author xuejiaming
 */
@Deprecated
public class EasyQueryFirstOrNotNullException extends EasyQueryFirstNotNullException{
    private static final long serialVersionUID = 4426844319659828849L;

    public EasyQueryFirstOrNotNullException(String msg) {
        super(msg);
    }

    public EasyQueryFirstOrNotNullException(String msg, String code) {
        super(msg, code);
    }

    public EasyQueryFirstOrNotNullException(Throwable e) {
        super(e);
    }

    public EasyQueryFirstOrNotNullException(String msg, String code, Throwable e) {
        super(msg, code, e);
    }
}
