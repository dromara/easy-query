package com.easy.query.core.api.dynamic.where;

import com.easy.query.core.api.dynamic.EasyDynamicStrategy;

/**
 * @FileName: QueryEntityMapping.java
 * @Description: 文件说明
 * @Date: 2023/3/13 12:13
 * @author xuejiaming
 */
public interface EasyDynamicWhereConfiguration<TObject> extends EasyDynamicStrategy {
    void configure(EasyDynamicWhereBuilder<TObject> builder);
}
