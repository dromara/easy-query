package org.easy.query.core.impl.lambda.select;

import org.easy.query.core.abstraction.lambda.Property;
import org.easy.query.core.abstraction.lambda.SqlExpression;
import org.easy.query.core.enums.SqlKeywordEnum;
import org.easy.query.core.impl.SelectContext;
import org.easy.query.core.abstraction.sql.base.SqlPredicate;
import org.easy.query.core.abstraction.sql.base.WherePredicate;
import org.easy.query.core.segments.*;
import org.easy.query.core.segments.predicate.ColumnCollectionPredicate;
import org.easy.query.core.segments.predicate.ColumnColumnPredicate;
import org.easy.query.core.segments.predicate.ColumnPredicate;
import org.easy.query.core.segments.predicate.ColumnValuePredicate;

import java.util.Collection;

/**
 * @FileName: SqlWherePredicate.java
 * @Description: 文件说明
 * @Date: 2023/2/7 06:58
 * @Created by xuejiaming
 */
public class DefaultSqlPredicate<T1> implements SqlPredicate<T1> {
    private final int index;
    private final SelectContext selectContext;
    private final PredicateSegment rootPredicateSegment;
    private PredicateSegment nextPredicateSegment;

    public DefaultSqlPredicate(int index, SelectContext selectContext, PredicateSegment predicateSegment) {
        this.index = index;
        this.selectContext = selectContext;
        this.rootPredicateSegment = predicateSegment;
        this.nextPredicateSegment = new AndPredicateSegment();
    }

    @Override
    public int getIndex() {
        return index;
    }

    protected void nextAnd() {
        this.rootPredicateSegment.addPredicateSegment(nextPredicateSegment);
        this.nextPredicateSegment = new AndPredicateSegment();
    }

    protected void nextOr() {
        this.rootPredicateSegment.addPredicateSegment(nextPredicateSegment);
        this.nextPredicateSegment = new OrPredicateSegment();
    }

    protected void appendThisPredicate(Property<T1, ?> column, Object val, SqlKeywordEnum condition) {
        String columnName = selectContext.getTable(getIndex()).getColumnName(column);
        nextPredicateSegment.setPredicate(new ColumnValuePredicate(index, columnName, val, condition, selectContext));
    }


    @Override
    public DefaultSqlPredicate<T1> eq(boolean condition, Property<T1, ?> column, Object val) {
        if (condition) {
            appendThisPredicate(column, val, SqlKeywordEnum.EQ);
            nextAnd();
        }
        return this;
    }

    @Override
    public SqlPredicate<T1> ne(boolean condition, Property<T1, ?> column, Object val) {
        if (condition) {
            appendThisPredicate(column, val, SqlKeywordEnum.NE);
            nextAnd();
        }
        return this;
    }

    @Override
    public SqlPredicate<T1> gt(boolean condition, Property<T1, ?> column, Object val) {
        if (condition) {
            appendThisPredicate(column, val, SqlKeywordEnum.GT);
            nextAnd();
        }
        return this;
    }

    @Override
    public SqlPredicate<T1> ge(boolean condition, Property<T1, ?> column, Object val) {
        if (condition) {
            appendThisPredicate(column, val, SqlKeywordEnum.GE);
            nextAnd();
        }
        return this;
    }

    @Override
    public SqlPredicate<T1> like(boolean condition, Property<T1, ?> column, Object val) {
        if (condition) {
            appendThisPredicate(column, val, SqlKeywordEnum.LIKE);
            nextAnd();
        }
        return this;
    }
    @Override
    public SqlPredicate<T1> notLike(boolean condition, Property<T1, ?> column, Object val) {
        if (condition) {
            appendThisPredicate(column, val, SqlKeywordEnum.NOT_LIKE);
            nextAnd();
        }
        return this;
    }

    @Override
    public SqlPredicate<T1> le(boolean condition, Property<T1, ?> column, Object val) {
        if (condition) {
            appendThisPredicate(column, val, SqlKeywordEnum.LE);
            nextAnd();
        }
        return this;
    }

    @Override
    public SqlPredicate<T1> lt(boolean condition, Property<T1, ?> column, Object val) {
        if (condition) {
            appendThisPredicate(column, val, SqlKeywordEnum.LT);
            nextAnd();
        }
        return this;
    }


    @Override
    public SqlPredicate<T1> isNull(boolean condition, Property<T1, ?> column) {
        if (condition) {
            String columnName = selectContext.getTable(getIndex()).getColumnName(column);
            nextPredicateSegment.setPredicate(new ColumnPredicate(index, columnName, SqlKeywordEnum.IS_NULL, selectContext));
            nextAnd();
        }
        return this;
    }

    @Override
    public SqlPredicate<T1> isNotNull(boolean condition, Property<T1, ?> column) {
        if (condition) {
            String columnName = selectContext.getTable(getIndex()).getColumnName(column);
            nextPredicateSegment.setPredicate(new ColumnPredicate(index, columnName, SqlKeywordEnum.IS_NOT_NULL, selectContext));
            nextAnd();
        }
        return this;
    }

    @Override
    public SqlPredicate<T1> in(boolean condition, Property<T1, ?> column, Collection<?> collection) {
        if (condition) {
            String columnName = selectContext.getTable(getIndex()).getColumnName(column);
            nextPredicateSegment.setPredicate(new ColumnCollectionPredicate(index, columnName,collection, SqlKeywordEnum.IN, selectContext));
            nextAnd();
        }
        return this;
    }

    @Override
    public SqlPredicate<T1> notIn(boolean condition, Property<T1, ?> column, Collection<?> collection) {
        if (condition) {
            String columnName = selectContext.getTable(getIndex()).getColumnName(column);
            nextPredicateSegment.setPredicate(new ColumnCollectionPredicate(index, columnName,collection, SqlKeywordEnum.NOT_IN, selectContext));
            nextAnd();
        }
        return this;
    }

    @Override
    public <T2, TChain2> DefaultSqlPredicate<T1> eq(boolean condition, WherePredicate<T2, TChain2> sub, Property<T1, ?> column1, Property<T2, ?> column2) {
        if (condition) {
            String columnName = selectContext.getTable(getIndex()).getColumnName(column1);
            String columnName2 = selectContext.getTable(sub.getIndex()).getColumnName(column2);
            nextPredicateSegment.setPredicate(new ColumnColumnPredicate(index, columnName, sub.getIndex(), columnName2, SqlKeywordEnum.EQ, selectContext));
            nextAnd();

//            PredicateSegment0 predicateSegment =  PredicateSegment0.createColumn2Column(index, columnName, selectContext, SqlCompareEnum.EQ,sub.getIndex(),columnName2);
//            sqlSegment0Builder.append(predicateSegment);
        }
        return this;
    }

    @Override
    public <T2, TChain2> WherePredicate<T2, TChain2> then(WherePredicate<T2, TChain2> sub) {
        if (this.nextPredicateSegment instanceof AndPredicateSegment) {
            sub.and();
        } else if (this.nextPredicateSegment instanceof OrPredicateSegment) {
            sub.or();
            this.nextPredicateSegment = new AndPredicateSegment();
        }
        return sub;
    }

    @Override
    public SqlPredicate<T1> and(boolean condition) {
        if (condition) {
            this.nextPredicateSegment = new AndPredicateSegment();
        }
        return this;
    }

    @Override
    public SqlPredicate<T1> and(boolean condition, SqlExpression<SqlPredicate<T1>> predicateSqlExpression) {
        if (condition) {
            this.rootPredicateSegment.addPredicateSegment(this.nextPredicateSegment);
            SqlPredicate<T1> sqlPredicate = selectContext.getRuntimeContext().getEasyQueryLambdaFactory().createSqlPredicate(index, selectContext, this.nextPredicateSegment);
            predicateSqlExpression.apply(sqlPredicate);
        }
        return this;
    }

    @Override
    public SqlPredicate<T1> or(boolean condition) {
        if (condition) {
            this.nextPredicateSegment = new OrPredicateSegment();
        }
        return this;
    }

    @Override
    public SqlPredicate<T1> or(boolean condition, SqlExpression<SqlPredicate<T1>> predicateSqlExpression) {
        if (condition) {
            this.nextPredicateSegment = new OrPredicateSegment();
            this.rootPredicateSegment.addPredicateSegment(this.nextPredicateSegment);
            SqlPredicate<T1> sqlPredicate = selectContext.getRuntimeContext().getEasyQueryLambdaFactory().createSqlPredicate(index, selectContext, this.nextPredicateSegment);
            predicateSqlExpression.apply(sqlPredicate);
        }
        return this;
    }
}
