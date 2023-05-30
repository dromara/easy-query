package com.easy.query.core.expression.parser.impl;

import com.easy.query.core.basic.api.select.Queryable;
import com.easy.query.core.enums.EasyBehaviorEnum;
import com.easy.query.core.expression.func.ColumnPropertyFunction;
import com.easy.query.core.expression.parser.core.SQLWherePredicate;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.segment.ColumnSegment;
import com.easy.query.core.expression.sql.builder.AnonymousEntityTableExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityQueryExpressionBuilder;
import com.easy.query.core.metadata.ColumnMetadata;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.expression.lambda.Property;
import com.easy.query.core.expression.parser.core.SQLColumnAsSelector;
import com.easy.query.core.expression.segment.impl.ColumnSegmentImpl;
import com.easy.query.core.expression.segment.builder.SQLBuilderSegment;
import com.easy.query.core.expression.sql.builder.EntityTableExpressionBuilder;

import java.util.Collection;
import java.util.Objects;
import java.util.function.Function;

/**
 * @FileName: DefaultSqSelector.java
 * @Description: 文件说明
 * @Date: 2023/2/8 00:10
 * @author xuejiaming
 */
public class DefaultAutoSQLColumnAsSelector<T1, TR> extends AbstractSQLColumnSelector<T1, SQLColumnAsSelector<T1, TR>> implements SQLColumnAsSelector<T1, TR> {

    protected final Class<TR> resultClass;

    public DefaultAutoSQLColumnAsSelector(int index, EntityQueryExpressionBuilder entityQueryExpressionBuilder, SQLBuilderSegment sqlSegment0Builder, Class<TR> resultClass) {
        super(index, entityQueryExpressionBuilder, sqlSegment0Builder);
        this.resultClass = resultClass;
    }

    @Override
    public SQLColumnAsSelector<T1, TR> columnAs(Property<T1, ?> column, Property<TR, ?> alias) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <TSubQuery> SQLColumnAsSelector<T1, TR> columnSubQueryAs(Function<SQLWherePredicate<T1>,Queryable<TSubQuery>> subQueryableFunc, Property<TR, TSubQuery> alias) {
        throw new UnsupportedOperationException();
    }

    @Override
    public TableAvailable getTable() {
        return table;
    }

    @Override
    public SQLColumnAsSelector<T1, TR> columnAll() {

        EntityTableExpressionBuilder table = entityQueryExpressionBuilder.getTable(index);
        if (table.getEntityClass().equals(resultClass)) {
            super.columnAll();
            return this;
        } else {
            return columnAll(table);
        }
    }

    private SQLColumnAsSelector<T1, TR> columnAll(EntityTableExpressionBuilder table) {
        if (table instanceof AnonymousEntityTableExpressionBuilder) {
            columnAnonymousAll((AnonymousEntityTableExpressionBuilder) table);
        } else {
            //只查询当前对象返回结果属性名称匹配
            boolean queryLargeColumn = entityQueryExpressionBuilder.getExpressionContext().getBehavior().hasBehavior(EasyBehaviorEnum.QUERY_LARGE_COLUMN);
            EntityMetadata targetEntityMetadata = entityQueryExpressionBuilder.getRuntimeContext().getEntityMetadataManager().getEntityMetadata(resultClass);
            EntityMetadata sourceEntityMetadata = table.getEntityMetadata();

            Collection<String> sourceProperties = sourceEntityMetadata.getProperties();
            for (String property : sourceProperties) {

                ColumnMetadata sourceColumnMetadata = sourceEntityMetadata.getColumnNotNull(property);
                if(!columnAllQueryLargeColumn(queryLargeColumn,sourceColumnMetadata)){
                    continue;
                }
                String sourceColumnName = sourceColumnMetadata.getName();
                String targetPropertyName = targetEntityMetadata.getPropertyNameOrNull(sourceColumnName, null);
                if (targetPropertyName != null) {

                    ColumnMetadata targetColumnMetadata = targetEntityMetadata.getColumnOrNull(targetPropertyName);
                    if (targetColumnMetadata != null) {

                        if(!columnAllQueryLargeColumn(queryLargeColumn,targetColumnMetadata)){
                            continue;
                        }
                        String targetColumnName = targetColumnMetadata.getName();
                        //如果当前属性和查询对象属性一致那么就返回对应的列名，对应的列名如果不一样就返回对应返回结果对象的属性上的列名
                        String alias = Objects.equals(sourceColumnName, targetColumnName) ? null : targetColumnName;
                        ColumnSegment columnSegment = sqlSegmentFactory.createColumnSegment(table.getEntityTable(), property, entityQueryExpressionBuilder.getRuntimeContext(), alias);
                        sqlSegmentBuilder.append(columnSegment);
                    }
                }

            }
        }
        return this;
    }

    @Override
    public SQLColumnAsSelector<T1, TR> columnFuncAs(ColumnPropertyFunction columnPropertyFunction, Property<TR, ?> alias) {
        throw new UnsupportedOperationException();
    }
}
