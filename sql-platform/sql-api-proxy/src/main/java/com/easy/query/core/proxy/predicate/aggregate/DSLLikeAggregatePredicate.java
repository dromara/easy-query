package com.easy.query.core.proxy.predicate.aggregate;

import com.easy.query.core.enums.SQLLikeEnum;
import com.easy.query.core.enums.SQLPredicateCompareEnum;
import com.easy.query.core.func.SQLFunc;
import com.easy.query.core.proxy.impl.SQLPredicateImpl;
import com.easy.query.core.proxy.predicate.DSLLikePredicate;
import com.easy.query.core.util.EasySQLUtil;

/**
 * create time 2023/12/14 22:50
 * 文件说明
 *
 * @author xuejiaming
 */
public interface DSLLikeAggregatePredicate<TProperty> extends DSLLikePredicate<TProperty>,DSLSQLFunctionAvailable {

    @Override
    default void likeMatchLeft(boolean condition, TProperty val) {
        if (condition) {
            getEntitySQLContext().accept(new SQLPredicateImpl(f -> {
                SQLFunc fx = f.getRuntimeContext().fx();
                f.funcValueFilter(this.getTable(), func().apply(fx), EasySQLUtil.getLikeParameter(val, SQLLikeEnum.LIKE_PERCENT_RIGHT), SQLPredicateCompareEnum.LIKE);
            }));


//            getEntitySQLContext().accept(new SQLAggregatePredicateImpl(f -> {
//                SQLFunc fx = f.getRuntimeContext().fx();
//                f.funcValueFilter(this.getTable(), func().apply(fx), EasySQLUtil.getLikeParameter(val, SQLLikeEnum.LIKE_PERCENT_RIGHT), SQLPredicateCompareEnum.LIKE);
//            },f->{
//                SQLFunc fx = f.getRuntimeContext().fx();
//                f.func(this.getTable(), func().apply(fx),  SQLPredicateCompareEnum.LIKE,EasySQLUtil.getLikeParameter(val, SQLLikeEnum.LIKE_PERCENT_RIGHT));
//            }));
        }
    }

    @Override
    default void likeMatchRight(boolean condition, TProperty val) {
        if (condition) {
            getEntitySQLContext().accept(new SQLPredicateImpl(f -> {
                SQLFunc fx = f.getRuntimeContext().fx();
                f.funcValueFilter(this.getTable(), func().apply(fx), EasySQLUtil.getLikeParameter(val, SQLLikeEnum.LIKE_PERCENT_LEFT), SQLPredicateCompareEnum.LIKE);
            }));
        }
    }

    @Override
    default void like(boolean condition, TProperty val) {
        if (condition) {
            getEntitySQLContext().accept(new SQLPredicateImpl(f -> {
                SQLFunc fx = f.getRuntimeContext().fx();
                f.funcValueFilter(this.getTable(), func().apply(fx), EasySQLUtil.getLikeParameter(val, SQLLikeEnum.LIKE_PERCENT_ALL), SQLPredicateCompareEnum.LIKE);
            }));
        }
    }

    @Override
    default void notLikeMatchLeft(boolean condition, TProperty val) {
        if (condition) {
            getEntitySQLContext().accept(new SQLPredicateImpl(f -> {
                SQLFunc fx = f.getRuntimeContext().fx();
                f.funcValueFilter(this.getTable(), func().apply(fx), EasySQLUtil.getLikeParameter(val, SQLLikeEnum.LIKE_PERCENT_RIGHT), SQLPredicateCompareEnum.NOT_LIKE);
            }));
        }
    }

    @Override
    default void notLikeMatchRight(boolean condition, TProperty val) {
        if (condition) {
            getEntitySQLContext().accept(new SQLPredicateImpl(f -> {
                SQLFunc fx = f.getRuntimeContext().fx();
                f.funcValueFilter(this.getTable(), func().apply(fx), EasySQLUtil.getLikeParameter(val, SQLLikeEnum.LIKE_PERCENT_LEFT), SQLPredicateCompareEnum.NOT_LIKE);
            }));
        }
    }

    @Override
    default void notLike(boolean condition, TProperty val) {
        if (condition) {
            getEntitySQLContext().accept(new SQLPredicateImpl(f -> {
                SQLFunc fx = f.getRuntimeContext().fx();
                f.funcValueFilter(this.getTable(), func().apply(fx), EasySQLUtil.getLikeParameter(val, SQLLikeEnum.LIKE_PERCENT_ALL), SQLPredicateCompareEnum.NOT_LIKE);
            }));
        }
    }
}
