package com.easy.query.search.exception;

import com.easy.query.core.exception.EasyQueryException;

/**
 * 排序异常
 *
 * @author bkbits
 */
public class EasySearchSortException extends EasyQueryException {
    public EasySearchSortException(String msg) {
        super(msg);
    }

    public EasySearchSortException(Throwable e) {
        super(e);
    }

    public EasySearchSortException(String msg, Throwable e) {
        super(msg, e);
    }
}
