package com.easy.query.core.exception;

import com.easy.query.core.annotation.NotNull;
import com.easy.query.core.basic.api.select.Query;

/**
 * create time 2023/12/1 13:13
 * 文件说明
 *
 * @author xuejiaming
 */
public interface AssertExceptionFactory {
    @NotNull
    <T> RuntimeException createFindNotNullException(Query<T> query, String msg, String code);
    @NotNull
    <T> RuntimeException createRequiredException(Query<T> query, String msg, String code);
    @NotNull
    <T> RuntimeException createFirstNotNullException(Query<T> query, String msg, String code);
    @NotNull
    <T> RuntimeException createSingleNotNullException(Query<T> query,String msg, String code);
    @NotNull
    <T> RuntimeException createSingleMoreElementException(Query<T> query);
}
