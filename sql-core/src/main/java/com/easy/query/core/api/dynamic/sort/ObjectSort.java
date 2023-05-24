package com.easy.query.core.api.dynamic.sort;

import com.easy.query.core.api.dynamic.DynamicQueryStrategy;

/**
 * create time 2023/3/23 17:23
 * 动态排序配置
 * @author xuejiaming
 */
public interface ObjectSort extends DynamicQueryStrategy {
    void configure(ObjectSortBuilder builder);
}
