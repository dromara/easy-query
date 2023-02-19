package org.easy.query.core.impl.lambda;

import org.easy.query.core.abstraction.SqlSegment0Builder;
import org.easy.query.core.abstraction.lambda.Property;
import org.easy.query.core.impl.SelectContext;
import org.easy.query.core.abstraction.sql.base.SqlColumnAsSelector;
import org.easy.query.core.abstraction.sql.base.ColumnAsSelector;
import org.easy.query.core.segments.ColumnSegment;
import org.easy.query.core.util.LambdaUtil;

/**
 * @FileName: DefaultSqSelector.java
 * @Description: 文件说明
 * @Date: 2023/2/8 00:10
 * @Created by xuejiaming
 */
public class DefaultSqlColumnAsSelector<T1,TR> extends AbstractSqlColumnSelector<T1, SqlColumnAsSelector<T1, TR>> implements SqlColumnAsSelector<T1,TR> {


    private final SqlSegment0Builder sqlSegmentBuilder;

    public DefaultSqlColumnAsSelector(int index, SelectContext selectContext, SqlSegment0Builder sqlSegment0Builder){
        super(index,selectContext,sqlSegment0Builder);

        this.sqlSegmentBuilder = sqlSegment0Builder;
    }

    @Override
    public SqlColumnAsSelector<T1, TR> columnAs(Property<T1, ?> column, Property<TR, ?> alias) {
        String columnName = getSelectContext().getTable(getIndex()).getColumnName(column);
        String columnAsName = LambdaUtil.getAttrName(alias);
        sqlSegmentBuilder.append(new ColumnSegment(getIndex(),columnName,getSelectContext(),columnAsName));
        return this;
    }

    @Override
    public SqlColumnAsSelector<T1, TR> columnCount(Property<TR, ?> alias) {
        return null;
    }

    @Override
    public SqlColumnAsSelector<T1, TR> columnCount(Property<T1, ?> column, Property<TR, ?> alias) {
        return null;
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
