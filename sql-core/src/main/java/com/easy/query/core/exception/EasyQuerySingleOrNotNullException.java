package com.easy.query.core.exception;

/**
 * create time 2023/12/1 13:24
 * 请使用 {@link EasyQuerySingleNotNullException}
 *
 * @author xuejiaming
 */
@Deprecated
public class EasyQuerySingleOrNotNullException extends EasyQuerySingleNotNullException{
    private static final long serialVersionUID = 4426844319659828849L;
    public EasyQuerySingleOrNotNullException(String msg) {
        super(msg);
    }

    public EasyQuerySingleOrNotNullException(String msg, String code) {
        super(msg, code);
    }

    public EasyQuerySingleOrNotNullException(Throwable e) {
        super(e);
    }

    public EasyQuerySingleOrNotNullException(String msg, String code, Throwable e) {
        super(msg, code, e);
    }
}
