package com.easy.query.core.api.dynamic.executor.search.exception;

import com.easy.query.core.exception.EasyQueryException;

/**
 * 状态异常
 *
 * @author bkbits
 */
public class EasySearchStatusException extends EasyQueryException {
    public EasySearchStatusException(String msg) {
        super(msg);
    }

    public EasySearchStatusException(Throwable e) {
        super(e);
    }

    public EasySearchStatusException(String msg, Throwable e) {
        super(msg, e);
    }
}
