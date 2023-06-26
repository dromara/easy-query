package com.easy.query.core.expression.parser.core.base.impl;

import com.easy.query.core.basic.api.select.Query;
import com.easy.query.core.enums.SQLLikeEnum;
import com.easy.query.core.enums.SQLPredicateCompare;
import com.easy.query.core.enums.SQLRangeEnum;
import com.easy.query.core.expression.builder.Filter;
import com.easy.query.core.expression.func.ColumnPropertyFunction;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.parser.core.base.WherePredicate;

import java.util.Collection;

/**
 * @author xuejiaming
 * @Description: 默认的数据库条件比较
 * @Date: 2023/2/7 06:58
 */
public class WherePredicateImpl<T1> implements WherePredicate<T1> {
    private final Filter filter;
    private final TableAvailable table;
    public WherePredicateImpl(TableAvailable table, Filter filter) {
        this.filter = filter;
        this.table = table;

    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    @Override
    public WherePredicate<T1> eq(boolean condition, String property, Object val) {
        if (condition) {
            filter.eq(table, property, val);
        }
        return this;
    }

    @Override
    public WherePredicate<T1> ne(boolean condition, String property, Object val) {
        if (condition) {
            filter.ne(table, property, val);
        }
        return this;
    }

    @Override
    public WherePredicate<T1> gt(boolean condition, String property, Object val) {
        if (condition) {
            filter.gt(table, property, val);
        }
        return this;
    }

    @Override
    public WherePredicate<T1> ge(boolean condition, String property, Object val) {
        if (condition) {
            filter.ge(table, property, val);
        }
        return this;
    }

    @Override
    public WherePredicate<T1> like(boolean condition, String property, Object val, SQLLikeEnum sqlLike) {
        if (condition) {
            filter.like(table, property, val, sqlLike);
        }
        return this;
    }

    @Override
    public WherePredicate<T1> notLike(boolean condition, String property, Object val, SQLLikeEnum sqlLike) {
        if (condition) {
            filter.notLike(table, property, val, sqlLike);
        }
        return this;
    }

    @Override
    public WherePredicate<T1> le(boolean condition, String property, Object val) {
        if (condition) {
            filter.le(table, property, val);
        }
        return this;
    }

    @Override
    public WherePredicate<T1> lt(boolean condition, String property, Object val) {
        if (condition) {
            filter.lt(table, property, val);
        }
        return this;
    }


    @Override
    public WherePredicate<T1> isNull(boolean condition, String property) {
        if (condition) {
            filter.isNull(table, property);
        }
        return this;
    }

    @Override
    public WherePredicate<T1> isNotNull(boolean condition, String property) {
        if (condition) {
            filter.isNotNull(table, property);
        }
        return this;
    }


    @Override
    public WherePredicate<T1> in(boolean condition, String property, Collection<?> collection) {
        if (condition) {
            filter.in(table, property, collection);
        }
        return this;
    }

    @Override
    public <TProperty> WherePredicate<T1> in(boolean condition, String property, TProperty[] collection) {
        if (condition) {
            filter.in(table, property, collection);
        }
        return this;
    }

    @Override
    public <TProperty> WherePredicate<T1> in(boolean condition, String property, Query<TProperty> subQuery) {
        if (condition) {
            filter.in(table, property, subQuery);
        }
        return this;
    }

    @Override
    public WherePredicate<T1> notIn(boolean condition, String property, Collection<?> collection) {
        if (condition) {
            filter.notIn(table, property, collection);
        }
        return this;
    }

    @Override
    public <TProperty> WherePredicate<T1> notIn(boolean condition, String property, TProperty[] collection) {
        if (condition) {
            filter.notIn(table, property, collection);
        }
        return this;
    }

    @Override
    public <TProperty> WherePredicate<T1> notIn(boolean condition, String property, Query<TProperty> subQuery) {
        if (condition) {
            filter.notIn(table, property, subQuery);
        }
        return this;
    }

    @Override
    public <T2> WherePredicate<T1> exists(boolean condition, Query<T2> subQuery) {
        if (condition) {
            filter.exists(table, subQuery);
        }
        return this;
    }

    @Override
    public <T2> WherePredicate<T1> notExists(boolean condition, Query<T2> subQuery) {
        if (condition) {
            filter.notExists(table, subQuery);
        }
        return this;
    }

    @Override
    public WherePredicate<T1> range(boolean condition, String property, boolean conditionLeft, Object valLeft, boolean conditionRight, Object valRight, SQLRangeEnum sqlRange) {
        if (condition) {
            filter.range(table, property, conditionLeft, valLeft, conditionRight, valRight, sqlRange);
        }
        return this;
    }

    @Override
    public WherePredicate<T1> columnFunc(boolean condition, ColumnPropertyFunction columnPropertyFunction, SQLPredicateCompare sqlPredicateCompare, Object val) {
        if (condition) {
            filter.columnFunc(table, columnPropertyFunction, sqlPredicateCompare, val);
        }
        return this;
    }

    @Override
    public <T2> WherePredicate<T1> eq(boolean condition, WherePredicate<T2> sub, String property1, String property2) {
        if (condition) {
            TableAvailable rightTable = sub.getTable();
            filter.eq(table, property1, rightTable, property2);
        }
        return this;
    }

    @Override
    public <T2> WherePredicate<T2> then(WherePredicate<T2> sub) {
        return sub;
    }

    @Override
    public WherePredicate<T1> and(boolean condition) {
        if (condition) {
            filter.and();
        }
        return this;
    }

    @Override
    public WherePredicate<T1> and(boolean condition, SQLExpression1<WherePredicate<T1>> sqlWherePredicateSQLExpression) {
        if (condition) {
            filter.and(f->{
                sqlWherePredicateSQLExpression.apply(new WherePredicateImpl<>(table,f));
            });
        }
        return this;
    }

    @Override
    public WherePredicate<T1> or(boolean condition) {
        if (condition) {
            filter.or();
        }
        return this;
    }

    @Override
    public WherePredicate<T1> or(boolean condition, SQLExpression1<WherePredicate<T1>> sqlWherePredicateSQLExpression) {
        if (condition) {
            filter.or(f->{
                sqlWherePredicateSQLExpression.apply(new WherePredicateImpl<>(table,f));
            });
        }
        return this;
    }


    @Override
    public TableAvailable getTable() {
        return table;
    }
}
