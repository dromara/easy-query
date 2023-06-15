package com.easy.query.core.expression.sql.builder.impl;

import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.enums.SQLExecuteStrategyEnum;
import com.easy.query.core.exception.EasyQueryException;
import com.easy.query.core.expression.segment.ColumnInsertSegment;
import com.easy.query.core.expression.segment.SQLEntitySegment;
import com.easy.query.core.expression.segment.builder.ProjectSQLBuilderSegmentImpl;
import com.easy.query.core.expression.segment.builder.SQLBuilderSegment;
import com.easy.query.core.expression.segment.factory.SQLSegmentFactory;
import com.easy.query.core.expression.sql.builder.EntityInsertExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityTableExpressionBuilder;
import com.easy.query.core.expression.sql.builder.ExpressionContext;
import com.easy.query.core.expression.sql.builder.internal.AbstractEntityExpressionBuilder;
import com.easy.query.core.expression.sql.expression.EntityInsertSQLExpression;
import com.easy.query.core.expression.sql.expression.factory.ExpressionFactory;
import com.easy.query.core.expression.sql.expression.impl.EntitySQLExpressionMetadata;
import com.easy.query.core.metadata.ColumnMetadata;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.metadata.EntityMetadataManager;
import com.easy.query.core.util.EasyBeanUtil;
import com.easy.query.core.util.EasyClassUtil;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * @author xuejiaming
 * @Description: 文件说明
 * @Date: 2023/3/4 16:49
 */
public class InsertExpressionBuilder extends AbstractEntityExpressionBuilder implements EntityInsertExpressionBuilder {
    protected final SQLBuilderSegment columns;

    public InsertExpressionBuilder(ExpressionContext expressionContext) {
        super(expressionContext);
        this.columns = new ProjectSQLBuilderSegmentImpl();
    }

    @Override
    public SQLBuilderSegment getColumns() {
        return columns;
    }

    private void checkTable() {
        int tableCount = getTables().size();
        if (tableCount == 0) {
            throw new EasyQueryException("未找到查询表信息");
        }
        if (tableCount > 1) {
            throw new EasyQueryException("找到多张表信息");
        }
    }

    private boolean clearIgnoreProperties(Set<String> ignorePropertySet, QueryRuntimeContext runtimeContext, Object entity) {

        if (entity != null) {
            //以下应该二选一
            //todo 获取更新策略按需更新
            SQLExecuteStrategyEnum insertStrategy = expressionContext.getSQLStrategy();
            if (Objects.equals(SQLExecuteStrategyEnum.DEFAULT, insertStrategy)) {
                SQLExecuteStrategyEnum globalInsertStrategy = runtimeContext.getQueryConfiguration().getEasyQueryOption().getInsertStrategy();
                getCustomIgnoreProperties(ignorePropertySet, globalInsertStrategy, runtimeContext.getEntityMetadataManager(), entity);
                return true;
            } else {
                getCustomIgnoreProperties(ignorePropertySet, insertStrategy, runtimeContext.getEntityMetadataManager(), entity);
                return true;
            }
        }
        return false;
    }

    private void getCustomIgnoreProperties(Set<String> ignoreUpdateSet, SQLExecuteStrategyEnum updateStrategy, EntityMetadataManager entityMetadataManager, Object entity) {

        if (Objects.equals(SQLExecuteStrategyEnum.ONLY_NOT_NULL_COLUMNS, updateStrategy) || Objects.equals(SQLExecuteStrategyEnum.ONLY_NULL_COLUMNS, updateStrategy)) {
            Set<String> beanMatchProperties = EasyBeanUtil.getBeanMatchProperties(entityMetadataManager, entity, Objects.equals(SQLExecuteStrategyEnum.ONLY_NOT_NULL_COLUMNS, updateStrategy) ? Objects::isNull : Objects::nonNull);
            ignoreUpdateSet.addAll(beanMatchProperties);
        }
    }

    @Override
    public EntityInsertSQLExpression toExpression(Object entity) {

        checkTable();

        EntityTableExpressionBuilder table = getTable(0);
        QueryRuntimeContext runtimeContext = getRuntimeContext();
        ExpressionFactory expressionFactory = runtimeContext.getExpressionFactory();
        EntitySQLExpressionMetadata entitySQLExpressionMetadata = new EntitySQLExpressionMetadata(expressionContext.getTableContext(), runtimeContext);
        EntityInsertSQLExpression easyInsertSQLExpression = expressionFactory.createEasyInsertSQLExpression(entitySQLExpressionMetadata, table.toExpression());
        EntityMetadata entityMetadata = table.getEntityMetadata();
        SQLBuilderSegment insertCloneColumns = getColumns().cloneSQLBuilder();
        if (insertCloneColumns.isEmpty()) {
            SQLSegmentFactory sqlSegmentFactory = runtimeContext.getSQLSegmentFactory();

            Collection<String> properties = table.getEntityMetadata().getProperties();
            for (String property : properties) {
                ColumnInsertSegment columnInsertSegment = sqlSegmentFactory.createColumnInsertSegment(table.getEntityTable(), property, runtimeContext);
                insertCloneColumns.append(columnInsertSegment);
            }

            Set<String> ignorePropertySet = new HashSet<>(entityMetadata.getProperties().size());
            boolean clearIgnoreProperties = clearIgnoreProperties(ignorePropertySet, getRuntimeContext(), entity);


            insertCloneColumns.getSQLSegments().removeIf(o -> {
                if (o instanceof SQLEntitySegment) {
                    SQLEntitySegment sqlEntitySegment = (SQLEntitySegment) o;
                    String propertyName = sqlEntitySegment.getPropertyName();
                    ColumnMetadata columnMetadata = entityMetadata.getColumnNotNull(propertyName);
                    if (columnMetadata.isIncrement() || columnMetadata.isInsertIgnore()) {
                        return true;
                    }
                    if (clearIgnoreProperties) {
                        return ignorePropertySet.contains(propertyName);
                    }
                }
                return false;
            });
        }

        int insertColumns = insertCloneColumns.getSQLSegments().size();
        if (insertColumns == 0) {
            throw new EasyQueryException("not found insert columns :" + EasyClassUtil.getSimpleName(table.getEntityClass()));
        }
        insertCloneColumns.copyTo(easyInsertSQLExpression.getColumns());
        return easyInsertSQLExpression;
    }

    @Override
    public EntityInsertExpressionBuilder cloneEntityExpressionBuilder() {


        EntityInsertExpressionBuilder insertExpressionBuilder = runtimeContext.getExpressionBuilderFactory().createEntityInsertExpressionBuilder(expressionContext);

        if (getColumns().isNotEmpty()) {
            getColumns().copyTo(insertExpressionBuilder.getColumns());
        }
        for (EntityTableExpressionBuilder table : super.tables) {
            insertExpressionBuilder.getTables().add(table.copyEntityTableExpressionBuilder());
        }
        return insertExpressionBuilder;
    }
}
