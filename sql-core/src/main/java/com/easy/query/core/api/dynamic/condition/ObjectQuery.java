package com.easy.query.core.api.dynamic.condition;

import com.easy.query.core.api.dynamic.DynamicQueryStrategy;

/**
 * create time 2023/3/13 12:13
 * 不添加也可以使用whereObject,改接口用来配置属性映射关系
 *
 * @author xuejiaming
 */
public interface ObjectQuery<TObject> extends DynamicQueryStrategy {
   void configure(ObjectQueryBuilder<TObject> builder);
}
