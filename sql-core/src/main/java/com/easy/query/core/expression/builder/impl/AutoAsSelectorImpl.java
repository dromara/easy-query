package com.easy.query.core.expression.builder.impl;

import com.easy.query.core.basic.api.select.Query;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.enums.EasyBehaviorEnum;
import com.easy.query.core.exception.EasyQueryInvalidOperationException;
import com.easy.query.core.expression.builder.AsSelector;
import com.easy.query.core.expression.func.ColumnPropertyFunction;
import com.easy.query.core.expression.lambda.SQLFuncExpression;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.segment.ColumnSegment;
import com.easy.query.core.expression.segment.CloneableSQLSegment;
import com.easy.query.core.expression.segment.builder.SQLBuilderSegment;
import com.easy.query.core.expression.sql.builder.AnonymousEntityTableExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityQueryExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityTableExpressionBuilder;
import com.easy.query.core.metadata.ColumnMetadata;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.util.EasyClassUtil;
import com.easy.query.core.util.EasyCollectionUtil;

import java.util.Collection;
import java.util.Objects;

/**
 * create time 2023/6/24 11:11
 * 文件说明
 *
 * @author xuejiaming
 */
public class AutoAsSelectorImpl  extends AbstractSelector<AsSelector> implements AsSelector {

    private final EntityQueryExpressionBuilder entityQueryExpressionBuilder;
    private final Class<?> resultClass;

    public AutoAsSelectorImpl(EntityQueryExpressionBuilder entityQueryExpressionBuilder, SQLBuilderSegment sqlBuilderSegment, Class<?> resultClass) {
        super(entityQueryExpressionBuilder, sqlBuilderSegment);
        this.entityQueryExpressionBuilder = entityQueryExpressionBuilder;
        this.resultClass = resultClass;
    }

    @Override
    public QueryRuntimeContext getRuntimeContext() {
        return runtimeContext;
    }

    @Override
    public AsSelector columnAs(TableAvailable table, String property, String propertyAlias) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <TSubQuery> AsSelector columnSubQueryAs(SQLFuncExpression<Query<TSubQuery>> subQueryableFunc, String propertyAlias) {
        throw new UnsupportedOperationException();
    }

    @Override
    public AsSelector columnFuncAs(TableAvailable table, ColumnPropertyFunction columnPropertyFunction, String propertyAlias) {
        throw new UnsupportedOperationException();
    }

    @Override
    public AsSelector sqlSegmentAs(CloneableSQLSegment sqlColumnSegment, String propertyAlias) {
        throw new UnsupportedOperationException();
    }

    @Override
    protected AsSelector castTChain() {
        return this;
    }

    @Override
    public AsSelector columnAll(TableAvailable table) {

        if (table.getEntityClass().equals(resultClass)) {
            super.columnAll(table);
            return this;
        } else {

            EntityTableExpressionBuilder tableExpressionBuilder = EasyCollectionUtil.firstOrDefault(entityQueryExpressionBuilder.getTables(), t -> Objects.equals(table, t.getEntityTable()), null);
            if(tableExpressionBuilder==null){
                throw new EasyQueryInvalidOperationException("not found table in expression context:"+ EasyClassUtil.getSimpleName(table.getEntityClass()));
            }
            return columnAll0(tableExpressionBuilder);
        }
    }
    private AsSelector columnAll0(EntityTableExpressionBuilder table) {
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
                if(ignoreColumnIfLargeNotQuery(queryLargeColumn,sourceColumnMetadata)){
                    continue;
                }
                String sourceColumnName = sourceColumnMetadata.getName();
                ColumnMetadata targetColumnMetadata = targetEntityMetadata.getColumnMetadataOrNull(sourceColumnName);
                if (targetColumnMetadata != null) {

                    if(ignoreColumnIfLargeNotQuery(queryLargeColumn,targetColumnMetadata)){
                        continue;
                    }
                    String targetColumnName = targetColumnMetadata.getName();
                    //如果当前属性和查询对象属性一致那么就返回对应的列名，对应的列名如果不一样就返回对应返回结果对象的属性上的列名
                    String alias = Objects.equals(sourceColumnName, targetColumnName) ? null : targetColumnName;
                    ColumnSegment columnSegment = sqlSegmentFactory.createColumnSegment(table.getEntityTable(), property, entityQueryExpressionBuilder.getRuntimeContext(), alias);
                    sqlBuilderSegment.append(columnSegment);
                }

            }
        }
        return this;
    }
}
