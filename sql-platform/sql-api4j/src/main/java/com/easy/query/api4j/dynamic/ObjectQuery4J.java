package com.easy.query.api4j.dynamic;

import com.easy.query.api4j.dynamic.condition.ObjectQueryBuilder4J;
import com.easy.query.api4j.dynamic.condition.impl.ObjectQueryBuilder4JImpl;
import com.easy.query.core.api.dynamic.condition.ObjectQuery;
import com.easy.query.core.api.dynamic.condition.ObjectQueryBuilder;

/**
 * create time 2023/6/11 09:39
 * 对象快速查询java强类型lambda接口
 *
 * @author xuejiaming
 */
public interface ObjectQuery4J<TObject,TEntity>extends ObjectQuery {
    @Override
   default void configure(ObjectQueryBuilder builder){
        configure(new ObjectQueryBuilder4JImpl<>(builder));
    }

    void configure(ObjectQueryBuilder4J<TObject,TEntity> builder);
}
