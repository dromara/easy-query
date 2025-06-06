package com.easy.query.core.expression.parser.core.base.impl;

import com.easy.query.core.enums.SQLPredicateCompare;
import com.easy.query.core.enums.SQLPredicateCompareEnum;
import com.easy.query.core.enums.SQLRangeEnum;
import com.easy.query.core.exception.EasyQueryInvalidOperationException;
import com.easy.query.core.expression.builder.Filter;
import com.easy.query.core.expression.builder.core.SQLNative;
import com.easy.query.core.expression.lambda.SQLActionExpression;
import com.easy.query.core.expression.lambda.SQLActionExpression1;
import com.easy.query.core.expression.lambda.SQLActionExpression2;
import com.easy.query.core.expression.parser.core.EntitySQLTableOwner;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.parser.core.base.WherePredicate;
import com.easy.query.core.expression.parser.core.base.core.FilterContext;
import com.easy.query.core.expression.sql.builder.EntityExpressionBuilder;
import com.easy.query.core.util.EasyArrayUtil;
import com.easy.query.core.util.EasyCollectionUtil;
import com.easy.query.core.util.EasyObjectUtil;

import java.util.Collections;
import java.util.List;

/**
 * @author xuejiaming
 * @Description: 默认的数据库条件比较
 * create time 2023/2/7 06:58
 */
public class WherePredicateImpl<T1> implements WherePredicate<T1> {
    private final FilterContext filterContext;
    private final TableAvailable table;

    public WherePredicateImpl(TableAvailable table, FilterContext filterContext) {
        this.filterContext = filterContext;
        this.table = table;

    }

    @Override
    public Filter getFilter() {
        return filterContext.getFilter();
    }

    @Override
    public WherePredicate<T1> castChain() {
        return this;
    }

    @Override
    public EntityExpressionBuilder getEntityExpressionBuilder() {
        return filterContext.getEntityExpressionBuilder();
    }

    @Override
    public <T2> WherePredicate<T1> compareSelf(boolean condition, EntitySQLTableOwner<T2> sub, String property1, String property2, SQLPredicateCompare sqlPredicateCompare) {
        if (condition) {
            TableAvailable rightTable = sub.getTable();
            getFilter().valueColumnFilter(table, property1, rightTable, property2, sqlPredicateCompare);
        }
        return castChain();
    }

    @Override
    public <T2> WherePredicate<T1> multiEq(boolean condition, EntitySQLTableOwner<T2> sub, String[] properties1, String[] properties2) {
        if (condition) {
            if (EasyArrayUtil.isEmpty(properties1)) {
                throw new EasyQueryInvalidOperationException("properties1 is empty");
            }
            if (EasyArrayUtil.isEmpty(properties2)) {
                throw new EasyQueryInvalidOperationException("properties2 is empty");
            }
            if (properties1.length != properties2.length) {
                throw new EasyQueryInvalidOperationException("properties1.length != properties2.length");
            }
            if (properties1.length == 1) {
                eq(sub, properties1[0], properties2[0]);
            } else {
                and(() -> {
                    for (int i = 0; i < properties1.length; i++) {
                        eq(sub, properties1[i], properties2[i]);
                    }
                });
            }


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
            getFilter().and();
        }
        return this;
    }

    @Override
    public WherePredicate<T1> and(boolean condition, SQLActionExpression1<WherePredicate<T1>> sqlWherePredicateSQLExpression) {
        if (condition) {
            getFilter().and(f -> {
                Filter filter = filterContext.getFilter();
                filterContext.setFilter(f);
                sqlWherePredicateSQLExpression.apply(new WherePredicateImpl<>(table, filterContext));
                filterContext.setFilter(filter);
            });
        }
        return this;
    }

    @Override
    public WherePredicate<T1> and(boolean condition, SQLActionExpression sqlWherePredicateSQLExpression) {
        if (condition) {
            getFilter().and(f -> {
                Filter filter = filterContext.getFilter();
                filterContext.setFilter(f);
                sqlWherePredicateSQLExpression.apply();
                filterContext.setFilter(filter);
            });
        }
        return this;
    }

    @Override
    public <T2> WherePredicate<T1> and(boolean condition, WherePredicate<T2> t2WherePredicate, SQLActionExpression2<WherePredicate<T1>, WherePredicate<T2>> sqlWherePredicateSQLExpression) {
        if (condition) {
            getFilter().and(f -> {
                Filter filter = filterContext.getFilter();
                filterContext.setFilter(f);
                sqlWherePredicateSQLExpression.apply(new WherePredicateImpl<>(table, filterContext), new WherePredicateImpl<>(t2WherePredicate.getTable(), filterContext));
                filterContext.setFilter(filter);
            });
        }
        return this;
    }

    @Override
    public WherePredicate<T1> or(boolean condition) {
        if (condition) {
            getFilter().or();
        }
        return this;
    }

    @Override
    public WherePredicate<T1> or(boolean condition, SQLActionExpression1<WherePredicate<T1>> sqlWherePredicateSQLExpression) {
        if (condition) {
            getFilter().or(f -> {
                Filter filter = filterContext.getFilter();
                filterContext.setFilter(f);
                sqlWherePredicateSQLExpression.apply(new WherePredicateImpl<>(table, filterContext));
                filterContext.setFilter(filter);
            });
        }
        return this;
    }

    @Override
    public WherePredicate<T1> or(boolean condition, SQLActionExpression sqlWherePredicateSQLExpression) {
        if (condition) {
            getFilter().or(f -> {
                Filter filter = filterContext.getFilter();
                filterContext.setFilter(f);
                sqlWherePredicateSQLExpression.apply();
                filterContext.setFilter(filter);
            });
        }
        return this;
    }

    @Override
    public <T2> WherePredicate<T1> or(boolean condition, WherePredicate<T2> t2WherePredicate, SQLActionExpression2<WherePredicate<T1>, WherePredicate<T2>> sqlWherePredicateSQLExpression) {
        if (condition) {
            getFilter().or(f -> {
                Filter filter = filterContext.getFilter();
                filterContext.setFilter(f);
                sqlWherePredicateSQLExpression.apply(new WherePredicateImpl<>(table, filterContext), new WherePredicateImpl<>(t2WherePredicate.getTable(), filterContext));
                filterContext.setFilter(filter);
            });
        }
        return this;
    }

    @Override
    public <T2> WherePredicate<T2> withOther(WherePredicate<T2> wherePredicate) {
        return new WherePredicateImpl<>(wherePredicate.getTable(), filterContext);
    }

    @Override
    public <T> SQLNative<T> getSQLNative() {
        return EasyObjectUtil.typeCastNullable(getFilter());
    }

    @Override
    public TableAvailable getTable() {
        return table;
    }


    @Override
    public WherePredicate<T1> multiEq(boolean condition, String[] properties, List<Object> vals) {
        if (condition) {
            if (EasyArrayUtil.isEmpty(properties)) {
                throw new EasyQueryInvalidOperationException("properties is empty");
            }
            if (EasyCollectionUtil.isEmpty(vals)) {
                throw new EasyQueryInvalidOperationException("vals is empty");
            }
            if (properties.length != vals.size()) {
                throw new EasyQueryInvalidOperationException("properties.length != vals.size()");
            }
            if (properties.length == 1) {
                eq(properties[0], vals.get(0));
            } else {
                getFilter().relationEq(getTable(), properties, Collections.singletonList(vals));
//                throw new UnsupportedOperationException("还没实现");
            }
        }
        return this;
    }

    @Override
    public WherePredicate<T1> range(boolean condition, String property, boolean conditionLeft, Object valLeft, boolean conditionRight, Object valRight, SQLRangeEnum sqlRange) {
        if (condition) {
            Filter filter = getFilter();
            boolean isOr = filter.isOr();
            if (isOr) {
                or(() -> {
                    range0(property,conditionLeft,valLeft,conditionRight,valRight,sqlRange);
                });
            }else{
                and(() -> {
                    range0(property,conditionLeft,valLeft,conditionRight,valRight,sqlRange);
                });
            }
        }
        return castChain();
    }

    public void range0( String property, boolean conditionLeft, Object valLeft, boolean conditionRight, Object valRight, SQLRangeEnum sqlRange) {
        if (conditionLeft) {
            boolean openFirst = SQLRangeEnum.openFirst(sqlRange);
            getFilter().valueCompare(table, property, valLeft, openFirst ? SQLPredicateCompareEnum.GT : SQLPredicateCompareEnum.GE);
            getFilter().and();
        }
        if (conditionRight) {
            boolean openEnd = SQLRangeEnum.openEnd(sqlRange);
            getFilter().valueCompare(table, property, valRight, openEnd ? SQLPredicateCompareEnum.LT : SQLPredicateCompareEnum.LE);
        }
    }
}
