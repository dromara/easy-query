package com.easy.query.core.exception;

import com.easy.query.core.annotation.NotNull;

/**
 * create time 2023/12/1 13:13
 * 文件说明
 *
 * @author xuejiaming
 */
public interface AssertExceptionFactory {
    @NotNull
    RuntimeException createFirstNotNullException(String msg,String code);
    @NotNull
    RuntimeException createSingleNotNullException(String msg, String code);
    @NotNull RuntimeException createSingleMoreElementException(Class<?> queryClass);
}
