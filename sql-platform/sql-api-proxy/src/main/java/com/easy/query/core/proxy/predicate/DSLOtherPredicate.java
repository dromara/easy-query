package com.easy.query.core.proxy.predicate;

import com.easy.query.core.func.SQLFunc;
import com.easy.query.core.func.SQLFunction;
import com.easy.query.core.proxy.PropTypeColumn;
import com.easy.query.core.proxy.SQLColumn;
import com.easy.query.core.proxy.TablePropColumn;
import com.easy.query.core.proxy.available.EntitySQLContextAvailable;
import com.easy.query.core.proxy.impl.SQLPredicateImpl;
import com.easy.query.core.proxy.predicate.aggregate.DSLSQLFunctionAvailable;

/**
 * create time 2023/12/2 14:18
 * 文件说明
 *
 * @author xuejiaming
 */
public interface DSLOtherPredicate<TProperty> extends TablePropColumn, EntitySQLContextAvailable {
    default <TProxy,TProp> void ge(SQLColumn<TProxy,TProp> column) {
         ge(true, column);
    }

    default <TProxy,TProp> void ge(boolean condition, SQLColumn<TProxy,TProp> column) {
        if (condition) {
            getEntitySQLContext().accept(new SQLPredicateImpl(f -> f.ge(this.getTable(), this.getValue(), column.getTable(), column.getValue())));
        }
        
    }
    default <TProxy,TProp> void gt(SQLColumn<TProxy,TProp> column) {
         gt(true, column);
    }

    default <TProxy,TProp> void gt(boolean condition, SQLColumn<TProxy,TProp> column) {
        if (condition) {
            getEntitySQLContext().accept( new SQLPredicateImpl(f -> f.gt(this.getTable(), this.getValue(), column.getTable(), column.getValue())));
        }
        
    }


    default <TProxy,TProp> void eq(SQLColumn<TProxy,TProp> column) {
         eq(true, column);
    }

    default <TProxy,TProp> void eq(boolean condition, SQLColumn<TProxy,TProp> column) {
        if (condition) {
            getEntitySQLContext().accept(new SQLPredicateImpl(f -> f.eq(this.getTable(), this.getValue(), column.getTable(), column.getValue())));
        }
    }

    default <TProxy,TProp> void ne(SQLColumn<TProxy,TProp> column) {
         ne(true, column);
    }

    default <TProxy,TProp> void ne(boolean condition, SQLColumn<TProxy,TProp> column) {
        if (condition) {
            getEntitySQLContext().accept(new SQLPredicateImpl(f -> f.ne(this.getTable(), this.getValue(), column.getTable(), column.getValue())));
        }
        
    }

    default <TProxy,TProp> void le(SQLColumn<TProxy,TProp> column) {
         le(true, column);
    }

    default <TProxy,TProp> void le(boolean condition, SQLColumn<TProxy,TProp> column) {
        if (condition) {
           getEntitySQLContext().accept(new SQLPredicateImpl(f -> f.le(this.getTable(), this.getValue(), column.getTable(), column.getValue())));
        }
        
    }

    default <TProxy,TProp> void lt(SQLColumn<TProxy,TProp> column) {
         lt(true, column);
    }

    default <TProxy,TProp> void lt(boolean condition, SQLColumn<TProxy,TProp> column) {
        if (condition) {
            getEntitySQLContext().accept(new SQLPredicateImpl(f -> f.lt(this.getTable(), this.getValue(), column.getTable(), column.getValue())));
        }
        
    }

}
