package com.easy.query.core.expression.parser.impl;

import com.easy.query.core.abstraction.metadata.ColumnMetadata;
import com.easy.query.core.abstraction.metadata.EntityMetadata;
import com.easy.query.core.enums.EasyAggregate;
import com.easy.query.core.expression.lambda.Property;
import com.easy.query.core.expression.parser.abstraction.SqlColumnAsSelector;
import com.easy.query.core.expression.parser.abstraction.internal.ColumnAsSelector;
import com.easy.query.core.expression.segment.ColumnSegment;
import com.easy.query.core.expression.segment.FuncColumnSegment;
import com.easy.query.core.expression.segment.builder.SqlBuilderSegment;
import com.easy.query.core.query.AnonymousEntityTableExpression;
import com.easy.query.core.query.SqlEntityExpression;
import com.easy.query.core.query.SqlEntityTableExpression;
import com.easy.query.core.util.LambdaUtil;

import java.util.Collection;
import java.util.LinkedHashMap;

/**
 * @FileName: DefaultSqSelector.java
 * @Description: 文件说明
 * @Date: 2023/2/8 00:10
 * @Created by xuejiaming
 */
public class DefaultAutoSqlColumnAsSelector<T1, TR> extends AbstractSqlColumnSelector<T1, SqlColumnAsSelector<T1, TR>> implements SqlColumnAsSelector<T1, TR> {

    private final Class<TR> resultClass;

    public DefaultAutoSqlColumnAsSelector(int index, SqlEntityExpression sqlEntityExpression, SqlBuilderSegment sqlSegment0Builder, Class<TR> resultClass) {
        super(index, sqlEntityExpression, sqlSegment0Builder);
        this.resultClass = resultClass;
    }

    @Override
    public SqlColumnAsSelector<T1, TR> columnAs(Property<T1, ?> column, Property<TR, ?> alias) {
        throw new UnsupportedOperationException();
    }

    @Override
    public SqlColumnAsSelector<T1, TR> columnAll() {

        SqlEntityTableExpression table = sqlEntityExpression.getTable(getIndex());
        if (table.entityClass().equals(resultClass)) {
            return super.columnAll();
        } else {
            return columnAll(table);
        }
    }

    private SqlColumnAsSelector<T1, TR> columnAll(SqlEntityTableExpression table) {
        if (table instanceof AnonymousEntityTableExpression) {
            columnAnonymousAll((AnonymousEntityTableExpression) table);
        } else {
            //只查询当前对象返回结果属性名称匹配
            EntityMetadata targetEntityMetadata = sqlEntityExpression.getRuntimeContext().getEntityMetadataManager().getEntityMetadata(resultClass);
            EntityMetadata sourceEntityMetadata = table.getEntityMetadata();

            Collection<String> sourceProperties = sourceEntityMetadata.getProperties();
            for (String property : sourceProperties) {

                ColumnMetadata sourceColumnMetadata = sourceEntityMetadata.getColumnNotNull(property);
                String sourceColumnName = sourceColumnMetadata.getName();
                String targetPropertyName = targetEntityMetadata.getPropertyName(sourceColumnName, null);
                if (targetPropertyName != null) {

                    ColumnMetadata targetColumnMetadata = targetEntityMetadata.getColumnNotNull(targetPropertyName);
                    if (targetColumnMetadata != null) {
                        String alias = targetColumnMetadata.getName();
                        sqlSegmentBuilder.append(new ColumnSegment(table, property, sqlEntityExpression, alias));
                    }
                }

            }
        }
        return this;
    }

    @Override
    public SqlColumnAsSelector<T1, TR> columnCount(Property<T1, ?> column) {
        throw new UnsupportedOperationException();
    }

    @Override
    public SqlColumnAsSelector<T1, TR> columnCount(Property<T1, ?> column, Property<TR, ?> alias) {
        throw new UnsupportedOperationException();
    }

    @Override
    public SqlColumnAsSelector<T1, TR> columnDistinctCount(Property<T1, ?> column) {
        throw new UnsupportedOperationException();
    }

    @Override
    public SqlColumnAsSelector<T1, TR> columnDistinctCount(Property<T1, ?> column, Property<TR, ?> alias) {
        throw new UnsupportedOperationException();
    }

    @Override
    public SqlColumnAsSelector<T1, TR> columnSum(Property<T1, ?> column) {
        throw new UnsupportedOperationException();
    }

    @Override
    public SqlColumnAsSelector<T1, TR> columnSum(Property<T1, ?> column, Property<TR, ?> alias) {
        throw new UnsupportedOperationException();
    }

    @Override
    public SqlColumnAsSelector<T1, TR> columnMax(Property<T1, ?> column) {
        throw new UnsupportedOperationException();
    }

    @Override
    public SqlColumnAsSelector<T1, TR> columnMax(Property<T1, ?> column, Property<TR, ?> alias) {
        throw new UnsupportedOperationException();
    }

    @Override
    public SqlColumnAsSelector<T1, TR> columnMin(Property<T1, ?> column) {
        throw new UnsupportedOperationException();
    }

    @Override
    public SqlColumnAsSelector<T1, TR> columnMin(Property<T1, ?> column, Property<TR, ?> alias) {
        throw new UnsupportedOperationException();
    }

    @Override
    public SqlColumnAsSelector<T1, TR> columnAvg(Property<T1, ?> column) {
        throw new UnsupportedOperationException();
    }

    @Override
    public SqlColumnAsSelector<T1, TR> columnAvg(Property<T1, ?> column, Property<TR, ?> alias) {
        throw new UnsupportedOperationException();
    }

    @Override
    public SqlColumnAsSelector<T1, TR> columnLen(Property<T1, ?> column) {
        throw new UnsupportedOperationException();
    }

    @Override
    public SqlColumnAsSelector<T1, TR> columnLen(Property<T1, ?> column, Property<TR, ?> alias) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <T2, TChain2> ColumnAsSelector<T2, TR, TChain2> then(ColumnAsSelector<T2, TR, TChain2> sub) {
        throw new UnsupportedOperationException();
    }

}
