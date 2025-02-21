package com.easy.query.core.proxy.predicate.aggregate;

import com.easy.query.core.enums.SQLLikeEnum;
import com.easy.query.core.enums.SQLPredicateCompare;
import com.easy.query.core.enums.SQLPredicateCompareEnum;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.func.SQLFunc;
import com.easy.query.core.func.SQLFunction;
import com.easy.query.core.proxy.core.EntitySQLContext;
import com.easy.query.core.proxy.impl.SQLAggregatePredicateImpl;
import com.easy.query.core.proxy.predicate.DSLLikePredicate;
import com.easy.query.core.util.EasySQLUtil;

import java.util.function.Function;

/**
 * create time 2023/12/14 22:50
 * 文件说明
 *
 * @author xuejiaming
 */
public interface DSLLikeAggregatePredicate<TProperty> extends DSLLikePredicate<TProperty>,DSLSQLFunctionAvailable {

    /**
     * column like 'value%'
     * @param condition
     * @param val
     */
    @Override
    default void likeMatchLeft(boolean condition, TProperty val) {
        if (condition) {
            _like(getEntitySQLContext(),this.getTable(),func(),_toFunctionSerializeValue(val),SQLLikeEnum.LIKE_PERCENT_RIGHT,SQLPredicateCompareEnum.LIKE);
        }
    }

    /**
     * column like '%value'
     * @param condition
     * @param val
     */
    @Override
    default void likeMatchRight(boolean condition, TProperty val) {
        if (condition) {
            _like(getEntitySQLContext(),this.getTable(),func(),_toFunctionSerializeValue(val),SQLLikeEnum.LIKE_PERCENT_LEFT,SQLPredicateCompareEnum.LIKE);
        }
    }

    @Override
    default void like(boolean condition, TProperty val) {
        if (condition) {
            _like(getEntitySQLContext(),this.getTable(),func(),_toFunctionSerializeValue(val),SQLLikeEnum.LIKE_PERCENT_ALL,SQLPredicateCompareEnum.LIKE);
        }
    }

    @Override
    default void notLikeMatchLeft(boolean condition, TProperty val) {
        if (condition) {
            _like(getEntitySQLContext(),this.getTable(),func(),_toFunctionSerializeValue(val),SQLLikeEnum.LIKE_PERCENT_RIGHT,SQLPredicateCompareEnum.NOT_LIKE);
        }
    }

    @Override
    default void notLikeMatchRight(boolean condition, TProperty val) {
        if (condition) {
            _like(getEntitySQLContext(),this.getTable(),func(),_toFunctionSerializeValue(val),SQLLikeEnum.LIKE_PERCENT_LEFT,SQLPredicateCompareEnum.NOT_LIKE);
        }
    }

    @Override
    default void notLike(boolean condition, TProperty val) {
        if (condition) {
            _like(getEntitySQLContext(),this.getTable(),func(),_toFunctionSerializeValue(val),SQLLikeEnum.LIKE_PERCENT_ALL,SQLPredicateCompareEnum.NOT_LIKE);
        }
    }

    //--------

    @Override
    default void likeRawMatchLeft(boolean condition, Object val) {
        if (condition) {
            _likeRaw(getEntitySQLContext(),this.getTable(),func(),val,SQLLikeEnum.LIKE_PERCENT_RIGHT,SQLPredicateCompareEnum.LIKE);
        }
    }

    @Override
    default void likeRawMatchRight(boolean condition, Object val) {
        if (condition) {
            _likeRaw(getEntitySQLContext(),this.getTable(),func(),val,SQLLikeEnum.LIKE_PERCENT_LEFT,SQLPredicateCompareEnum.LIKE);
        }
    }

    @Override
    default void likeRaw(boolean condition, Object val) {
        if (condition) {
            _likeRaw(getEntitySQLContext(),this.getTable(),func(),val,SQLLikeEnum.LIKE_PERCENT_ALL,SQLPredicateCompareEnum.LIKE);
        }
    }

    @Override
    default void notLikeRawMatchLeft(boolean condition, Object val) {
        if (condition) {
            _likeRaw(getEntitySQLContext(),this.getTable(),func(),val,SQLLikeEnum.LIKE_PERCENT_RIGHT,SQLPredicateCompareEnum.NOT_LIKE);
        }
    }

    @Override
    default void notLikeRawMatchRight(boolean condition, Object val) {
        if (condition) {
            _likeRaw(getEntitySQLContext(),this.getTable(),func(),val,SQLLikeEnum.LIKE_PERCENT_LEFT,SQLPredicateCompareEnum.NOT_LIKE);
        }
    }

    @Override
    default void notLikeRaw(boolean condition, Object val) {
        if (condition) {
            _likeRaw(getEntitySQLContext(),this.getTable(),func(),val,SQLLikeEnum.LIKE_PERCENT_ALL,SQLPredicateCompareEnum.NOT_LIKE);
        }
    }

    static void _likeRaw(EntitySQLContext entitySQLContext, TableAvailable table, Function<SQLFunc, SQLFunction> func, Object val, SQLLikeEnum sqlLike, SQLPredicateCompare likeOrNot){

        entitySQLContext.accept(new SQLAggregatePredicateImpl(f -> {
            SQLFunc fx = f.getRuntimeContext().fx();
            f.funcValueFilter(table, func.apply(fx), EasySQLUtil.getLikeRawParameter(val, sqlLike), likeOrNot);
        },f->{
            SQLFunc fx = f.getRuntimeContext().fx();
            f.func(table, func.apply(fx),  likeOrNot,EasySQLUtil.getLikeRawParameter(val, sqlLike));
        }));
    }

    static void _like(EntitySQLContext entitySQLContext,  TableAvailable table, Function<SQLFunc, SQLFunction> func, Object serializeValue, SQLLikeEnum sqlLike, SQLPredicateCompare likeOrNot){

        entitySQLContext.accept(new SQLAggregatePredicateImpl(f -> {
            SQLFunc fx = f.getRuntimeContext().fx();
            f.funcValueFilter(table, func.apply(fx), EasySQLUtil.getLikeParameter(serializeValue, sqlLike), likeOrNot);
        },f->{
            SQLFunc fx = f.getRuntimeContext().fx();
            f.func(table, func.apply(fx),  likeOrNot,EasySQLUtil.getLikeParameter(serializeValue, sqlLike));
        }));
    }
}
