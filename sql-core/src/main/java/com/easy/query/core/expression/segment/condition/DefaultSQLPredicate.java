package com.easy.query.core.expression.segment.condition;

import com.easy.query.core.basic.api.select.Queryable;
import com.easy.query.core.expression.func.ColumnFunction;
import com.easy.query.core.enums.SQLLikeEnum;
import com.easy.query.core.enums.SQLPredicateCompare;
import com.easy.query.core.enums.SQLRangeEnum;
import com.easy.query.core.expression.lambda.Property;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.parser.core.SQLWherePredicate;
import com.easy.query.core.expression.segment.condition.predicate.ColumnCollectionPredicate;
import com.easy.query.core.expression.segment.condition.predicate.ColumnExistsSubQueryPredicate;
import com.easy.query.core.expression.segment.condition.predicate.ColumnSubQueryPredicate;
import com.easy.query.core.expression.segment.condition.predicate.ColumnValuePredicate;
import com.easy.query.core.expression.segment.condition.predicate.ColumnWithColumnPredicate;
import com.easy.query.core.expression.segment.condition.predicate.FuncColumnValuePredicate;
import com.easy.query.core.enums.SQLPredicateCompareEnum;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.segment.condition.predicate.ColumnPredicate;
import com.easy.query.core.expression.sql.builder.EntityExpressionBuilder;
import com.easy.query.core.util.LambdaUtil;
import com.easy.query.core.util.SQLUtil;

import java.util.Collection;

/**
 * @Description: 默认的数据库条件比较
 * @Date: 2023/2/7 06:58
 * @author xuejiaming
 */
public class DefaultSQLPredicate<T1> implements SQLWherePredicate<T1> {
    private final int index;
    private final EntityExpressionBuilder entityExpressionBuilder;
    private final boolean reverse;
    private final PredicateSegment rootPredicateSegment;
    private final TableAvailable entityTable;
    private PredicateSegment nextPredicateSegment;

    public DefaultSQLPredicate(int index, EntityExpressionBuilder entityExpressionBuilder, PredicateSegment predicateSegment) {
        this(index,entityExpressionBuilder,predicateSegment,false);

    }
    public DefaultSQLPredicate(int index, EntityExpressionBuilder entityExpressionBuilder, PredicateSegment predicateSegment, boolean reverse) {
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
    private SQLPredicateCompare getReallyPredicateCompare(SQLPredicateCompare sqlPredicateCompare){
        return reverse?sqlPredicateCompare.toReverse():sqlPredicateCompare;
    }

    protected void appendThisPredicate(Property<T1, ?> column, Object val, SQLPredicateCompare condition) {
        String propertyName = LambdaUtil.getPropertyName(column);

        nextPredicateSegment.setPredicate(new ColumnValuePredicate(getTable(), propertyName, val, getReallyPredicateCompare(condition), entityExpressionBuilder.getRuntimeContext()));
    }
    protected void appendThisFuncPredicate(Property<T1, ?> column, ColumnFunction func, SQLPredicateCompare compare, Object val) {
        String propertyName = LambdaUtil.getPropertyName(column);

        nextPredicateSegment.setPredicate(new FuncColumnValuePredicate(getTable(),func, propertyName, val, compare, entityExpressionBuilder.getRuntimeContext()));
    }


    @Override
    public DefaultSQLPredicate<T1> eq(boolean condition, Property<T1, ?> column, Object val) {
        if (condition) {
            appendThisPredicate(column, val, SQLPredicateCompareEnum.EQ);
            next();
        }
        return this;
    }

    @Override
    public SQLWherePredicate<T1> ne(boolean condition, Property<T1, ?> column, Object val) {
        if (condition) {
            appendThisPredicate(column, val, SQLPredicateCompareEnum.NE);
            next();
        }
        return this;
    }

    @Override
    public SQLWherePredicate<T1> gt(boolean condition, Property<T1, ?> column, Object val) {
        if (condition) {
            appendThisPredicate(column, val, SQLPredicateCompareEnum.GT);
            next();
        }
        return this;
    }

    @Override
    public SQLWherePredicate<T1> ge(boolean condition, Property<T1, ?> column, Object val) {
        if (condition) {
            appendThisPredicate(column, val, SQLPredicateCompareEnum.GE);
            next();
        }
        return this;
    }

    @Override
    public SQLWherePredicate<T1> like(boolean condition, Property<T1, ?> column, Object val, SQLLikeEnum sqlLike) {
        if (condition) {
            appendThisPredicate(column, SQLUtil.getLikeParameter(val, sqlLike), SQLPredicateCompareEnum.LIKE);
            next();
        }
        return this;
    }

    @Override
    public SQLWherePredicate<T1> notLike(boolean condition, Property<T1, ?> column, Object val, SQLLikeEnum sqlLike) {
        if (condition) {
            appendThisPredicate(column, val, SQLPredicateCompareEnum.NOT_LIKE);
            next();
        }
        return this;
    }

    @Override
    public SQLWherePredicate<T1> le(boolean condition, Property<T1, ?> column, Object val) {
        if (condition) {
            appendThisPredicate(column, val, SQLPredicateCompareEnum.LE);
            next();
        }
        return this;
    }

    @Override
    public SQLWherePredicate<T1> lt(boolean condition, Property<T1, ?> column, Object val) {
        if (condition) {
            appendThisPredicate(column, val, SQLPredicateCompareEnum.LT);
            next();
        }
        return this;
    }


    @Override
    public SQLWherePredicate<T1> isNull(boolean condition, Property<T1, ?> column) {
        if (condition) {
            String propertyName = LambdaUtil.getPropertyName(column);
            nextPredicateSegment.setPredicate(new ColumnPredicate(getTable(), propertyName, getReallyPredicateCompare(SQLPredicateCompareEnum.IS_NULL), entityExpressionBuilder.getRuntimeContext()));
            next();
        }
        return this;
    }

    @Override
    public SQLWherePredicate<T1> isNotNull(boolean condition, Property<T1, ?> column) {
        if (condition) {
            String propertyName = LambdaUtil.getPropertyName(column);
            nextPredicateSegment.setPredicate(new ColumnPredicate(getTable(), propertyName, getReallyPredicateCompare(SQLPredicateCompareEnum.IS_NOT_NULL), entityExpressionBuilder.getRuntimeContext()));
            next();
        }
        return this;
    }


    @Override
    public SQLWherePredicate<T1> in(boolean condition, Property<T1, ?> column, Collection<?> collection) {
        if (condition) {
            String propertyName = LambdaUtil.getPropertyName(column);
            nextPredicateSegment.setPredicate(new ColumnCollectionPredicate(getTable(), propertyName, collection, getReallyPredicateCompare(SQLPredicateCompareEnum.IN), entityExpressionBuilder.getRuntimeContext()));
            next();
        }
        return this;
    }

    @Override
    public <TProperty> SQLWherePredicate<T1> in(boolean condition, Property<T1, TProperty> column, Queryable<TProperty> subQueryable) {
        if (condition) {
            String propertyName = LambdaUtil.getPropertyName(column);
            nextPredicateSegment.setPredicate(new ColumnSubQueryPredicate(getTable(), propertyName, subQueryable, getReallyPredicateCompare(SQLPredicateCompareEnum.IN), entityExpressionBuilder.getRuntimeContext()));
            next();
        }
        return this;
    }

    @Override
    public SQLWherePredicate<T1> notIn(boolean condition, Property<T1, ?> column, Collection<?> collection) {
        if (condition) {
            String propertyName = LambdaUtil.getPropertyName(column);
            nextPredicateSegment.setPredicate(new ColumnCollectionPredicate(getTable(), propertyName, collection, getReallyPredicateCompare(SQLPredicateCompareEnum.NOT_IN), entityExpressionBuilder.getRuntimeContext()));
            next();
        }
        return this;
    }

    @Override
    public <TProperty> SQLWherePredicate<T1> notIn(boolean condition, Property<T1, ?> column, Queryable<TProperty> subQueryable) {
        if (condition) {
            String propertyName = LambdaUtil.getPropertyName(column);
            nextPredicateSegment.setPredicate(new ColumnSubQueryPredicate(getTable(), propertyName, subQueryable, getReallyPredicateCompare(SQLPredicateCompareEnum.NOT_IN), entityExpressionBuilder.getRuntimeContext()));
            next();
        }
        return this;
    }

    @Override
    public <T2> SQLWherePredicate<T1> exists(boolean condition, Queryable<T2> subQueryable, SQLExpression1<SQLWherePredicate<T2>> whereExpression) {
        if (condition) {
            Queryable<T2> existsQueryable = subQueryable.cloneQueryable().where(whereExpression).select("1");

            nextPredicateSegment.setPredicate(new ColumnExistsSubQueryPredicate(getTable(), existsQueryable, getReallyPredicateCompare(SQLPredicateCompareEnum.EXISTS), entityExpressionBuilder.getRuntimeContext()));
            next();
        }
        return this;
    }

    @Override
    public <T2> SQLWherePredicate<T1> notExists(boolean condition, Queryable<T2> subQueryable, SQLExpression1<SQLWherePredicate<T2>> whereExpression) {
        if (condition) {
            Queryable<T2> existsQueryable = subQueryable.cloneQueryable().where(whereExpression).select("1");

            nextPredicateSegment.setPredicate(new ColumnExistsSubQueryPredicate(getTable(), existsQueryable, getReallyPredicateCompare(SQLPredicateCompareEnum.NOT_EXISTS), entityExpressionBuilder.getRuntimeContext()));
            next();
        }
        return this;
    }

    @Override
    public SQLWherePredicate<T1> range(boolean condition, Property<T1, ?> column, boolean conditionLeft, Object valLeft, boolean conditionRight, Object valRight, SQLRangeEnum sqlRange) {
        if (condition) {
            if (conditionLeft) {
                boolean openFirst = SQLRangeEnum.openFirst(sqlRange);
                appendThisPredicate(column, valLeft, getReallyPredicateCompare(openFirst ? SQLPredicateCompareEnum.GT : SQLPredicateCompareEnum.GE));
                next();
            }
            if (conditionRight) {
                boolean openEnd = SQLRangeEnum.openEnd(sqlRange);
                appendThisPredicate(column, valRight, getReallyPredicateCompare(openEnd ? SQLPredicateCompareEnum.LT : SQLPredicateCompareEnum.LE));
                next();
            }
        }
        return this;
    }

    @Override
    public SQLWherePredicate<T1> columnFunc(boolean condition, Property<T1, ?> column, ColumnFunction easyFunc, SQLPredicateCompare sqlPredicateCompare, Object val) {

        if (condition) {
            appendThisFuncPredicate(column,easyFunc, getReallyPredicateCompare(sqlPredicateCompare), val);
            next();
        }
        return this;
    }

    @Override
    public <T2> DefaultSQLPredicate<T1> eq(boolean condition, SQLWherePredicate<T2> sub, Property<T1, ?> column1, Property<T2, ?> column2) {
        if (condition) {
            TableAvailable leftTable = getTable();
            String leftPropertyName = LambdaUtil.getPropertyName(column1);
            TableAvailable rightTable = sub.getTable();
            String rightPropertyName = LambdaUtil.getPropertyName(column2);
            nextPredicateSegment.setPredicate(new ColumnWithColumnPredicate(leftTable, leftPropertyName, rightTable, rightPropertyName, getReallyPredicateCompare(SQLPredicateCompareEnum.EQ), entityExpressionBuilder.getRuntimeContext()));
            next();

        }
        return this;
    }

    @Override
    public <T2> SQLWherePredicate<T2> then(SQLWherePredicate<T2> sub) {
        if (this.nextPredicateSegment instanceof AndPredicateSegment) {
            sub.and();
        } else if (this.nextPredicateSegment instanceof OrPredicateSegment) {
            sub.or();
            and0();
        }
        return sub;
    }

    @Override
    public SQLWherePredicate<T1> and(boolean condition) {
        if (condition) {
            and0();
        }
        return this;
    }

    @Override
    public SQLWherePredicate<T1> and(boolean condition, SQLExpression1<SQLWherePredicate<T1>> sqlWherePredicateSQLExpression) {
        if (condition) {
            and0();
             SQLWherePredicate<T1> sqlPredicate = entityExpressionBuilder.getRuntimeContext().getEasyQueryLambdaFactory().createSQLPredicate(index, entityExpressionBuilder, this.nextPredicateSegment);
            sqlWherePredicateSQLExpression.apply(sqlPredicate);
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
    public SQLWherePredicate<T1> or(boolean condition) {
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
    public SQLWherePredicate<T1> or(boolean condition, SQLExpression1<SQLWherePredicate<T1>> sqlWherePredicateSQLExpression) {
        if (condition) {
            or0();
             SQLWherePredicate<T1> sqlPredicate = entityExpressionBuilder.getRuntimeContext().getEasyQueryLambdaFactory().createSQLPredicate(index, entityExpressionBuilder, this.nextPredicateSegment);
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
