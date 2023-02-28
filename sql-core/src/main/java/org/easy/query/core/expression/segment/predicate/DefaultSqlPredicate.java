package org.easy.query.core.expression.segment.predicate;

import org.easy.query.core.basic.api.context.SqlContext;
import org.easy.query.core.enums.SqlPredicateCompareEnum;
import org.easy.query.core.expression.lambda.Property;
import org.easy.query.core.expression.lambda.SqlExpression;
import org.easy.query.core.expression.segment.AndPredicateSegment;
import org.easy.query.core.expression.segment.OrPredicateSegment;
import org.easy.query.core.expression.segment.PredicateSegment;
import org.easy.query.core.expression.parser.abstraction.SqlPredicate;
import org.easy.query.core.expression.parser.abstraction.internal.WherePredicate;
import org.easy.query.core.expression.segment.predicate.node.ColumnCollectionPredicate;
import org.easy.query.core.expression.segment.predicate.node.ColumnPredicate;
import org.easy.query.core.expression.segment.predicate.node.ColumnValuePredicate;
import org.easy.query.core.expression.segment.predicate.node.ColumnWithColumnPredicate;
import org.easy.query.core.query.builder.SqlTableInfo;

import java.util.Collection;

/**
 * @FileName: DefaultSqlPredicate.java
 * @Description: 默认的数据库条件比较
 * @Date: 2023/2/7 06:58
 * @Created by xuejiaming
 */
public class DefaultSqlPredicate<T1> implements SqlPredicate<T1> {
    private final int index;
    private final SqlContext sqlContext;
    private final PredicateSegment rootPredicateSegment;
    private PredicateSegment nextPredicateSegment;

    public DefaultSqlPredicate(int index, SqlContext sqlContext, PredicateSegment predicateSegment) {
        this.index = index;
        this.sqlContext = sqlContext;
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

    protected void appendThisPredicate(Property<T1, ?> column, Object val, SqlPredicateCompareEnum condition) {
        SqlTableInfo table = sqlContext.getTable(getIndex());
        String propertyName = table.getPropertyName(column);
        nextPredicateSegment.setPredicate(new ColumnValuePredicate(table, propertyName, val, condition, sqlContext));
    }


    @Override
    public DefaultSqlPredicate<T1> eq(boolean condition, Property<T1, ?> column, Object val) {
        if (condition) {
            appendThisPredicate(column, val, SqlPredicateCompareEnum.EQ);
            nextAnd();
        }
        return this;
    }

    @Override
    public SqlPredicate<T1> ne(boolean condition, Property<T1, ?> column, Object val) {
        if (condition) {
            appendThisPredicate(column, val, SqlPredicateCompareEnum.NE);
            nextAnd();
        }
        return this;
    }

    @Override
    public SqlPredicate<T1> gt(boolean condition, Property<T1, ?> column, Object val) {
        if (condition) {
            appendThisPredicate(column, val, SqlPredicateCompareEnum.GT);
            nextAnd();
        }
        return this;
    }

    @Override
    public SqlPredicate<T1> ge(boolean condition, Property<T1, ?> column, Object val) {
        if (condition) {
            appendThisPredicate(column, val, SqlPredicateCompareEnum.GE);
            nextAnd();
        }
        return this;
    }

    @Override
    public SqlPredicate<T1> like(boolean condition, Property<T1, ?> column, Object val) {
        if (condition) {
            appendThisPredicate(column, val, SqlPredicateCompareEnum.LIKE);
            nextAnd();
        }
        return this;
    }
    @Override
    public SqlPredicate<T1> notLike(boolean condition, Property<T1, ?> column, Object val) {
        if (condition) {
            appendThisPredicate(column, val, SqlPredicateCompareEnum.NOT_LIKE);
            nextAnd();
        }
        return this;
    }

    @Override
    public SqlPredicate<T1> le(boolean condition, Property<T1, ?> column, Object val) {
        if (condition) {
            appendThisPredicate(column, val, SqlPredicateCompareEnum.LE);
            nextAnd();
        }
        return this;
    }

    @Override
    public SqlPredicate<T1> lt(boolean condition, Property<T1, ?> column, Object val) {
        if (condition) {
            appendThisPredicate(column, val, SqlPredicateCompareEnum.LT);
            nextAnd();
        }
        return this;
    }


    @Override
    public SqlPredicate<T1> isNull(boolean condition, Property<T1, ?> column) {
        if (condition) {
            SqlTableInfo table = sqlContext.getTable(getIndex());
            String propertyName = table.getPropertyName(column);
            nextPredicateSegment.setPredicate(new ColumnPredicate(table, propertyName, SqlPredicateCompareEnum.IS_NULL, sqlContext));
            nextAnd();
        }
        return this;
    }

    @Override
    public SqlPredicate<T1> isNotNull(boolean condition, Property<T1, ?> column) {
        if (condition) {
            SqlTableInfo table = sqlContext.getTable(getIndex());
            String propertyName = table.getPropertyName(column);
            nextPredicateSegment.setPredicate(new ColumnPredicate(table, propertyName, SqlPredicateCompareEnum.IS_NOT_NULL, sqlContext));
            nextAnd();
        }
        return this;
    }

    @Override
    public SqlPredicate<T1> in(boolean condition, Property<T1, ?> column, Collection<?> collection) {
        if (condition) {
            SqlTableInfo table = sqlContext.getTable(getIndex());
            String propertyName = table.getPropertyName(column);
            nextPredicateSegment.setPredicate(new ColumnCollectionPredicate(table, propertyName,collection, SqlPredicateCompareEnum.IN, sqlContext));
            nextAnd();
        }
        return this;
    }

    @Override
    public SqlPredicate<T1> notIn(boolean condition, Property<T1, ?> column, Collection<?> collection) {
        if (condition) {
            SqlTableInfo table = sqlContext.getTable(getIndex());
            String propertyName = table.getPropertyName(column);
            nextPredicateSegment.setPredicate(new ColumnCollectionPredicate(table, propertyName,collection, SqlPredicateCompareEnum.NOT_IN, sqlContext));
            nextAnd();
        }
        return this;
    }

    @Override
    public <T2, TChain2> DefaultSqlPredicate<T1> eq(boolean condition, WherePredicate<T2, TChain2> sub, Property<T1, ?> column1, Property<T2, ?> column2) {
        if (condition) {
            SqlTableInfo leftTable = sqlContext.getTable(getIndex());
            String leftPropertyName = leftTable.getPropertyName(column1);
            SqlTableInfo rightTable = sqlContext.getTable(sub.getIndex());
            String rightPropertyName = rightTable.getPropertyName(column2);
            nextPredicateSegment.setPredicate(new ColumnWithColumnPredicate(leftTable, leftPropertyName, rightTable, rightPropertyName, SqlPredicateCompareEnum.EQ, sqlContext));
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
            SqlPredicate<T1> sqlPredicate = sqlContext.getRuntimeContext().getEasyQueryLambdaFactory().createSqlPredicate(index, sqlContext, this.nextPredicateSegment);
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
            SqlPredicate<T1> sqlPredicate = sqlContext.getRuntimeContext().getEasyQueryLambdaFactory().createSqlPredicate(index, sqlContext, this.nextPredicateSegment);
            predicateSqlExpression.apply(sqlPredicate);
        }
        return this;
    }
}
