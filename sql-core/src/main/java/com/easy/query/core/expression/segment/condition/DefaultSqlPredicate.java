package com.easy.query.core.expression.segment.condition;

import com.easy.query.core.basic.api.select.Queryable;
import com.easy.query.core.expression.func.ColumnFunction;
import com.easy.query.core.enums.SqlLikeEnum;
import com.easy.query.core.enums.SqlPredicateCompare;
import com.easy.query.core.enums.SqlRangeEnum;
import com.easy.query.core.expression.lambda.Property;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.parser.core.SqlWherePredicate;
import com.easy.query.core.expression.segment.condition.predicate.ColumnCollectionPredicate;
import com.easy.query.core.expression.segment.condition.predicate.ColumnExistsSubQueryPredicate;
import com.easy.query.core.expression.segment.condition.predicate.ColumnSubQueryPredicate;
import com.easy.query.core.expression.segment.condition.predicate.ColumnValuePredicate;
import com.easy.query.core.expression.segment.condition.predicate.ColumnWithColumnPredicate;
import com.easy.query.core.expression.segment.condition.predicate.FuncColumnValuePredicate;
import com.easy.query.core.enums.SqlPredicateCompareEnum;
import com.easy.query.core.expression.lambda.SqlExpression;
import com.easy.query.core.expression.segment.condition.predicate.ColumnPredicate;
import com.easy.query.core.expression.sql.builder.EntityExpressionBuilder;
import com.easy.query.core.util.LambdaUtil;
import com.easy.query.core.util.SqlUtil;

import java.util.Collection;

/**
 * @FileName: DefaultSqlPredicate.java
 * @Description: 默认的数据库条件比较
 * @Date: 2023/2/7 06:58
 * @author xuejiaming
 */
public class DefaultSqlPredicate<T1> implements SqlWherePredicate<T1> {
    private final int index;
    private final EntityExpressionBuilder entityExpressionBuilder;
    private final boolean reverse;
    private final PredicateSegment rootPredicateSegment;
    private final TableAvailable entityTable;
    private PredicateSegment nextPredicateSegment;

    public DefaultSqlPredicate(int index, EntityExpressionBuilder entityExpressionBuilder, PredicateSegment predicateSegment) {
        this(index,entityExpressionBuilder,predicateSegment,false);

    }
    public DefaultSqlPredicate(int index, EntityExpressionBuilder entityExpressionBuilder, PredicateSegment predicateSegment,boolean reverse) {
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
    protected void next(){
        if (reverse) {
            nextOr();
        } else {
            nextAnd();
        }
    }
    private SqlPredicateCompare getReallyPredicateCompare(SqlPredicateCompare sqlPredicateCompare){
        return reverse?sqlPredicateCompare.toReverse():sqlPredicateCompare;
    }

    protected void appendThisPredicate(Property<T1, ?> column, Object val, SqlPredicateCompare condition) {
        String propertyName = LambdaUtil.getPropertyName(column);

        nextPredicateSegment.setPredicate(new ColumnValuePredicate(getTable(), propertyName, val, getReallyPredicateCompare(condition), entityExpressionBuilder.getRuntimeContext()));
    }
    protected void appendThisFuncPredicate(Property<T1, ?> column, ColumnFunction func, SqlPredicateCompare compare, Object val) {
        String propertyName = LambdaUtil.getPropertyName(column);

        nextPredicateSegment.setPredicate(new FuncColumnValuePredicate(getTable(),func, propertyName, val, compare, entityExpressionBuilder.getRuntimeContext()));
    }


    @Override
    public DefaultSqlPredicate<T1> eq(boolean condition, Property<T1, ?> column, Object val) {
        if (condition) {
            appendThisPredicate(column, val, SqlPredicateCompareEnum.EQ);
            next();
        }
        return this;
    }

    @Override
    public SqlWherePredicate<T1> ne(boolean condition, Property<T1, ?> column, Object val) {
        if (condition) {
            appendThisPredicate(column, val, SqlPredicateCompareEnum.NE);
            next();
        }
        return this;
    }

    @Override
    public SqlWherePredicate<T1> gt(boolean condition, Property<T1, ?> column, Object val) {
        if (condition) {
            appendThisPredicate(column, val, SqlPredicateCompareEnum.GT);
            next();
        }
        return this;
    }

    @Override
    public SqlWherePredicate<T1> ge(boolean condition, Property<T1, ?> column, Object val) {
        if (condition) {
            appendThisPredicate(column, val, SqlPredicateCompareEnum.GE);
            next();
        }
        return this;
    }

    @Override
    public SqlWherePredicate<T1> like(boolean condition, Property<T1, ?> column, Object val, SqlLikeEnum sqlLike) {
        if (condition) {
            appendThisPredicate(column, SqlUtil.getLikeParameter(val, sqlLike), SqlPredicateCompareEnum.LIKE);
            next();
        }
        return this;
    }

    @Override
    public SqlWherePredicate<T1> notLike(boolean condition, Property<T1, ?> column, Object val, SqlLikeEnum sqlLike) {
        if (condition) {
            appendThisPredicate(column, val, SqlPredicateCompareEnum.NOT_LIKE);
            next();
        }
        return this;
    }

    @Override
    public SqlWherePredicate<T1> le(boolean condition, Property<T1, ?> column, Object val) {
        if (condition) {
            appendThisPredicate(column, val, SqlPredicateCompareEnum.LE);
            next();
        }
        return this;
    }

    @Override
    public SqlWherePredicate<T1> lt(boolean condition, Property<T1, ?> column, Object val) {
        if (condition) {
            appendThisPredicate(column, val, SqlPredicateCompareEnum.LT);
            next();
        }
        return this;
    }


    @Override
    public SqlWherePredicate<T1> isNull(boolean condition, Property<T1, ?> column) {
        if (condition) {
            String propertyName = LambdaUtil.getPropertyName(column);
            nextPredicateSegment.setPredicate(new ColumnPredicate(getTable(), propertyName, getReallyPredicateCompare(SqlPredicateCompareEnum.IS_NULL), entityExpressionBuilder.getRuntimeContext()));
            next();
        }
        return this;
    }

    @Override
    public SqlWherePredicate<T1> isNotNull(boolean condition, Property<T1, ?> column) {
        if (condition) {
            String propertyName = LambdaUtil.getPropertyName(column);
            nextPredicateSegment.setPredicate(new ColumnPredicate(getTable(), propertyName, getReallyPredicateCompare(SqlPredicateCompareEnum.IS_NOT_NULL), entityExpressionBuilder.getRuntimeContext()));
            next();
        }
        return this;
    }


    @Override
    public SqlWherePredicate<T1> in(boolean condition, Property<T1, ?> column, Collection<?> collection) {
        if (condition) {
            String propertyName = LambdaUtil.getPropertyName(column);
            nextPredicateSegment.setPredicate(new ColumnCollectionPredicate(getTable(), propertyName, collection, getReallyPredicateCompare(SqlPredicateCompareEnum.IN), entityExpressionBuilder.getRuntimeContext()));
            next();
        }
        return this;
    }

    @Override
    public <TProperty> SqlWherePredicate<T1> in(boolean condition, Property<T1, TProperty> column, Queryable<TProperty> subQueryable) {
        if (condition) {
            String propertyName = LambdaUtil.getPropertyName(column);
            nextPredicateSegment.setPredicate(new ColumnSubQueryPredicate(getTable(), propertyName, subQueryable, getReallyPredicateCompare(SqlPredicateCompareEnum.IN), entityExpressionBuilder.getRuntimeContext()));
            next();
        }
        return this;
    }

    @Override
    public SqlWherePredicate<T1> notIn(boolean condition, Property<T1, ?> column, Collection<?> collection) {
        if (condition) {
            String propertyName = LambdaUtil.getPropertyName(column);
            nextPredicateSegment.setPredicate(new ColumnCollectionPredicate(getTable(), propertyName, collection, getReallyPredicateCompare(SqlPredicateCompareEnum.NOT_IN), entityExpressionBuilder.getRuntimeContext()));
            next();
        }
        return this;
    }

    @Override
    public <TProperty> SqlWherePredicate<T1> notIn(boolean condition, Property<T1, ?> column, Queryable<TProperty> subQueryable) {
        if (condition) {
            String propertyName = LambdaUtil.getPropertyName(column);
            nextPredicateSegment.setPredicate(new ColumnSubQueryPredicate(getTable(), propertyName, subQueryable, getReallyPredicateCompare(SqlPredicateCompareEnum.NOT_IN), entityExpressionBuilder.getRuntimeContext()));
            next();
        }
        return this;
    }

    @Override
    public <T2> SqlWherePredicate<T1> exists(boolean condition,Queryable<T2> subQueryable, SqlExpression<SqlWherePredicate<T2>> whereExpression) {
        if (condition) {
            Queryable<T2> existsQueryable = subQueryable.cloneQueryable().where(whereExpression).select("1");

            nextPredicateSegment.setPredicate(new ColumnExistsSubQueryPredicate(getTable(), existsQueryable, getReallyPredicateCompare(SqlPredicateCompareEnum.EXISTS), entityExpressionBuilder.getRuntimeContext()));
            next();
        }
        return this;
    }

    @Override
    public <T2> SqlWherePredicate<T1> notExists(boolean condition, Queryable<T2> subQueryable, SqlExpression<SqlWherePredicate<T2>> whereExpression) {
        if (condition) {
            Queryable<T2> existsQueryable = subQueryable.cloneQueryable().where(whereExpression).select("1");

            nextPredicateSegment.setPredicate(new ColumnExistsSubQueryPredicate(getTable(), existsQueryable, getReallyPredicateCompare(SqlPredicateCompareEnum.NOT_EXISTS), entityExpressionBuilder.getRuntimeContext()));
            next();
        }
        return this;
    }

    @Override
    public SqlWherePredicate<T1> range(boolean condition, Property<T1, ?> column, boolean conditionLeft, Object valLeft, boolean conditionRight, Object valRight, SqlRangeEnum sqlRange) {
        if (condition) {
            if (conditionLeft) {
                boolean openFirst = SqlRangeEnum.openFirst(sqlRange);
                appendThisPredicate(column, valLeft, getReallyPredicateCompare(openFirst ? SqlPredicateCompareEnum.GT : SqlPredicateCompareEnum.GE));
                next();
            }
            if (conditionRight) {
                boolean openEnd = SqlRangeEnum.openEnd(sqlRange);
                appendThisPredicate(column, valRight, getReallyPredicateCompare(openEnd ? SqlPredicateCompareEnum.LT : SqlPredicateCompareEnum.LE));
                next();
            }
        }
        return this;
    }

    @Override
    public SqlWherePredicate<T1> columnFunc(boolean condition, Property<T1, ?> column, ColumnFunction easyFunc, SqlPredicateCompare sqlPredicateCompare, Object val) {

        if (condition) {
            appendThisFuncPredicate(column,easyFunc, getReallyPredicateCompare(sqlPredicateCompare), val);
            next();
        }
        return this;
    }

    @Override
    public <T2> DefaultSqlPredicate<T1> eq(boolean condition, SqlWherePredicate<T2> sub, Property<T1, ?> column1, Property<T2, ?> column2) {
        if (condition) {
            TableAvailable leftTable = getTable();
            String leftPropertyName = LambdaUtil.getPropertyName(column1);
            TableAvailable rightTable = sub.getTable();
            String rightPropertyName = LambdaUtil.getPropertyName(column2);
            nextPredicateSegment.setPredicate(new ColumnWithColumnPredicate(leftTable, leftPropertyName, rightTable, rightPropertyName, getReallyPredicateCompare(SqlPredicateCompareEnum.EQ), entityExpressionBuilder.getRuntimeContext()));
            next();

        }
        return this;
    }

    @Override
    public <T2> SqlWherePredicate<T2> then(SqlWherePredicate<T2> sub) {
        if (this.nextPredicateSegment instanceof AndPredicateSegment) {
            sub.and();
        } else if (this.nextPredicateSegment instanceof OrPredicateSegment) {
            sub.or();
            and0();
        }
        return sub;
    }

    @Override
    public SqlWherePredicate<T1> and(boolean condition) {
        if (condition) {
            and0();
        }
        return this;
    }

    @Override
    public SqlWherePredicate<T1> and(boolean condition, SqlExpression<SqlWherePredicate<T1>> predicateSqlExpression) {
        if (condition) {
            and0();
             SqlWherePredicate<T1> sqlPredicate = entityExpressionBuilder.getRuntimeContext().getEasyQueryLambdaFactory().createSqlPredicate(index, entityExpressionBuilder, this.nextPredicateSegment);
            predicateSqlExpression.apply(sqlPredicate);
            next();
        }
        return this;
    }
    protected void and0(){
        if(reverse){
            this.nextPredicateSegment = new OrPredicateSegment();
        }else{
            this.nextPredicateSegment = new AndPredicateSegment();
        }
    }

    @Override
    public SqlWherePredicate<T1> or(boolean condition) {
        if (condition) {
            or0();
        }
        return this;
    }
    protected void or0(){
        if(reverse){
            this.nextPredicateSegment = new AndPredicateSegment();
        }else{
            this.nextPredicateSegment = new OrPredicateSegment();
        }
    }

    @Override
    public SqlWherePredicate<T1> or(boolean condition, SqlExpression<SqlWherePredicate<T1>> predicateSqlExpression) {
        if (condition) {
            or0();
             SqlWherePredicate<T1> sqlPredicate = entityExpressionBuilder.getRuntimeContext().getEasyQueryLambdaFactory().createSqlPredicate(index, entityExpressionBuilder, this.nextPredicateSegment);
            predicateSqlExpression.apply(sqlPredicate);
            next();
        }
        return this;
    }

    @Override
    public TableAvailable getTable() {
        return entityTable;
    }
}
