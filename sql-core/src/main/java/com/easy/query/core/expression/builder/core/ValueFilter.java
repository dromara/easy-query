package com.easy.query.core.expression.builder.core;

import com.easy.query.core.expression.parser.core.available.TableAvailable;

/**
 * create time 2023/8/19 14:29
 * 文件说明
 *
 * @author xuejiaming
 */
@FunctionalInterface
public interface ValueFilter {
    boolean accept(TableAvailable table, String property, Object value);
}
