package com.easy.query.api4kt.dynamic;

import com.easy.query.api4kt.dynamic.condition.ObjectQueryBuilder4Kt;
import com.easy.query.api4kt.dynamic.condition.impl.ObjectQueryBuilder4KtImpl;
import com.easy.query.core.api.dynamic.condition.ObjectQuery;
import com.easy.query.core.api.dynamic.condition.ObjectQueryBuilder;

/**
 * create time 2023/6/11 09:39
 * 对象快速查询java强类型lambda接口
 *
 * @author xuejiaming
 */
public interface ObjectQuery4Kt<TObject,TEntity>extends ObjectQuery {
    @Override
   default void configure(ObjectQueryBuilder builder){
        configure(new ObjectQueryBuilder4KtImpl<>(builder));
    }

    void configure(ObjectQueryBuilder4Kt<TObject,TEntity> builder);
}
