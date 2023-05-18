package com.easy.query.core.api.dynamic.sort;

import com.easy.query.core.api.dynamic.DynamicQueryStrategy;

/**
 * @FileName: ObjectSort.java
 * @Description: 文件说明
 * @Date: 2023/3/23 17:23
 * @author xuejiaming
 */
public interface ObjectSort extends DynamicQueryStrategy {
    void configure(ObjectSortBuilder builder);
}
