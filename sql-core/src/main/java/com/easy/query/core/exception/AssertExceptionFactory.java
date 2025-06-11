package com.easy.query.core.exception;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
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

    /**
     * 并发执行时候行数不一致
     * @param expectRows 期望受影响行数
     * @param realRows 实际受影响行数
     * @param msg
     * @param code
     * @return
     */
    @NotNull
    RuntimeException createExecuteCurrentException(long expectRows,long realRows, String msg, String code);
}
