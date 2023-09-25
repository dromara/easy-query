package com.easy.query.core.expression.parser.core.base.impl;

import com.easy.query.core.enums.SQLPredicateCompare;
import com.easy.query.core.enums.SQLPredicateCompareEnum;
import com.easy.query.core.expression.builder.Filter;
import com.easy.query.core.expression.builder.core.SQLNative;
import com.easy.query.core.expression.func.ColumnPropertyFunction;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLExpression2;
import com.easy.query.core.expression.parser.core.EntitySQLTableOwner;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.parser.core.base.WherePredicate;
import com.easy.query.core.util.EasyObjectUtil;

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
    public WherePredicate<T1> castChain() {
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
    public <T2> WherePredicate<T1> compareSelf(boolean condition, EntitySQLTableOwner<T2> sub, String property1, String property2, SQLPredicateCompare sqlPredicateCompare) {
        if (condition) {
            TableAvailable rightTable = sub.getTable();
            filter.compareSelf(table, property1, rightTable, property2, sqlPredicateCompare);
        }
        return castChain();
    }

    @Override
    public <T2> WherePredicate<T1> ne(boolean condition, EntitySQLTableOwner<T2> sub, String property1, String property2) {
        return compareSelf(condition, sub, property1, property2, SQLPredicateCompareEnum.NE);
    }

    @Override
    public <T2> WherePredicate<T1> gt(boolean condition, EntitySQLTableOwner<T2> sub, String property1, String property2) {
        return compareSelf(condition, sub, property1, property2, SQLPredicateCompareEnum.GT);
    }

    @Override
    public <T2> WherePredicate<T1> ge(boolean condition, EntitySQLTableOwner<T2> sub, String property1, String property2) {
        return compareSelf(condition, sub, property1, property2, SQLPredicateCompareEnum.GE);
    }

    @Override
    public <T2> WherePredicate<T1> le(boolean condition, EntitySQLTableOwner<T2> sub, String property1, String property2) {
        return compareSelf(condition, sub, property1, property2, SQLPredicateCompareEnum.LE);
    }

    @Override
    public <T2> WherePredicate<T1> lt(boolean condition, EntitySQLTableOwner<T2> sub, String property1, String property2) {
        return compareSelf(condition, sub, property1, property2, SQLPredicateCompareEnum.LT);
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
            filter.and(f -> {
                sqlWherePredicateSQLExpression.apply(new WherePredicateImpl<>(table, f));
            });
        }
        return this;
    }

    @Override
    public <T2> WherePredicate<T1> and(boolean condition, WherePredicate<T2> t2WherePredicate, SQLExpression2<WherePredicate<T1>, WherePredicate<T2>> sqlWherePredicateSQLExpression) {
        if (condition) {
            filter.and(f -> {
                sqlWherePredicateSQLExpression.apply(new WherePredicateImpl<>(table, f),new WherePredicateImpl<>(t2WherePredicate.getTable(), f));
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
            filter.or(f -> {
                sqlWherePredicateSQLExpression.apply(new WherePredicateImpl<>(table, f));
            });
        }
        return this;
    }

    @Override
    public <T2> WherePredicate<T1> or(boolean condition, WherePredicate<T2> t2WherePredicate, SQLExpression2<WherePredicate<T1>, WherePredicate<T2>> sqlWherePredicateSQLExpression) {
        if (condition) {
            filter.or(f -> {
                sqlWherePredicateSQLExpression.apply(new WherePredicateImpl<>(table, f),new WherePredicateImpl<>(t2WherePredicate.getTable(), f));
            });
        }
        return this;
    }



    @Override
    public <T> SQLNative<T> getSQLNative() {
        return EasyObjectUtil.typeCastNullable(filter);
    }

    @Override
    public TableAvailable getTable() {
        return table;
    }
}
