package com.easy.query.core.api.dynamic.executor.query;

import com.easy.query.core.basic.api.select.ClientQueryable;

/**
 * create time 2025/1/31 22:14
 * 无视实体配置的orderBy和其余信息直接使用当前配置来返回queryable
 *
 * @author xuejiaming
 */
public interface SelectAutoIncludeConfigurable {
    <T> ClientQueryable<T> configure(ClientQueryable<T> queryable, ConfigureArgument configureArgument);
}
