package com.easy.query.core.expression.builder.core;

import com.easy.query.core.annotation.NotNull;
import com.easy.query.core.expression.parser.core.available.TableAvailable;

/**
 * create time 2023/8/19 14:29
 * 文件说明
 *
 * @author xuejiaming
 */
@FunctionalInterface
public interface ValueFilter {
    boolean accept(@NotNull TableAvailable table, @NotNull String property, Object value);
}
