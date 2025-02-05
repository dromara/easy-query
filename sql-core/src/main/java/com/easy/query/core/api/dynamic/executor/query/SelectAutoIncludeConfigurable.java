package com.easy.query.core.api.dynamic.executor.query;

import com.easy.query.core.basic.api.select.ClientQueryable;

/**
 * create time 2025/1/31 22:14
 * 无视实体配置的orderBy和其余信息直接使用当前配置来返回queryable
 *
 * @author xuejiaming
 */
public interface SelectAutoIncludeConfigurable {
    /**
     * 标识该行为是否继承自实体的行为譬如order by limit等
     * @return true 表示继承行为，false 表示独立行为
     */
    boolean isInheritedBehavior();

    <T> ClientQueryable<T> configure(ClientQueryable<T> queryable, ConfigureArgument configureArgument);
}
