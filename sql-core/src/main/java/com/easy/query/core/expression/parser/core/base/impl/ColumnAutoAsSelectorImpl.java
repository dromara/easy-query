package com.easy.query.core.expression.parser.core.base.impl;

import com.easy.query.core.basic.api.select.Query;
import com.easy.query.core.enums.EasyBehaviorEnum;
import com.easy.query.core.expression.func.ColumnPropertyFunction;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.parser.core.base.ColumnAsSelector;
import com.easy.query.core.expression.parser.core.base.WherePredicate;
import com.easy.query.core.expression.segment.ColumnSegment;
import com.easy.query.core.expression.segment.builder.SQLBuilderSegment;
import com.easy.query.core.expression.sql.builder.AnonymousEntityTableExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityQueryExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityTableExpressionBuilder;
import com.easy.query.core.metadata.ColumnMetadata;
import com.easy.query.core.metadata.EntityMetadata;

import java.util.Collection;
import java.util.Objects;
import java.util.function.Function;

/**
 * @author xuejiaming
 * @FileName: DefaultSqSelector.java
 * @Description: 文件说明
 * @Date: 2023/2/8 00:10
 */
public class ColumnAutoAsSelectorImpl<T1, TR> extends AbstractColumnSelector<T1, ColumnAsSelector<T1, TR>> implements ColumnAsSelector<T1, TR> {

    protected final Class<TR> resultClass;

    public ColumnAutoAsSelectorImpl(int index, EntityQueryExpressionBuilder entityQueryExpressionBuilder, SQLBuilderSegment sqlSegment0Builder, Class<TR> resultClass) {
        super(index, entityQueryExpressionBuilder, sqlSegment0Builder);
        this.resultClass = resultClass;
    }

    @Override
    public ColumnAsSelector<T1, TR> columnAs(String property, String propertyAlias) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <TSubQuery> ColumnAsSelector<T1, TR> columnSubQueryAs(Function<WherePredicate<T1>, Query<TSubQuery>> subQueryableFunc, String propertyAlias) {
        throw new UnsupportedOperationException();
    }

    @Override
    public TableAvailable getTable() {
        return table;
    }

    @Override
    public ColumnAsSelector<T1, TR> columnAll() {

        EntityTableExpressionBuilder table = entityQueryExpressionBuilder.getTable(index);
        if (table.getEntityClass().equals(resultClass)) {
            super.columnAll();
            return this;
        } else {
            return columnAll(table);
        }
    }

    private ColumnAsSelector<T1, TR> columnAll(EntityTableExpressionBuilder table) {
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
                ColumnMetadata targetColumnMetadata = targetEntityMetadata.getColumnMetadataOrNull(sourceColumnName);
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
        return this;
    }

    @Override
    public ColumnAsSelector<T1, TR> columnFuncAs(ColumnPropertyFunction columnPropertyFunction, String propertyAlias) {
        throw new UnsupportedOperationException();
    }
}
