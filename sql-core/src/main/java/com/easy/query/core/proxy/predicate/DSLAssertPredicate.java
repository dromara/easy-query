package com.easy.query.core.proxy.predicate;

import com.easy.query.core.proxy.TablePropColumn;
import com.easy.query.core.proxy.available.EntitySQLContextAvailable;
import com.easy.query.core.proxy.impl.SQLPredicateImpl;

/**
 * create time 2023/12/2 14:10
 * 文件说明
 *
 * @author xuejiaming
 */
public interface DSLAssertPredicate<TProperty> extends TablePropColumn, EntitySQLContextAvailable {
    default void isNull() {
         isNull(true);
    }

    default void isNull(boolean condition){
        if(condition){
            getEntitySQLContext().accept(new SQLPredicateImpl(f -> f.isNull(this.getTable(), this.getValue())));
        }
    }
    default void isNotNull() {
         isNotNull(true);
    }

    default void isNotNull(boolean condition){
        if(condition){
            getEntitySQLContext().accept(new SQLPredicateImpl(f -> f.isNotNull(this.getTable(), this.getValue())));
        }
    }
}
