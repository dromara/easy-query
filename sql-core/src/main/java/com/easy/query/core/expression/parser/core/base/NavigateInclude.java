package com.easy.query.core.expression.parser.core.base;

import com.easy.query.core.basic.api.select.ClientQueryable;

/**
 * create time 2023/6/18 10:43
 * 文件说明
 *
 * @author xuejiaming
 */
public interface NavigateInclude<T1> {
    <TProperty> ClientQueryable<TProperty> with(String property);

}
