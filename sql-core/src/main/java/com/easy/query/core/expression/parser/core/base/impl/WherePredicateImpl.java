package com.easy.query.core.expression.parser.core.base.impl;

import com.easy.query.core.basic.api.select.Query;
import com.easy.query.core.enums.SQLLikeEnum;
import com.easy.query.core.enums.SQLPredicateCompare;
import com.easy.query.core.enums.SQLPredicateCompareEnum;
import com.easy.query.core.enums.SQLRangeEnum;
import com.easy.query.core.expression.func.ColumnFunction;
import com.easy.query.core.expression.func.ColumnPropertyFunction;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.parser.core.base.WherePredicate;
import com.easy.query.core.expression.segment.condition.AndPredicateSegment;
import com.easy.query.core.expression.segment.condition.OrPredicateSegment;
import com.easy.query.core.expression.segment.condition.PredicateSegment;
import com.easy.query.core.expression.segment.condition.predicate.ColumnCollectionPredicate;
import com.easy.query.core.expression.segment.condition.predicate.ColumnExistsSubQueryPredicate;
import com.easy.query.core.expression.segment.condition.predicate.ColumnInSubQueryPredicate;
import com.easy.query.core.expression.segment.condition.predicate.ColumnPredicate;
import com.easy.query.core.expression.segment.condition.predicate.ColumnValuePredicate;
import com.easy.query.core.expression.segment.condition.predicate.ColumnWithColumnPredicate;
import com.easy.query.core.expression.segment.condition.predicate.FuncColumnValuePredicate;
import com.easy.query.core.expression.sql.builder.EntityExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityQueryExpressionBuilder;
import com.easy.query.core.util.EasySQLUtil;

import java.util.Arrays;
import java.util.Collection;

/**
 * @author xuejiaming
 * @Description: 默认的数据库条件比较
 * @Date: 2023/2/7 06:58
 */
public class WherePredicateImpl<T1> implements WherePredicate<T1> {
    private final int index;
    private final EntityExpressionBuilder entityExpressionBuilder;
    private final boolean reverse;
    private final PredicateSegment rootPredicateSegment;
    private final TableAvailable entityTable;
    private PredicateSegment nextPredicateSegment;

    public WherePredicateImpl(int index, EntityExpressionBuilder entityExpressionBuilder, PredicateSegment predicateSegment) {
        this(index, entityExpressionBuilder, predicateSegment, false);

    }

    public WherePredicateImpl(int index, EntityExpressionBuilder entityExpressionBuilder, PredicateSegment predicateSegment, boolean reverse) {
        this.index = index;
        this.entityExpressionBuilder = entityExpressionBuilder;
        this.reverse = reverse;
        this.entityTable = entityExpressionBuilder.getTable(index).getEntityTable();
        this.rootPredicateSegment = predicateSegment;
        this.nextPredicateSegment = new AndPredicateSegment();
    }


    protected void nextAnd() {
        this.rootPredicateSegment.addPredicateSegment(nextPredicateSegment);
        this.nextPredicateSegment = new AndPredicateSegment();
    }

    protected void nextOr() {
        this.rootPredicateSegment.addPredicateSegment(nextPredicateSegment);
        this.nextPredicateSegment = new OrPredicateSegment();
    }

    protected void next() {
        if (reverse) {
            nextOr();
        } else {
            nextAnd();
        }
    }

    protected SQLPredicateCompare getReallyPredicateCompare(SQLPredicateCompare sqlPredicateCompare) {
        return reverse ? sqlPredicateCompare.toReverse() : sqlPredicateCompare;
    }

    protected void appendThisPredicate(String property, Object val, SQLPredicateCompare condition) {

        nextPredicateSegment.setPredicate(new ColumnValuePredicate(getTable(), property, val, getReallyPredicateCompare(condition), entityExpressionBuilder.getRuntimeContext()));
    }

    protected void appendThisFuncPredicate(String propertyName, ColumnFunction func, SQLPredicateCompare compare, Object val) {
        nextPredicateSegment.setPredicate(new FuncColumnValuePredicate(getTable(), func, propertyName, val, compare, entityExpressionBuilder.getRuntimeContext()));
    }

    @Override
    public WherePredicate<T1> eq(boolean condition, String property, Object val) {
        if (condition) {
            appendThisPredicate(property, val, SQLPredicateCompareEnum.EQ);
            next();
        }
        return this;
    }

    @Override
    public WherePredicate<T1> ne(boolean condition, String property, Object val) {
        if (condition) {
            appendThisPredicate(property, val, SQLPredicateCompareEnum.NE);
            next();
        }
        return this;
    }

    @Override
    public WherePredicate<T1> gt(boolean condition, String property, Object val) {
        if (condition) {
            appendThisPredicate(property, val, SQLPredicateCompareEnum.GT);
            next();
        }
        return this;
    }

    @Override
    public WherePredicate<T1> ge(boolean condition, String property, Object val) {
        if (condition) {
            appendThisPredicate(property, val, SQLPredicateCompareEnum.GE);
            next();
        }
        return this;
    }

    @Override
    public WherePredicate<T1> like(boolean condition, String property, Object val, SQLLikeEnum sqlLike) {
        if (condition) {
            appendThisPredicate(property, EasySQLUtil.getLikeParameter(val, sqlLike), SQLPredicateCompareEnum.LIKE);
            next();
        }
        return this;
    }

    @Override
    public WherePredicate<T1> notLike(boolean condition, String property, Object val, SQLLikeEnum sqlLike) {
        if (condition) {
            appendThisPredicate(property, EasySQLUtil.getLikeParameter(val, sqlLike), SQLPredicateCompareEnum.NOT_LIKE);
            next();
        }
        return this;
    }

    @Override
    public WherePredicate<T1> le(boolean condition, String property, Object val) {
        if (condition) {
            appendThisPredicate(property, val, SQLPredicateCompareEnum.LE);
            next();
        }
        return this;
    }

    @Override
    public WherePredicate<T1> lt(boolean condition, String property, Object val) {
        if (condition) {
            appendThisPredicate(property, val, SQLPredicateCompareEnum.LT);
            next();
        }
        return this;
    }


    @Override
    public WherePredicate<T1> isNull(boolean condition, String property) {
        if (condition) {
            nextPredicateSegment.setPredicate(new ColumnPredicate(getTable(), property, getReallyPredicateCompare(SQLPredicateCompareEnum.IS_NULL), entityExpressionBuilder.getRuntimeContext()));
            next();
        }
        return this;
    }

    @Override
    public WherePredicate<T1> isNotNull(boolean condition, String property) {
        if (condition) {
            nextPredicateSegment.setPredicate(new ColumnPredicate(getTable(), property, getReallyPredicateCompare(SQLPredicateCompareEnum.IS_NOT_NULL), entityExpressionBuilder.getRuntimeContext()));
            next();
        }
        return this;
    }


    @Override
    public WherePredicate<T1> in(boolean condition, String property, Collection<?> collection) {
        if (condition) {
            nextPredicateSegment.setPredicate(new ColumnCollectionPredicate(getTable(), property, collection, getReallyPredicateCompare(SQLPredicateCompareEnum.IN), entityExpressionBuilder.getRuntimeContext()));
            next();
        }
        return this;
    }
    @Override
    public <TProperty> WherePredicate<T1> in(boolean condition, String property, TProperty[] collection) {
        if(condition){
            nextPredicateSegment.setPredicate(new ColumnCollectionPredicate(getTable(), property, Arrays.asList(collection), getReallyPredicateCompare(SQLPredicateCompareEnum.IN), entityExpressionBuilder.getRuntimeContext()));
            next();
        }
        return this;
    }

    @Override
    public <TProperty> WherePredicate<T1> in(boolean condition, String property, Query<TProperty> subQuery) {
        if (condition) {
            subQueryIn(property, subQuery, SQLPredicateCompareEnum.IN);
        }
        return this;
    }

    @Override
    public WherePredicate<T1> notIn(boolean condition, String property, Collection<?> collection) {
        if (condition) {
            nextPredicateSegment.setPredicate(new ColumnCollectionPredicate(getTable(), property, collection, getReallyPredicateCompare(SQLPredicateCompareEnum.NOT_IN), entityExpressionBuilder.getRuntimeContext()));
            next();
        }
        return this;
    }

    @Override
    public <TProperty> WherePredicate<T1> notIn(boolean condition, String property, TProperty[] collection) {
        if(condition){
            nextPredicateSegment.setPredicate(new ColumnCollectionPredicate(getTable(), property, Arrays.asList(collection), getReallyPredicateCompare(SQLPredicateCompareEnum.NOT_IN), entityExpressionBuilder.getRuntimeContext()));
            next();
        }
        return this;
    }

    @Override
    public <TProperty> WherePredicate<T1> notIn(boolean condition, String property, Query<TProperty> subQueryable) {
        if (condition) {
            subQueryIn(property, subQueryable, SQLPredicateCompareEnum.NOT_IN);
        }
        return this;
    }

    public <TProperty> void subQueryIn(String property, Query<TProperty> subQueryable, SQLPredicateCompareEnum sqlPredicateCompare) {
        extract(subQueryable);
        nextPredicateSegment.setPredicate(new ColumnInSubQueryPredicate(getTable(), property, subQueryable, getReallyPredicateCompare(sqlPredicateCompare), entityExpressionBuilder.getRuntimeContext()));
        next();
    }

    @Override
    public <T2> WherePredicate<T1> exists(boolean condition, Query<T2> subQueryable) {
        if (condition) {
            subQueryExists(subQueryable, SQLPredicateCompareEnum.EXISTS);
        }
        return this;
    }

    @Override
    public <T2> WherePredicate<T1> notExists(boolean condition, Query<T2> subQuery) {
        if (condition) {
            subQueryExists(subQuery, SQLPredicateCompareEnum.NOT_EXISTS);
        }
        return this;
    }

    public <T2> void subQueryExists(Query<T2> subQuery, SQLPredicateCompareEnum sqlPredicateCompare) {

        extract(subQuery);
        Query<T2> existsQuery = subQuery.cloneQueryable().select("1");

        nextPredicateSegment.setPredicate(new ColumnExistsSubQueryPredicate(getTable(), existsQuery, getReallyPredicateCompare(sqlPredicateCompare), entityExpressionBuilder.getRuntimeContext()));
        next();
    }

    public <T2> void extract(Query<T2> subQuery) {

        EntityQueryExpressionBuilder subQueryableSQLEntityExpressionBuilder = subQuery.getSQLEntityExpressionBuilder();
        entityExpressionBuilder.getExpressionContext().extract(subQueryableSQLEntityExpressionBuilder.getExpressionContext());
    }

    @Override
    public WherePredicate<T1> range(boolean condition, String property, boolean conditionLeft, Object valLeft, boolean conditionRight, Object valRight, SQLRangeEnum sqlRange) {
        if (condition) {
            if (conditionLeft) {
                boolean openFirst = SQLRangeEnum.openFirst(sqlRange);
                appendThisPredicate(property, valLeft, getReallyPredicateCompare(openFirst ? SQLPredicateCompareEnum.GT : SQLPredicateCompareEnum.GE));
                next();
            }
            if (conditionRight) {
                boolean openEnd = SQLRangeEnum.openEnd(sqlRange);
                appendThisPredicate(property, valRight, getReallyPredicateCompare(openEnd ? SQLPredicateCompareEnum.LT : SQLPredicateCompareEnum.LE));
                next();
            }
        }
        return this;
    }

    @Override
    public WherePredicate<T1> columnFunc(boolean condition, ColumnPropertyFunction columnPropertyFunction, SQLPredicateCompare sqlPredicateCompare, Object val) {

        if (condition) {
            appendThisFuncPredicate(columnPropertyFunction.getPropertyName(), columnPropertyFunction.getColumnFunction(), getReallyPredicateCompare(sqlPredicateCompare), val);
            next();
        }
        return this;
    }

    @Override
    public <T2> WherePredicate<T1> eq(boolean condition, WherePredicate<T2> sub, String property1, String property2) {
        if (condition) {
            TableAvailable leftTable = getTable();
            TableAvailable rightTable = sub.getTable();
            nextPredicateSegment.setPredicate(new ColumnWithColumnPredicate(leftTable, property1, rightTable, property2, getReallyPredicateCompare(SQLPredicateCompareEnum.EQ), entityExpressionBuilder.getRuntimeContext()));
            next();

        }
        return this;
    }

    @Override
    public <T2> WherePredicate<T2> then(WherePredicate<T2> sub) {
        if (this.nextPredicateSegment instanceof AndPredicateSegment) {
            sub.and();
        } else if (this.nextPredicateSegment instanceof OrPredicateSegment) {
            sub.or();
            and0();
        }
        return sub;
    }

    @Override
    public WherePredicate<T1> and(boolean condition) {
        if (condition) {
            and0();
        }
        return this;
    }

    @Override
    public WherePredicate<T1> and(boolean condition, SQLExpression1<WherePredicate<T1>> sqlWherePredicateSQLExpression) {
        if (condition) {
            and0();
            WherePredicate<T1> sqlPredicate = entityExpressionBuilder.getRuntimeContext().getSQLExpressionInvokeFactory().createWherePredicate(index, entityExpressionBuilder, this.nextPredicateSegment);
            sqlWherePredicateSQLExpression.apply(sqlPredicate);
            next();
        }
        return this;
    }

    protected void and0() {
        if (reverse) {
            this.nextPredicateSegment = new OrPredicateSegment();
        } else {
            this.nextPredicateSegment = new AndPredicateSegment();
        }
    }

    @Override
    public WherePredicate<T1> or(boolean condition) {
        if (condition) {
            or0();
        }
        return this;
    }

    protected void or0() {
        if (reverse) {
            this.nextPredicateSegment = new AndPredicateSegment();
        } else {
            this.nextPredicateSegment = new OrPredicateSegment();
        }
    }

    @Override
    public WherePredicate<T1> or(boolean condition, SQLExpression1<WherePredicate<T1>> sqlWherePredicateSQLExpression) {
        if (condition) {
            or0();
            WherePredicate<T1> sqlPredicate = entityExpressionBuilder.getRuntimeContext().getSQLExpressionInvokeFactory().createWherePredicate(index, entityExpressionBuilder, this.nextPredicateSegment);
            sqlWherePredicateSQLExpression.apply(sqlPredicate);
            next();
        }
        return this;
    }

    @Override
    public TableAvailable getTable() {
        return entityTable;
    }
}
