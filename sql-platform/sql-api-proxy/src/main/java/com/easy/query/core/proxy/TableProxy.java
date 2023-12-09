package com.easy.query.core.proxy;


import com.easy.query.core.basic.api.select.Query;
import com.easy.query.core.expression.parser.core.EntitySQLTableOwner;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.proxy.available.EntitySQLContextAvailable;
import com.easy.query.core.proxy.core.EntitySQLContext;
import com.easy.query.core.proxy.core.ProxyEntitySQLContext;
import com.easy.query.core.proxy.impl.SQLPredicateImpl;

import java.io.Serializable;
import java.util.function.Supplier;

/**
 * create time 2023/6/21 16:41
 * 文件说明
 *
 * @author xuejiaming
 */
public interface TableProxy<TProxy extends TableProxy<TProxy, TEntity>, TEntity> extends BeanProxy, EntitySQLTableOwner<TEntity>, EntitySQLContextAvailable, Serializable {

    default boolean isDefault() {
        return getTable() == null;
    }

    Class<TEntity> getEntityClass();

   default TProxy create(TableAvailable table){
       return create(table,new ProxyEntitySQLContext());
   }
    TProxy create(TableAvailable table, EntitySQLContext entitySQLContext);

    default void exists(Supplier<Query<?>> subQueryFunc) {
         exists(true, subQueryFunc);
    }

    default void exists(boolean condition, Supplier<Query<?>> subQueryFunc) {
        if (condition) {
            getEntitySQLContext().accept(new SQLPredicateImpl(f -> f.exists(subQueryFunc.get())));
        }
    }
    default void notExists(Supplier<Query<?>> subQueryFunc) {
         notExists(true, subQueryFunc);
    }

    default void notExists(boolean condition, Supplier<Query<?>> subQueryFunc) {
        if (condition) {
            getEntitySQLContext().accept(new SQLPredicateImpl(f -> f.notExists(subQueryFunc.get())));
        }
    }
}
