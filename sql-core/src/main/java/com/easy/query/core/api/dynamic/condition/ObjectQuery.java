package com.easy.query.core.api.dynamic.condition;

import com.easy.query.core.api.dynamic.DynamicQueryStrategy;

/**
 * @FileName: ObjectQuery.java
 * @Description: 文件说明
 * @Date: 2023/3/13 12:13
 * @author xuejiaming
 */
public interface ObjectQuery<TObject> extends DynamicQueryStrategy {
   void configure(ObjectQueryBuilder<TObject> builder);
}
