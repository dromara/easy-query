package com.easy.query.core.proxy;


import com.easy.query.core.basic.api.select.Query;
import com.easy.query.core.expression.parser.core.EntitySQLTableOwner;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.proxy.impl.SQLPredicateImpl;

import java.io.Serializable;
import java.util.function.Supplier;

/**
 * create time 2023/6/21 16:41
 * 文件说明
 *
 * @author xuejiaming
 */
public interface TableProxy<TProxy extends TableProxy<TProxy, TEntity>, TEntity> extends BeanProxy, EntitySQLTableOwner<TEntity>, Serializable {

    default boolean isDefault() {
        return getTable() == null;
    }

    Class<TEntity> getEntityClass();

    TProxy create(TableAvailable table);
//    void setTable(TableAvailable table);

//    default TEntity createEntity() {
//        return null;
//    }

    default SQLPredicate exists(Supplier<Query<?>> subQueryFunc) {
        return exists(true, subQueryFunc);
    }

    default SQLPredicate exists(boolean condition, Supplier<Query<?>> subQueryFunc) {
        if (condition) {
            return new SQLPredicateImpl(f -> f.exists(subQueryFunc.get()));
        }
        return SQLPredicate.empty;
    }
    default SQLPredicate notExists(Supplier<Query<?>> subQueryFunc) {
        return notExists(true, subQueryFunc);
    }

    default SQLPredicate notExists(boolean condition, Supplier<Query<?>> subQueryFunc) {
        if (condition) {
            return new SQLPredicateImpl(f -> f.notExists(subQueryFunc.get()));
        }
        return SQLPredicate.empty;
    }
}
