package com.easy.query.core.exception;

import com.easy.query.core.annotation.NotNull;
import com.easy.query.core.annotation.Nullable;
import com.easy.query.core.basic.api.select.Query;

/**
 * create time 2023/12/1 13:13
 * 文件说明
 *
 * @author xuejiaming
 */
public interface AssertExceptionFactory {
    @NotNull
    <T> RuntimeException createFindNotNullException(@Nullable Query<T> query, String msg, String code);
    @NotNull
    <T> RuntimeException createRequiredException(@Nullable Query<T> query, String msg, String code);
    @NotNull
    <T> RuntimeException createFirstNotNullException(@Nullable Query<T> query, String msg, String code);
    @NotNull
    <T> RuntimeException createSingleNotNullException(@Nullable Query<T> query, String msg, String code);
    @NotNull
    <T> RuntimeException createSingleMoreElementException(@Nullable Query<T> query);
}
