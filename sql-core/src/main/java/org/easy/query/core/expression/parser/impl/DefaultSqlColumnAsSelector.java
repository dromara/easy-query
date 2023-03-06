package org.easy.query.core.expression.parser.impl;

import org.easy.query.core.expression.segment.builder.SqlBuilderSegment;
import org.easy.query.core.expression.lambda.Property;
import org.easy.query.core.abstraction.sql.enums.EasyAggregate;
import org.easy.query.core.expression.parser.abstraction.SqlColumnAsSelector;
import org.easy.query.core.expression.parser.abstraction.internal.ColumnAsSelector;
import org.easy.query.core.query.SqlEntityExpression;
import org.easy.query.core.query.SqlEntityQueryExpression;
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


    private final SqlBuilderSegment sqlSegmentBuilder;

    public DefaultSqlColumnAsSelector(int index, SqlEntityExpression sqlEntityExpression, SqlBuilderSegment sqlSegment0Builder){
        super(index,sqlEntityExpression,sqlSegment0Builder);

        this.sqlSegmentBuilder = sqlSegment0Builder;
    }

    @Override
    public SqlColumnAsSelector<T1, TR> columnAs(Property<T1, ?> column, Property<TR, ?> alias) {
        SqlEntityTableExpression table = getSqlEntityExpression().getTable(getIndex());
        String propertyName = table.getPropertyName(column);
        String columnAsName = LambdaUtil.getAttrName(alias);
        sqlSegmentBuilder.append(new ColumnSegment(table,propertyName, getSqlEntityExpression(),columnAsName));
        return this;
    }

    @Override
    public SqlColumnAsSelector<T1, TR> columnCount(Property<T1, ?> column) {
        return this;
    }

    @Override
    public SqlColumnAsSelector<T1, TR> columnCount(Property<T1, ?> column, Property<TR, ?> alias) {
        SqlEntityTableExpression table = getSqlEntityExpression().getTable(getIndex());
        String propertyName = table.getPropertyName(column);
        String columnAsName = LambdaUtil.getAttrName(alias);
        sqlSegmentBuilder.append(new FuncColumnSegment(table,propertyName, getSqlEntityExpression(), EasyAggregate.COUNT,columnAsName));
        return this;
    }

    @Override
    public SqlColumnAsSelector<T1, TR> columnDistinctCount(Property<T1, ?> column) {
        return null;
    }

    @Override
    public SqlColumnAsSelector<T1, TR> columnDistinctCount(Property<T1, ?> column, Property<TR, ?> alias) {
        return null;
    }

    @Override
    public SqlColumnAsSelector<T1, TR> columnSum(Property<T1, ?> column) {
        return null;
    }

    @Override
    public SqlColumnAsSelector<T1, TR> columnSum(Property<T1, ?> column, Property<TR, ?> alias) {
        return null;
    }

    @Override
    public SqlColumnAsSelector<T1, TR> columnMax(Property<T1, ?> column) {
        return null;
    }

    @Override
    public SqlColumnAsSelector<T1, TR> columnMax(Property<T1, ?> column, Property<TR, ?> alias) {
        return null;
    }

    @Override
    public SqlColumnAsSelector<T1, TR> columnMin(Property<T1, ?> column) {
        return null;
    }

    @Override
    public SqlColumnAsSelector<T1, TR> columnMin(Property<T1, ?> column, Property<TR, ?> alias) {
        return null;
    }

    @Override
    public SqlColumnAsSelector<T1, TR> columnAvg(Property<T1, ?> column) {
        return null;
    }

    @Override
    public SqlColumnAsSelector<T1, TR> columnAvg(Property<T1, ?> column, Property<TR, ?> alias) {
        return null;
    }

    @Override
    public SqlColumnAsSelector<T1, TR> columnLen(Property<T1, ?> column) {
        return null;
    }

    @Override
    public SqlColumnAsSelector<T1, TR> columnLen(Property<T1, ?> column, Property<TR, ?> alias) {
        return null;
    }

    @Override
    public <T2, TChain2> ColumnAsSelector<T2, TR, TChain2> then(ColumnAsSelector<T2, TR, TChain2> sub) {
        return sub;
    }

}
