package org.easy.query.core.impl.lambda;

import org.easy.query.core.abstraction.SqlSegment;
import org.easy.query.core.abstraction.lambda.Property;
import org.easy.query.core.impl.SelectContext;
import org.easy.query.core.query.builder.SelectTableInfo;
import org.easy.query.core.util.LambdaUtil;
import org.easy.query.core.abstraction.sql.base.SqlPredicate;
import org.easy.query.core.abstraction.sql.base.WherePredicate;

/**
 * @FileName: SqlWherePredicate.java
 * @Description: 文件说明
 * @Date: 2023/2/7 06:58
 * @Created by xuejiaming
 */
public class DefaultSqlPredicate<T1> extends PredicateBuilder implements SqlPredicate<T1> {
    private final int index;
    private final SelectContext selectContext;

    public DefaultSqlPredicate(int index, SelectContext selectContext, SqlSegment sqlSegment) {
        super(selectContext,sqlSegment);
        this.index = index;
        this.selectContext = selectContext;
    }

    @Override
    public int index() {
        return index;
    }


    @Override
    public DefaultSqlPredicate<T1> eq(boolean condition, Property<T1, ?> column, Object val) {
        SelectTableInfo table = selectContext.getTable(index());
        String attrName = LambdaUtil.getAttrName(column);
        String columnName = table.getColumnName(attrName);
        this.appendAndSql(table.getAlias(),columnName,val,"=");
        return this;
    }

    @Override
    public SqlPredicate<T1> like(boolean condition, Property<T1, ?> column, Object val) {
        SelectTableInfo table = selectContext.getTable(index());
        String attrName = LambdaUtil.getAttrName(column);
        String columnName = table.getColumnName(attrName);
        this.appendAndSql(table.getAlias(),columnName,val,"like");
        return this;
    }

    @Override
    public <T2, TChain2> DefaultSqlPredicate<T1> eq(boolean condition, WherePredicate<T2, TChain2> sub, Property<T1, ?> column1, Property<T2, ?> column2) {

        SelectTableInfo table = selectContext.getTable(index());
        String attrName1 = LambdaUtil.getAttrName(column1);
        String columnName = table.getColumnName(attrName1);

        SelectTableInfo table2 = selectContext.getTable(sub.index());
        String attrName2 = LambdaUtil.getAttrName(column2);
        String columnName2 = table2.getColumnName(attrName2);
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

}
