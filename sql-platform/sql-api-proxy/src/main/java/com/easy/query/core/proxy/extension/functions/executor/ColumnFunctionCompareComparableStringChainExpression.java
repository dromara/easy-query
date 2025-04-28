package com.easy.query.core.proxy.extension.functions.executor;

import com.easy.query.core.enums.SQLLikeEnum;
import com.easy.query.core.expression.parser.core.base.scec.core.SQLNativeChainExpressionContextImpl;
import com.easy.query.core.func.SQLFunc;
import com.easy.query.core.func.SQLFunction;
import com.easy.query.core.proxy.PropTypeColumn;
import com.easy.query.core.proxy.core.Expression;
import com.easy.query.core.proxy.extension.functions.ColumnStringFunctionAvailable;
import com.easy.query.core.proxy.impl.SQLAggregateNativeSQLPredicateImpl;
import com.easy.query.core.proxy.impl.SQLPredicateImpl;
import com.easy.query.core.proxy.predicate.DSLColumnContainsPredicate;
import com.easy.query.core.proxy.predicate.DSLColumnPropTypeContainsPredicate;
import com.easy.query.core.proxy.predicate.DSLStringAssertPredicate;
import com.easy.query.core.proxy.predicate.aggregate.DSLSQLFunctionAvailable;
import com.easy.query.core.util.EasyObjectUtil;

/**
 * create time 2023/12/21 09:19
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ColumnFunctionCompareComparableStringChainExpression<T> extends ColumnFunctionCompareComparableObjectChainExpression<T>,
        ColumnStringFunctionAvailable<T>,
        DSLStringAssertPredicate<T>,
        DSLSQLFunctionAvailable,
        DSLColumnContainsPredicate,
        DSLColumnPropTypeContainsPredicate {

    @Override
    default <TR> ColumnFunctionCompareComparableStringChainExpression<TR> asAnyType(Class<TR> clazz) {
        ColumnFunctionCompareComparableObjectChainExpression.super.asAnyType(clazz);
        return EasyObjectUtil.typeCastNullable(this);
    }

    @Override
    default void isBlank(boolean condition) {
        if (condition) {
            getCurrentEntitySQLContext().accept(new SQLAggregateNativeSQLPredicateImpl(f -> {
                SQLFunc fx = f.getRuntimeContext().fx();
                SQLFunction bank = fx.bank(func().apply(fx));
                f.sqlFunctionExecute(getTable(),bank);
            }));
        }
    }

    @Override
    default void isNotBlank(boolean condition) {
        if (condition) {
            getCurrentEntitySQLContext().accept(new SQLAggregateNativeSQLPredicateImpl(f -> {
                SQLFunc fx = f.getRuntimeContext().fx();
                SQLFunction notBank = fx.notBank(func().apply(fx));
                f.sqlFunctionExecute(getTable(),notBank);
            }));
        }
    }

    @Override
    default void isEmpty(boolean condition) {
        if (condition) {
            getCurrentEntitySQLContext().accept(new SQLAggregateNativeSQLPredicateImpl(f -> {
                SQLFunc fx = f.getRuntimeContext().fx();
                SQLFunction empty = fx.empty(func().apply(fx));
                f.sqlFunctionExecute(getTable(),empty);
            }));
        }
    }

    @Override
    default void isNotEmpty(boolean condition) {
        if (condition) {
            getCurrentEntitySQLContext().accept(new SQLAggregateNativeSQLPredicateImpl(f -> {
                SQLFunc fx = f.getRuntimeContext().fx();
                SQLFunction notEmpty = fx.notEmpty(func().apply(fx));
                f.sqlFunctionExecute(getTable(),notEmpty);
            }));
        }
    }

    @Override
    default void startsWith(boolean condition, String val) {
        if (condition) {
            SQLFunction sqlFunction = Expression.of(getEntitySQLContext()).constant(val).func().apply(getEntitySQLContext().getRuntimeContext().fx());
            getCurrentEntitySQLContext().accept(new SQLPredicateImpl(f -> {
                SQLFunc fx = f.getRuntimeContext().fx();
                SQLFunction likeSQLFunction = fx.like(s->{
                    s.sqlFunc(func().apply(fx));
                    s.sqlFunc(sqlFunction);
                },true, SQLLikeEnum.LIKE_PERCENT_RIGHT);
                f.sqlFunctionExecute(getTable(),likeSQLFunction);
            }));
        }
    }

    @Override
    default void endsWith(boolean condition, String val) {
        if (condition) {
            SQLFunction sqlFunction = Expression.of(getEntitySQLContext()).constant(val).func().apply(getEntitySQLContext().getRuntimeContext().fx());

            getCurrentEntitySQLContext().accept(new SQLPredicateImpl(f -> {
                SQLFunc fx = f.getRuntimeContext().fx();
                SQLFunction likeSQLFunction = fx.like(s->{
                    s.sqlFunc(func().apply(fx));
                    s.sqlFunc(sqlFunction);
                },true, SQLLikeEnum.LIKE_PERCENT_LEFT);
                f.sqlFunctionExecute(getTable(),likeSQLFunction);
            }));
        }
    }

    @Override
    default void contains(boolean condition, String val) {
        if (condition) {
            SQLFunction sqlFunction = Expression.of(getEntitySQLContext()).constant(val).func().apply(getEntitySQLContext().getRuntimeContext().fx());

            getCurrentEntitySQLContext().accept(new SQLPredicateImpl(f -> {
                SQLFunc fx = f.getRuntimeContext().fx();
                SQLFunction likeSQLFunction = fx.like(s->{
                    s.sqlFunc(func().apply(fx));
                    s.sqlFunc(sqlFunction);
                },true, SQLLikeEnum.LIKE_PERCENT_ALL);
                f.sqlFunctionExecute(getTable(),likeSQLFunction);
            }));
        }
    }

    @Override
    default void notStartsWith(boolean condition, String val) {
        if (condition) {
            SQLFunction sqlFunction = Expression.of(getEntitySQLContext()).constant(val).func().apply(getEntitySQLContext().getRuntimeContext().fx());

            getCurrentEntitySQLContext().accept(new SQLPredicateImpl(f -> {
                SQLFunc fx = f.getRuntimeContext().fx();
                SQLFunction likeSQLFunction = fx.like(s->{
                    s.sqlFunc(func().apply(fx));
                    s.sqlFunc(sqlFunction);
                },false, SQLLikeEnum.LIKE_PERCENT_RIGHT);
                f.sqlFunctionExecute(getTable(),likeSQLFunction);
            }));
        }
    }

    @Override
    default void notEndsWith(boolean condition, String val) {
        if (condition) {
            SQLFunction sqlFunction = Expression.of(getEntitySQLContext()).constant(val).func().apply(getEntitySQLContext().getRuntimeContext().fx());

            getCurrentEntitySQLContext().accept(new SQLPredicateImpl(f -> {
                SQLFunc fx = f.getRuntimeContext().fx();
                SQLFunction likeSQLFunction = fx.like(s->{
                    s.sqlFunc(func().apply(fx));
                    s.sqlFunc(sqlFunction);
                },false, SQLLikeEnum.LIKE_PERCENT_LEFT);
                f.sqlFunctionExecute(getTable(),likeSQLFunction);
            }));
        }
    }

    @Override
    default void notContains(boolean condition, String val) {
        if (condition) {
            SQLFunction sqlFunction = Expression.of(getEntitySQLContext()).constant(val).func().apply(getEntitySQLContext().getRuntimeContext().fx());
            getCurrentEntitySQLContext().accept(new SQLPredicateImpl(f -> {
                SQLFunc fx = f.getRuntimeContext().fx();
                SQLFunction likeSQLFunction = fx.like(s->{
                    s.sqlFunc(func().apply(fx));
                    s.sqlFunc(sqlFunction);
                },false, SQLLikeEnum.LIKE_PERCENT_ALL);
                f.sqlFunctionExecute(getTable(),likeSQLFunction);
            }));
        }
    }

    @Override
    default void startsWith(boolean condition, PropTypeColumn<String> val) {
        if (condition) {
            getCurrentEntitySQLContext().accept(new SQLPredicateImpl(f -> {
                SQLFunc fx = f.getRuntimeContext().fx();
                SQLFunction likeSQLFunction = fx.like(s->{
                    s.sqlFunc(func().apply(fx));
                    PropTypeColumn.columnFuncSelector(s,val);
                },true, SQLLikeEnum.LIKE_PERCENT_RIGHT);
                f.sqlFunctionExecute(getTable(),likeSQLFunction);
            }));
        }
    }

    @Override
    default void endsWith(boolean condition, PropTypeColumn<String> val) {
        if (condition) {

            getCurrentEntitySQLContext().accept(new SQLPredicateImpl(f -> {
                SQLFunc fx = f.getRuntimeContext().fx();
                SQLFunction likeSQLFunction = fx.like(s->{
                    s.sqlFunc(func().apply(fx));
                    PropTypeColumn.columnFuncSelector(s,val);
                },true, SQLLikeEnum.LIKE_PERCENT_LEFT);
                f.sqlFunctionExecute(getTable(),likeSQLFunction);
            }));
        }
    }

    @Override
    default void contains(boolean condition, PropTypeColumn<String> val) {
        if (condition) {
            getCurrentEntitySQLContext().accept(new SQLPredicateImpl(f -> {
                SQLFunc fx = f.getRuntimeContext().fx();
                SQLFunction likeSQLFunction = fx.like(s->{
                    s.sqlFunc(func().apply(fx));
                    PropTypeColumn.columnFuncSelector(s,val);
                },true, SQLLikeEnum.LIKE_PERCENT_ALL);
                f.sqlFunctionExecute(getTable(),likeSQLFunction);
            }));
        }
    }

    @Override
    default void notStartsWith(boolean condition, PropTypeColumn<String> val) {
        if (condition) {
            getCurrentEntitySQLContext().accept(new SQLPredicateImpl(f -> {
                SQLFunc fx = f.getRuntimeContext().fx();
                SQLFunction likeSQLFunction = fx.like(s->{
                    s.sqlFunc(func().apply(fx));
                    PropTypeColumn.columnFuncSelector(s,val);
                },false, SQLLikeEnum.LIKE_PERCENT_RIGHT);
                f.sqlFunctionExecute(getTable(),likeSQLFunction);
            }));
        }
    }

    @Override
    default void notEndsWith(boolean condition, PropTypeColumn<String> val) {
        if (condition) {
            getCurrentEntitySQLContext().accept(new SQLPredicateImpl(f -> {
                SQLFunc fx = f.getRuntimeContext().fx();
                SQLFunction likeSQLFunction = fx.like(s->{
                    s.sqlFunc(func().apply(fx));
                    PropTypeColumn.columnFuncSelector(s,val);
                },false, SQLLikeEnum.LIKE_PERCENT_LEFT);
                f.sqlFunctionExecute(getTable(),likeSQLFunction);
            }));
        }
    }

    @Override
    default void notContains(boolean condition, PropTypeColumn<String> val) {
        if (condition) {
            getCurrentEntitySQLContext().accept(new SQLPredicateImpl(f -> {
                SQLFunc fx = f.getRuntimeContext().fx();
                SQLFunction likeSQLFunction = fx.like(s->{
                    s.sqlFunc(func().apply(fx));
                    PropTypeColumn.columnFuncSelector(s,val);
                },false, SQLLikeEnum.LIKE_PERCENT_ALL);
                f.sqlFunctionExecute(getTable(),likeSQLFunction);
            }));
        }
    }
}
