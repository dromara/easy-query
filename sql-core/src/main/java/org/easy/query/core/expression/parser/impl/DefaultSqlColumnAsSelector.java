package org.easy.query.core.expression.parser.impl;

import org.easy.query.core.expression.segment.builder.SqlBuilderSegment;
import org.easy.query.core.expression.lambda.Property;
import org.easy.query.core.abstraction.sql.enums.EasyAggregate;
import org.easy.query.core.expression.parser.abstraction.SqlColumnAsSelector;
import org.easy.query.core.expression.parser.abstraction.internal.ColumnAsSelector;
import org.easy.query.core.query.SqlEntityExpression;
import org.easy.query.core.query.SqlEntityTableExpression;
import org.easy.query.core.expression.segment.ColumnSegment;
import org.easy.query.core.expression.segment.FuncColumnSegment;
import org.easy.query.core.util.LambdaUtil;

/**
 * @FileName: DefaultSqSelector.java
 * @Description: 文件说明
 * @Date: 2023/2/8 00:10
 * @Created by xuejiaming
 */
public class DefaultSqlColumnAsSelector<T1,TR> extends AbstractSqlColumnSelector<T1, SqlColumnAsSelector<T1, TR>> implements SqlColumnAsSelector<T1,TR> {

    public DefaultSqlColumnAsSelector(int index, SqlEntityExpression sqlEntityExpression, SqlBuilderSegment sqlSegment0Builder){
        super(index,sqlEntityExpression,sqlSegment0Builder);
    }

    @Override
    public SqlColumnAsSelector<T1, TR> columnAs(Property<T1, ?> column, Property<TR, ?> alias) {
        SqlEntityTableExpression table = sqlEntityExpression.getTable(getIndex());
        String propertyName = table.getPropertyName(column);
        String columnAsName = LambdaUtil.getPropertyName(alias);
        sqlSegmentBuilder.append(new ColumnSegment(table,propertyName, sqlEntityExpression,columnAsName));
        return this;
    }
    private void doColumnFunc(Property<T1, ?> column, Property<TR, ?> alias,EasyAggregate aggregate){
        SqlEntityTableExpression table = sqlEntityExpression.getTable(getIndex());
        String propertyName = table.getPropertyName(column);
        String columnAsName = alias==null?table.getColumnName(propertyName):LambdaUtil.getPropertyName(alias);
        sqlSegmentBuilder.append(new FuncColumnSegment(table,propertyName,sqlEntityExpression, aggregate,columnAsName));
    }

    @Override
    public SqlColumnAsSelector<T1, TR> columnCount(Property<T1, ?> column) {
        doColumnFunc(column,null,EasyAggregate.COUNT);
        return this;
    }

    @Override
    public SqlColumnAsSelector<T1, TR> columnCount(Property<T1, ?> column, Property<TR, ?> alias) {
        doColumnFunc(column,alias,EasyAggregate.COUNT);
        return this;
    }

    @Override
    public SqlColumnAsSelector<T1, TR> columnDistinctCount(Property<T1, ?> column) {
        doColumnFunc(column,null,EasyAggregate.COUNT_DISTINCT);
        return this;
    }

    @Override
    public SqlColumnAsSelector<T1, TR> columnDistinctCount(Property<T1, ?> column, Property<TR, ?> alias) {
        doColumnFunc(column,alias,EasyAggregate.COUNT_DISTINCT);
        return this;
    }

    @Override
    public SqlColumnAsSelector<T1, TR> columnSum(Property<T1, ?> column) {
        doColumnFunc(column,null,EasyAggregate.SUM);
        return this;
    }

    @Override
    public SqlColumnAsSelector<T1, TR> columnSum(Property<T1, ?> column, Property<TR, ?> alias) {
        doColumnFunc(column,alias,EasyAggregate.SUM);
        return this;
    }

    @Override
    public SqlColumnAsSelector<T1, TR> columnMax(Property<T1, ?> column) {
        doColumnFunc(column,null,EasyAggregate.MAX);
        return this;
    }

    @Override
    public SqlColumnAsSelector<T1, TR> columnMax(Property<T1, ?> column, Property<TR, ?> alias) {
        doColumnFunc(column,alias,EasyAggregate.MAX);
        return this;
    }

    @Override
    public SqlColumnAsSelector<T1, TR> columnMin(Property<T1, ?> column) {
        doColumnFunc(column,null,EasyAggregate.MIN);
        return this;
    }

    @Override
    public SqlColumnAsSelector<T1, TR> columnMin(Property<T1, ?> column, Property<TR, ?> alias) {
        doColumnFunc(column,alias,EasyAggregate.MIN);
        return this;
    }

    @Override
    public SqlColumnAsSelector<T1, TR> columnAvg(Property<T1, ?> column) {
        doColumnFunc(column,null,EasyAggregate.AVG);
        return this;
    }

    @Override
    public SqlColumnAsSelector<T1, TR> columnAvg(Property<T1, ?> column, Property<TR, ?> alias) {
        doColumnFunc(column,alias,EasyAggregate.AVG);
        return this;
    }

    @Override
    public SqlColumnAsSelector<T1, TR> columnLen(Property<T1, ?> column) {
        doColumnFunc(column,null,EasyAggregate.LEN);
        return this;
    }

    @Override
    public SqlColumnAsSelector<T1, TR> columnLen(Property<T1, ?> column, Property<TR, ?> alias) {
        doColumnFunc(column,alias,EasyAggregate.LEN);
        return this;
    }

    @Override
    public <T2, TChain2> ColumnAsSelector<T2, TR, TChain2> then(ColumnAsSelector<T2, TR, TChain2> sub) {
        return sub;
    }

}
