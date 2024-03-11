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
public interface DSLLikeOtherPredicate<TProperty> extends TablePropColumn, EntitySQLContextAvailable {
    default <TProxy> void likeMatchLeft(SQLColumn<TProxy,String> column) {
        likeMatchLeft(true, column);
    }

    default <TProxy> void likeMatchLeft(boolean condition, SQLColumn<TProxy,String> column) {
        if (condition) {
            getEntitySQLContext().accept(new SQLPredicateImpl(f -> f.like(this.getTable(), this.getValue(), column.getTable(), column.getValue(),true, SQLLikeEnum.LIKE_PERCENT_RIGHT)));
        }
        
    }
    default <TProxy> void likeMatchRight(SQLColumn<TProxy,String> column) {
        likeMatchRight(true, column);
    }

    default <TProxy> void likeMatchRight(boolean condition, SQLColumn<TProxy,String> column) {
        if (condition) {
            getEntitySQLContext().accept( new SQLPredicateImpl(f -> f.like(this.getTable(), this.getValue(), column.getTable(), column.getValue(),true,SQLLikeEnum.LIKE_PERCENT_LEFT)));
        }
        
    }
    default <TProxy> void like(SQLColumn<TProxy,String> column) {
        like(true, column);
    }

    default <TProxy> void like(boolean condition, SQLColumn<TProxy,String> column) {
        if (condition) {
            getEntitySQLContext().accept(new SQLPredicateImpl(f -> f.like(this.getTable(), this.getValue(), column.getTable(), column.getValue(),true, SQLLikeEnum.LIKE_PERCENT_ALL)));
        }
    }


    default <TProxy> void notLikeMatchLeft(SQLColumn<TProxy,String> column) {
        notLikeMatchLeft(true, column);
    }

    default <TProxy> void notLikeMatchLeft(boolean condition, SQLColumn<TProxy,String> column) {
        if (condition) {
            getEntitySQLContext().accept(new SQLPredicateImpl(f -> f.like(this.getTable(), this.getValue(), column.getTable(), column.getValue(),false, SQLLikeEnum.LIKE_PERCENT_RIGHT)));
        }

    }
    default <TProxy> void notLikeMatchRight(SQLColumn<TProxy,String> column) {
        notLikeMatchRight(true, column);
    }

    default <TProxy> void notLikeMatchRight(boolean condition, SQLColumn<TProxy,String> column) {
        if (condition) {
            getEntitySQLContext().accept( new SQLPredicateImpl(f -> f.like(this.getTable(), this.getValue(), column.getTable(), column.getValue(),false,SQLLikeEnum.LIKE_PERCENT_LEFT)));
        }

    }
    default <TProxy> void notLike(SQLColumn<TProxy,String> column) {
        notLike(true, column);
    }

    default <TProxy> void notLike(boolean condition, SQLColumn<TProxy,String> column) {
        if (condition) {
            getEntitySQLContext().accept(new SQLPredicateImpl(f -> f.like(this.getTable(), this.getValue(), column.getTable(), column.getValue(),false, SQLLikeEnum.LIKE_PERCENT_ALL)));
        }
    }

}
