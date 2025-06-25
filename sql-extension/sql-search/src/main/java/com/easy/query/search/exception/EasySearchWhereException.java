package com.easy.query.search.exception;

import com.easy.query.core.exception.EasyQueryException;

/**
 * 搜索异常
 *
 * @author bkbits
 */
public class EasySearchWhereException
        extends EasyQueryException {
    public EasySearchWhereException(String msg) {
        super(msg);
    }

    public EasySearchWhereException(Throwable e) {
        super(e);
    }

    public EasySearchWhereException(String msg, Throwable e) {
        super(msg, e);
    }
}
