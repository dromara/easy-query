package com.easy.query.core.expression.parser.impl;

import com.easy.query.core.abstraction.metadata.ColumnMetadata;
import com.easy.query.core.abstraction.metadata.EntityMetadata;
import com.easy.query.core.enums.EasyAggregate;
import com.easy.query.core.exception.EasyQueryException;
import com.easy.query.core.expression.lambda.Property;
import com.easy.query.core.expression.parser.abstraction.SqlColumnAsSelector;
import com.easy.query.core.expression.parser.abstraction.internal.ColumnAsSelector;
import com.easy.query.core.expression.segment.ColumnSegment;
import com.easy.query.core.expression.segment.FuncColumnSegment;
import com.easy.query.core.expression.segment.SqlEntityAliasSegment;
import com.easy.query.core.expression.segment.SqlSegment;
import com.easy.query.core.expression.segment.builder.SqlBuilderSegment;
import com.easy.query.core.query.AnonymousEntityTableExpression;
import com.easy.query.core.query.SqlEntityExpression;
import com.easy.query.core.query.SqlEntityQueryExpression;
import com.easy.query.core.query.SqlEntityTableExpression;
import com.easy.query.core.util.ClassUtil;
import com.easy.query.core.util.EasyUtil;
import com.easy.query.core.util.LambdaUtil;

import java.util.Collection;
import java.util.List;

/**
 * @FileName: DefaultSqSelector.java
 * @Description: 文件说明
 * @Date: 2023/2/8 00:10
 * @Created by xuejiaming
 */
public class DefaultSqlColumnAsSelector<T1, TR> extends AbstractSqlColumnSelector<T1, SqlColumnAsSelector<T1, TR>> implements SqlColumnAsSelector<T1, TR> {

    private final Class<TR> resultClass;

    public DefaultSqlColumnAsSelector(int index, SqlEntityExpression sqlEntityExpression, SqlBuilderSegment sqlSegment0Builder, Class<TR> resultClass) {
        super(index, sqlEntityExpression, sqlSegment0Builder);
        this.resultClass = resultClass;
    }

    @Override
    public SqlColumnAsSelector<T1, TR> columnAs(Property<T1, ?> column, Property<TR, ?> alias) {
        SqlEntityTableExpression table = sqlEntityExpression.getTable(getIndex());
        String propertyName = table.getPropertyName(column);
        String columnAsName = LambdaUtil.getPropertyName(alias);
        sqlSegmentBuilder.append(new ColumnSegment(table, propertyName, sqlEntityExpression, columnAsName));
        return this;
    }

    @Override
    public SqlColumnAsSelector<T1, TR> columnAll() {

        SqlEntityTableExpression table = sqlEntityExpression.getTable(getIndex());
        if (table instanceof AnonymousEntityTableExpression) {
            SqlEntityQueryExpression sqlEntityQueryExpression = ((AnonymousEntityTableExpression) table).getSqlEntityQueryExpression();
            List<SqlSegment> sqlSegments = sqlEntityQueryExpression.getProjects().getSqlSegments();
            for (SqlSegment sqlSegment : sqlSegments) {
                if (sqlSegment instanceof SqlEntityAliasSegment) {
                    String columnName = EasyUtil.getAnonymousColumnName((SqlEntityAliasSegment) sqlSegment);
                    sqlSegmentBuilder.append(new ColumnSegment(table, columnName, sqlEntityExpression));
                } else {
                    throw new EasyQueryException("columnAll函数无法获取指定列" + ClassUtil.getInstanceSimpleName(sqlSegment));
                }
            }
        } else {
            EntityMetadata entityMetadata = sqlEntityExpression.getRuntimeContext().getEntityMetadataManager().getEntityMetadata(resultClass);
            Collection<String> properties = table.getEntityMetadata().getProperties();
            for (String property : properties) {
                ColumnMetadata columnOrNull = entityMetadata.getColumnOrNull(property);
                String alias = columnOrNull == null ? null :columnOrNull.getName();
                sqlSegmentBuilder.append(new ColumnSegment(table, property, sqlEntityExpression,alias));
            }
        }
        return this;
    }

    private void doColumnFunc(Property<T1, ?> column, Property<TR, ?> alias, EasyAggregate aggregate) {
        SqlEntityTableExpression table = sqlEntityExpression.getTable(getIndex());
        String propertyName = table.getPropertyName(column);
        String columnAsName = alias == null ? table.getColumnName(propertyName) : LambdaUtil.getPropertyName(alias);
        sqlSegmentBuilder.append(new FuncColumnSegment(table, propertyName, sqlEntityExpression, aggregate, columnAsName));
    }

    @Override
    public SqlColumnAsSelector<T1, TR> columnCount(Property<T1, ?> column) {
        doColumnFunc(column, null, EasyAggregate.COUNT);
        return this;
    }

    @Override
    public SqlColumnAsSelector<T1, TR> columnCount(Property<T1, ?> column, Property<TR, ?> alias) {
        doColumnFunc(column, alias, EasyAggregate.COUNT);
        return this;
    }

    @Override
    public SqlColumnAsSelector<T1, TR> columnDistinctCount(Property<T1, ?> column) {
        doColumnFunc(column, null, EasyAggregate.COUNT_DISTINCT);
        return this;
    }

    @Override
    public SqlColumnAsSelector<T1, TR> columnDistinctCount(Property<T1, ?> column, Property<TR, ?> alias) {
        doColumnFunc(column, alias, EasyAggregate.COUNT_DISTINCT);
        return this;
    }

    @Override
    public SqlColumnAsSelector<T1, TR> columnSum(Property<T1, ?> column) {
        doColumnFunc(column, null, EasyAggregate.SUM);
        return this;
    }

    @Override
    public SqlColumnAsSelector<T1, TR> columnSum(Property<T1, ?> column, Property<TR, ?> alias) {
        doColumnFunc(column, alias, EasyAggregate.SUM);
        return this;
    }

    @Override
    public SqlColumnAsSelector<T1, TR> columnMax(Property<T1, ?> column) {
        doColumnFunc(column, null, EasyAggregate.MAX);
        return this;
    }

    @Override
    public SqlColumnAsSelector<T1, TR> columnMax(Property<T1, ?> column, Property<TR, ?> alias) {
        doColumnFunc(column, alias, EasyAggregate.MAX);
        return this;
    }

    @Override
    public SqlColumnAsSelector<T1, TR> columnMin(Property<T1, ?> column) {
        doColumnFunc(column, null, EasyAggregate.MIN);
        return this;
    }

    @Override
    public SqlColumnAsSelector<T1, TR> columnMin(Property<T1, ?> column, Property<TR, ?> alias) {
        doColumnFunc(column, alias, EasyAggregate.MIN);
        return this;
    }

    @Override
    public SqlColumnAsSelector<T1, TR> columnAvg(Property<T1, ?> column) {
        doColumnFunc(column, null, EasyAggregate.AVG);
        return this;
    }

    @Override
    public SqlColumnAsSelector<T1, TR> columnAvg(Property<T1, ?> column, Property<TR, ?> alias) {
        doColumnFunc(column, alias, EasyAggregate.AVG);
        return this;
    }

    @Override
    public SqlColumnAsSelector<T1, TR> columnLen(Property<T1, ?> column) {
        doColumnFunc(column, null, EasyAggregate.LEN);
        return this;
    }

    @Override
    public SqlColumnAsSelector<T1, TR> columnLen(Property<T1, ?> column, Property<TR, ?> alias) {
        doColumnFunc(column, alias, EasyAggregate.LEN);
        return this;
    }

    @Override
    public <T2, TChain2> ColumnAsSelector<T2, TR, TChain2> then(ColumnAsSelector<T2, TR, TChain2> sub) {
        return sub;
    }

}
