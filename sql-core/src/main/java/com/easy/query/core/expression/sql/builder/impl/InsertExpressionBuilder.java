package com.easy.query.core.expression.sql.builder.impl;

import com.easy.query.core.abstraction.EasyQueryRuntimeContext;
import com.easy.query.core.enums.SqlExecuteStrategyEnum;
import com.easy.query.core.expression.sql.builder.EntityExpressionBuilder;
import com.easy.query.core.expression.sql.expression.EasyInsertSqlExpression;
import com.easy.query.core.expression.sql.expression.factory.EasyExpressionFactory;
import com.easy.query.core.metadata.ColumnMetadata;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.exception.EasyQueryException;
import com.easy.query.core.expression.segment.SqlEntitySegment;
import com.easy.query.core.expression.segment.builder.ProjectSqlBuilderSegmentImpl;
import com.easy.query.core.expression.segment.builder.SqlBuilderSegment;
import com.easy.query.core.expression.parser.impl.DefaultInsertSqlColumnSelector;
import com.easy.query.core.expression.sql.builder.internal.AbstractEntityExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityInsertExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityTableExpressionBuilder;
import com.easy.query.core.expression.sql.builder.ExpressionContext;
import com.easy.query.core.metadata.EntityMetadataManager;
import com.easy.query.core.util.BeanUtil;
import com.easy.query.core.util.ClassUtil;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * @author xuejiaming
 * @FileName: EasySqlInsertExpression.java
 * @Description: 文件说明
 * @Date: 2023/3/4 16:49
 */
public class InsertExpressionBuilder extends AbstractEntityExpressionBuilder implements EntityInsertExpressionBuilder {
    protected final SqlBuilderSegment columns;

    public InsertExpressionBuilder(ExpressionContext expressionContext) {
        super(expressionContext);
        this.columns = new ProjectSqlBuilderSegmentImpl();
    }

    @Override
    public SqlBuilderSegment getColumns() {
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

    private boolean clearIgnoreProperties(Set<String> ignorePropertySet, EasyQueryRuntimeContext runtimeContext, Object entity) {

        if (entity != null) {
            //以下应该二选一
            //todo 获取更新策略按需更新
            SqlExecuteStrategyEnum insertStrategy = sqlExpressionContext.getSqlStrategy();
            if (Objects.equals(SqlExecuteStrategyEnum.DEFAULT, insertStrategy)) {
                SqlExecuteStrategyEnum globalUpdateStrategy = runtimeContext.getEasyQueryConfiguration().getEasyQueryOption().getUpdateStrategy();
                getCustomIgnoreProperties(ignorePropertySet, globalUpdateStrategy, runtimeContext.getEntityMetadataManager(), entity);
                return true;
            } else {
                getCustomIgnoreProperties(ignorePropertySet, insertStrategy, runtimeContext.getEntityMetadataManager(), entity);
                return true;
            }
        }
        return false;
    }

    private void getCustomIgnoreProperties(Set<String> ignoreUpdateSet, SqlExecuteStrategyEnum updateStrategy, EntityMetadataManager entityMetadataManager, Object entity) {

        if (Objects.equals(SqlExecuteStrategyEnum.ONLY_NOT_NULL_COLUMNS, updateStrategy) || Objects.equals(SqlExecuteStrategyEnum.ONLY_NULL_COLUMNS, updateStrategy)) {
            Set<String> beanMatchProperties = BeanUtil.getBeanMatchProperties(entityMetadataManager, entity, Objects.equals(SqlExecuteStrategyEnum.ONLY_NOT_NULL_COLUMNS, updateStrategy) ? Objects::isNull : Objects::nonNull);
            ignoreUpdateSet.addAll(beanMatchProperties);
        }
    }

    @Override
    public EasyInsertSqlExpression toExpression(Object entity) {

        checkTable();

        EntityTableExpressionBuilder table = getTable(0);
        EasyQueryRuntimeContext runtimeContext = getRuntimeContext();
        EasyExpressionFactory expressionFactory = runtimeContext.getExpressionFactory();
        EasyInsertSqlExpression easyInsertSqlExpression = expressionFactory.createEasyInsertSqlExpression(runtimeContext, table.toExpression(),sqlExpressionContext.getExecuteMethod());
        EntityMetadata entityMetadata = table.getEntityMetadata();
        SqlBuilderSegment insertCloneColumns = getColumns().cloneSqlBuilder();
        if (insertCloneColumns.isEmpty()) {
            DefaultInsertSqlColumnSelector<?> columnSelector = new DefaultInsertSqlColumnSelector<>(0, this, insertCloneColumns);
            columnSelector.columnAll();

            Set<String> ignorePropertySet = new HashSet<>(entityMetadata.getProperties().size());
            boolean clearIgnoreProperties = clearIgnoreProperties(ignorePropertySet, getRuntimeContext(), entity);


            insertCloneColumns.getSqlSegments().removeIf(o -> {
                if (o instanceof SqlEntitySegment) {
                    SqlEntitySegment sqlEntitySegment = (SqlEntitySegment) o;
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

        int insertColumns = insertCloneColumns.getSqlSegments().size();
        if (insertColumns == 0) {
            throw new EasyQueryException("not found insert columns :" + ClassUtil.getSimpleName(table.getEntityClass()));
        }
        insertCloneColumns.copyTo(easyInsertSqlExpression.getColumns());
        return easyInsertSqlExpression;
    }

    @Override
    public EntityExpressionBuilder cloneEntityExpressionBuilder() {


        ExpressionContext sqlExpressionContext = getExpressionContext();
        InsertExpressionBuilder insertExpressionBuilder = new InsertExpressionBuilder(sqlExpressionContext);

        if (getColumns().isNotEmpty()) {
            getColumns().copyTo(insertExpressionBuilder.getColumns());
        }
        for (EntityTableExpressionBuilder table : super.tables) {
            insertExpressionBuilder.tables.add(table.copyEntityTableExpressionBuilder());
        }
        return insertExpressionBuilder;
    }
}
