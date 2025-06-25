package com.easy.query.core.api.dynamic.executor.search.exception;

import com.easy.query.core.exception.EasyQueryException;

/**
 * 内部异常
 *
 * @author bkbits
 */
public class EasySearchInternalException
        extends EasyQueryException {
    public EasySearchInternalException(String msg) {
        super(msg);
    }

    public EasySearchInternalException(Throwable e) {
        super(e);
    }

    public EasySearchInternalException(String msg, Throwable e) {
        super(msg, e);
    }
}
