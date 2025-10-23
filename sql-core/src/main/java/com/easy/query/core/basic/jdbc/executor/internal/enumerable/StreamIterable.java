package com.easy.query.core.basic.jdbc.executor.internal.enumerable;

import com.easy.query.core.basic.api.select.ResultSetContext;

/**
 * create time 2023/7/31 16:06
 * 文件说明
 *
 * @author xuejiaming
 */
public interface StreamIterable<T> extends Iterable<T>{
    ResultSetContext getResultSetContext();
}
