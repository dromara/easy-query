package com.easy.query.core.expression.builder.core;

import org.jetbrains.annotations.NotNull;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import org.jetbrains.annotations.Nullable;

/**
 * create time 2023/8/19 14:29
 * 文件说明
 *
 * @author xuejiaming
 */
@FunctionalInterface
public interface ValueFilter {
    boolean accept(@Nullable TableAvailable table, @Nullable String property, Object value);
}
