package org.easy.query.core.impl.lambda;

import org.easy.query.core.abstraction.*;
import org.easy.query.core.abstraction.lambda.Property;
import org.easy.query.core.abstraction.lambda.SqlExpression;
import org.easy.query.core.enums.SqlKeywordEnum;
import org.easy.query.core.impl.SelectContext;
import org.easy.query.core.abstraction.sql.base.SqlPredicate;
import org.easy.query.core.abstraction.sql.base.WherePredicate;
import org.easy.query.core.segments.PredicateSegment0;

/**
 * @FileName: SqlWherePredicate.java
 * @Description: 文件说明
 * @Date: 2023/2/7 06:58
 * @Created by xuejiaming
 */
public class DefaultSqlPredicate<T1> implements SqlPredicate<T1> {
    private final int index;
    private final SelectContext selectContext;
    private final SqlSegment0Builder sqlSegment0Builder;

    public DefaultSqlPredicate(int index, SelectContext selectContext, SqlSegment0Builder sqlSegment0Builder) {
        this.index = index;
        this.selectContext = selectContext;
        this.sqlSegment0Builder = sqlSegment0Builder;
    }

    @Override
    public int getIndex() {
        return index;
    }


    @Override
    public DefaultSqlPredicate<T1> eq(boolean condition, Property<T1, ?> column, Object val) {
        if(condition){
            String columnName = selectContext.getTable(getIndex()).getColumnName(column);
            PredicateSegment0 predicateSegment = PredicateSegment0.createColumn2Value(index, columnName, selectContext, SqlKeywordEnum.EQ,val);
            predicateSegment.setCompareValue(val);
            sqlSegment0Builder.append(predicateSegment);
        }
        return this;
    }

    @Override
    public SqlPredicate<T1> like(boolean condition, Property<T1, ?> column, Object val) {
        if(condition){
            String columnName = selectContext.getTable(getIndex()).getColumnName(column);
            PredicateSegment0 predicateSegment = PredicateSegment0.createColumn2Value(index, columnName, selectContext, SqlKeywordEnum.LIKE,val);
            sqlSegment0Builder.append(predicateSegment);
        }
        return this;
    }

    @Override
    public <T2, TChain2> DefaultSqlPredicate<T1> eq(boolean condition, WherePredicate<T2, TChain2> sub, Property<T1, ?> column1, Property<T2, ?> column2) {
        if(condition){
            String columnName = selectContext.getTable(getIndex()).getColumnName(column1);
            String columnName2 = selectContext.getTable(sub.getIndex()).getColumnName(column2);
            PredicateSegment0 predicateSegment =  PredicateSegment0.createColumn2Column(index, columnName, selectContext, SqlKeywordEnum.EQ,sub.getIndex(),columnName2);
            sqlSegment0Builder.append(predicateSegment);
        }
        return this;
    }

    @Override
    public <T2, TChain2> WherePredicate<T2, TChain2> and(WherePredicate<T2, TChain2> sub) {
        return sub;
    }

    @Override
    public SqlPredicate<T1> and(boolean condition,SqlExpression<SqlPredicate<T1>> wherePredicateSqlExpression) {
        if(condition){
            wherePredicateSqlExpression.apply(this);
//            new DefaultSqlSegment0()
//            DefaultSqlPredicate<T1> x=null;
//            wherePredicateSqlExpression.apply(x);
//            getEasyQueryLambdaFactory().createSqlPredicate()
        }
        return this;
    }


}
