package org.easy.query.core.basic.expression.parser.impl;

import org.easy.query.core.basic.sql.segment.builder.SqlSegmentBuilder;
import org.easy.query.core.basic.expression.lambda.Property;
import org.easy.query.core.abstraction.sql.enums.EasyAggregate;
import org.easy.query.core.basic.expression.parser.abstraction.SqlColumnAsSelector;
import org.easy.query.core.basic.expression.parser.abstraction.internal.ColumnAsSelector;
import org.easy.query.core.impl.SqlContext;
import org.easy.query.core.query.builder.SqlTableInfo;
import org.easy.query.core.basic.sql.segment.segment.ColumnSegment;
import org.easy.query.core.basic.sql.segment.segment.FuncColumnSegment;
import org.easy.query.core.util.LambdaUtil;

/**
 * @FileName: DefaultSqSelector.java
 * @Description: 文件说明
 * @Date: 2023/2/8 00:10
 * @Created by xuejiaming
 */
public class DefaultSqlColumnAsSelector<T1,TR> extends AbstractSqlColumnSelector<T1, SqlColumnAsSelector<T1, TR>> implements SqlColumnAsSelector<T1,TR> {


    private final SqlSegmentBuilder sqlSegmentBuilder;

    public DefaultSqlColumnAsSelector(int index, SqlContext sqlContext, SqlSegmentBuilder sqlSegment0Builder){
        super(index,sqlContext,sqlSegment0Builder);

        this.sqlSegmentBuilder = sqlSegment0Builder;
    }

    @Override
    public SqlColumnAsSelector<T1, TR> columnAs(Property<T1, ?> column, Property<TR, ?> alias) {
        SqlTableInfo table = getSqlContext().getTable(getIndex());
        String propertyName = table.getPropertyName(column);
        String columnName = table.getColumnName(propertyName);
        String columnAsName = LambdaUtil.getAttrName(alias);
        sqlSegmentBuilder.append(new ColumnSegment(getIndex(),columnName,propertyName, getSqlContext(),columnAsName));
        return this;
    }

    @Override
    public SqlColumnAsSelector<T1, TR> columnCount(Property<TR, ?> alias) {
        return this;
    }

    @Override
    public SqlColumnAsSelector<T1, TR> columnCount(Property<T1, ?> column, Property<TR, ?> alias) {
        String columnName = getSqlContext().getTable(getIndex()).getColumnName(column);
        String columnAsName = LambdaUtil.getAttrName(alias);
        sqlSegmentBuilder.append(new FuncColumnSegment(getIndex(),columnName, getSqlContext(), EasyAggregate.COUNT,columnAsName));
        return this;
    }

    @Override
    public SqlColumnAsSelector<T1, TR> columnDistinctCount(Property<T1, ?> column, Property<TR, ?> alias) {
        return null;
    }

    @Override
    public SqlColumnAsSelector<T1, TR> columnSum(Property<T1, ?> column, Property<TR, ?> alias) {
        return null;
    }

    @Override
    public SqlColumnAsSelector<T1, TR> columnMax(Property<T1, ?> column, Property<TR, ?> alias) {
        return null;
    }

    @Override
    public SqlColumnAsSelector<T1, TR> columnMin(Property<T1, ?> column, Property<TR, ?> alias) {
        return null;
    }

    @Override
    public SqlColumnAsSelector<T1, TR> columnAvg(Property<T1, ?> column, Property<TR, ?> alias) {
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
