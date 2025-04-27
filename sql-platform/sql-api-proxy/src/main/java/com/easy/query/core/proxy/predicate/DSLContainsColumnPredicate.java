package com.easy.query.core.proxy.predicate;

import com.easy.query.core.enums.SQLLikeEnum;
import com.easy.query.core.proxy.SQLColumn;
import com.easy.query.core.proxy.TablePropColumn;
import com.easy.query.core.proxy.available.EntitySQLContextAvailable;
import com.easy.query.core.proxy.impl.SQLPredicateImpl;

/**
 * create time 2023/12/2 14:18
 * 文件说明
 *
 * @author xuejiaming
 */
public interface DSLContainsColumnPredicate extends TablePropColumn, EntitySQLContextAvailable {
    /**
     * column like 'value%'
     * @param column
     * @param <TProxy>
     */
    default <TProxy> void startsWith(SQLColumn<TProxy,String> column) {
        startsWith(true, column);
    }

    /**
     * column like 'value%'
     * @param condition
     * @param column
     * @param <TProxy>
     */
    default <TProxy> void startsWith(boolean condition, SQLColumn<TProxy,String> column) {
        if (condition) {
            getCurrentEntitySQLContext().accept(new SQLPredicateImpl(f -> f.like(this.getTable(), this.getValue(), column.getTable(), column.getValue(),true, SQLLikeEnum.LIKE_PERCENT_RIGHT)));
        }
        
    }

    /**
     * column like '%value'
     * @param column
     * @param <TProxy>
     */
    default <TProxy> void endsWith(SQLColumn<TProxy,String> column) {
        endsWith(true, column);
    }

    /**
     * column like '%value'
     * @param condition
     * @param column
     * @param <TProxy>
     */
    default <TProxy> void endsWith(boolean condition, SQLColumn<TProxy,String> column) {
        if (condition) {
            getCurrentEntitySQLContext().accept( new SQLPredicateImpl(f -> f.like(this.getTable(), this.getValue(), column.getTable(), column.getValue(),true,SQLLikeEnum.LIKE_PERCENT_LEFT)));
        }
        
    }
    default <TProxy> void contains(SQLColumn<TProxy,String> column) {
        contains(true, column);
    }

    default <TProxy> void contains(boolean condition, SQLColumn<TProxy,String> column) {
        if (condition) {
            getCurrentEntitySQLContext().accept(new SQLPredicateImpl(f -> f.like(this.getTable(), this.getValue(), column.getTable(), column.getValue(),true, SQLLikeEnum.LIKE_PERCENT_ALL)));
        }
    }


    default <TProxy> void notStartsWith(SQLColumn<TProxy,String> column) {
        notStartsWith(true, column);
    }

    default <TProxy> void notStartsWith(boolean condition, SQLColumn<TProxy,String> column) {
        if (condition) {
            getCurrentEntitySQLContext().accept(new SQLPredicateImpl(f -> f.like(this.getTable(), this.getValue(), column.getTable(), column.getValue(),false, SQLLikeEnum.LIKE_PERCENT_RIGHT)));
        }

    }
    default <TProxy> void notEndsWith(SQLColumn<TProxy,String> column) {
        notEndsWith(true, column);
    }

    default <TProxy> void notEndsWith(boolean condition, SQLColumn<TProxy,String> column) {
        if (condition) {
            getCurrentEntitySQLContext().accept( new SQLPredicateImpl(f -> f.like(this.getTable(), this.getValue(), column.getTable(), column.getValue(),false,SQLLikeEnum.LIKE_PERCENT_LEFT)));
        }

    }
    default <TProxy> void notContains(SQLColumn<TProxy,String> column) {
        notContains(true, column);
    }

    default <TProxy> void notContains(boolean condition, SQLColumn<TProxy,String> column) {
        if (condition) {
            getCurrentEntitySQLContext().accept(new SQLPredicateImpl(f -> f.like(this.getTable(), this.getValue(), column.getTable(), column.getValue(),false, SQLLikeEnum.LIKE_PERCENT_ALL)));
        }
    }

}
