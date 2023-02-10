package org.jdqc.sql.core.impl.lambda;

import org.jdqc.sql.core.abstraction.lambda.Property;
import org.jdqc.sql.core.abstraction.sql.base.SqlPredicate;
import org.jdqc.sql.core.abstraction.sql.base.WherePredicate;
import org.jdqc.sql.core.abstraction.sql.enums.PredicateModeEnum;
import org.jdqc.sql.core.exception.JDQCException;
import org.jdqc.sql.core.query.builder.SelectTableInfo;
import org.jdqc.sql.core.impl.SelectContext;

/**
 * @FileName: SqlWherePredicate.java
 * @Description: 文件说明
 * @Date: 2023/2/7 06:58
 * @Created by xuejiaming
 */
public class DefaultSqlPredicate<T1> extends PredicateBuilder implements SqlPredicate<T1> {
    private final int index;
    private final SelectContext selectContext;


    private PredicateModeEnum predicateMode;

    public DefaultSqlPredicate(int index, SelectContext selectContext, PredicateModeEnum predicateMode) {
        super(selectContext);
        this.index = index;
        this.selectContext = selectContext;
        this.predicateMode = predicateMode;
    }

    @Override
    public int index() {
        return index;
    }


    @Override
    public DefaultSqlPredicate<T1> eq(boolean condition, Property<T1, ?> column, Object val) {
        SelectTableInfo table = selectContext.getTable(index());
        String columnName = table.getTable().getColumnName(column);
        this.appendAndSql(table.getAlias(),columnName,val,"=");
        return this;
    }

    @Override
    public SqlPredicate<T1> like(boolean condition, Property<T1, ?> column, Object val) {
        SelectTableInfo table = selectContext.getTable(index());
        String columnName = table.getTable().getColumnName(column);
        this.appendAndSql(table.getAlias(),columnName,val,"like");
        return this;
    }

    @Override
    public <T2, TChain2> DefaultSqlPredicate<T1> eq(boolean condition, WherePredicate<T2, TChain2> sub, Property<T1, ?> column1, Property<T2, ?> column2) {

        SelectTableInfo table = selectContext.getTable(index());
        String columnName = table.getTable().getColumnName(column1);

        SelectTableInfo table2 = selectContext.getTable(sub.index());
        String columnName2 = table2.getTable().getColumnName(column2);
        this.appendAndOnSql(table.getAlias(),columnName,table2.getAlias(),columnName2,"=");
        return this;
    }

    @Override
    public <T2, TChain2> WherePredicate<T2, TChain2> and(WherePredicate<T2, TChain2> sub) {
        return sub;
    }

    @Override
    public DefaultSqlPredicate<T1> and() {
        return null;
    }

    @Override
    public StringBuilder getSql() {
       if(PredicateModeEnum.WHERE_PREDICATE.equals(predicateMode)){
           return this.selectContext.getWhere();
       }else if(PredicateModeEnum.ON_PREDICATE.equals(predicateMode)){
           return this.selectContext.getCurrentPredicateTable().getOn();
       }
       throw new JDQCException("cant get predicate sql");
    }
    public void setPredicateMode(PredicateModeEnum predicateMode) {
        this.predicateMode = predicateMode;
    }
}
