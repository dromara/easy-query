package com.easy.query.core.api.dynamic.order;

import com.easy.query.core.api.dynamic.EasyDynamicStrategy;

/**
 * @FileName: EasyDynamicOrderByConfiguration.java
 * @Description: 文件说明
 * @Date: 2023/3/23 17:23
 * @author xuejiaming
 */
public interface EasyOrderBy extends EasyDynamicStrategy {
    void configure(EasyOrderByBuilder builder);
}
