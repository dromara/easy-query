package com.easy.query.core.expression.lambda;

import org.jetbrains.annotations.Nullable;

/**
 * create time 2025/6/18 08:22
 * 文件说明
 *
 * @author xuejiaming
 */
@FunctionalInterface
public interface ValueConvertFunction<T, R> {
    @Nullable
    R convert(@Nullable T t);
}
