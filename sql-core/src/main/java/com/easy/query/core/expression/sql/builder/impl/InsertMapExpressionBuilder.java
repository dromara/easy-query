package com.easy.query.core.expression.sql.builder.impl;

import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.enums.SQLExecuteStrategyEnum;
import com.easy.query.core.exception.EasyQueryException;
import com.easy.query.core.expression.segment.InsertUpdateSetColumnSQLSegment;
import com.easy.query.core.expression.segment.builder.ProjectSQLBuilderSegmentImpl;
import com.easy.query.core.expression.segment.builder.SQLBuilderSegment;
import com.easy.query.core.expression.segment.factory.SQLSegmentFactory;
import com.easy.query.core.expression.sql.builder.ColumnConfigurerContext;
import com.easy.query.core.expression.sql.builder.EntityInsertExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityTableExpressionBuilder;
import com.easy.query.core.expression.sql.builder.ExpressionContext;
import com.easy.query.core.expression.sql.builder.internal.AbstractEntityExpressionBuilder;
import com.easy.query.core.expression.sql.expression.EntityInsertSQLExpression;
import com.easy.query.core.expression.sql.expression.factory.ExpressionFactory;
import com.easy.query.core.expression.sql.expression.impl.EntitySQLExpressionMetadata;
import com.easy.query.core.metadata.EntityMetadataManager;
import com.easy.query.core.util.EasyClassUtil;
import com.easy.query.core.util.EasyObjectUtil;
import com.easy.query.core.util.EasySQLSegmentUtil;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Predicate;

/**
 * create time 2023/10/3 08:35
 * 文件说明
 *
 * @author xuejiaming
 */
public class InsertMapExpressionBuilder extends AbstractEntityExpressionBuilder implements EntityInsertExpressionBuilder {

    public InsertMapExpressionBuilder(ExpressionContext expressionContext) {
        super(expressionContext, Map.class);
    }

    @Override
    public SQLBuilderSegment getColumns() {
        throw new UnsupportedOperationException();
    }

    @Override
    public SQLBuilderSegment getDuplicateKeyUpdateColumns() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Collection<String> getDuplicateKeys() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void addDuplicateKey(String duplicateKey) {
        throw new UnsupportedOperationException();
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

    private boolean clearIgnoreProperties(Set<String> ignorePropertySet, QueryRuntimeContext runtimeContext, Map<String, Object> map) {

        if (map != null) {
            //以下应该二选一
            //todo 获取更新策略按需更新
            SQLExecuteStrategyEnum insertStrategy = expressionContext.getSQLStrategy();
            if (Objects.equals(SQLExecuteStrategyEnum.DEFAULT, insertStrategy)) {
                SQLExecuteStrategyEnum globalInsertStrategy = runtimeContext.getQueryConfiguration().getEasyQueryOption().getInsertStrategy();
                getCustomIgnoreProperties(ignorePropertySet, globalInsertStrategy, runtimeContext.getEntityMetadataManager(), map);
                return true;
            } else {
                getCustomIgnoreProperties(ignorePropertySet, insertStrategy, runtimeContext.getEntityMetadataManager(), map);
                return true;
            }
        }
        return false;
    }

    private void getCustomIgnoreProperties(Set<String> ignoreUpdateSet, SQLExecuteStrategyEnum updateStrategy, EntityMetadataManager entityMetadataManager, Map<String, Object> map) {

        if (Objects.equals(SQLExecuteStrategyEnum.ONLY_NOT_NULL_COLUMNS, updateStrategy) || Objects.equals(SQLExecuteStrategyEnum.ONLY_NULL_COLUMNS, updateStrategy)) {
            Predicate<Object> valuePredicate = Objects.equals(SQLExecuteStrategyEnum.ONLY_NOT_NULL_COLUMNS, updateStrategy) ? Objects::isNull : Objects::nonNull;
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                Object value = entry.getValue();
                if (valuePredicate.test(value)) {
                    ignoreUpdateSet.add(entry.getKey());
                }
            }
        }
    }

    @Override
    public EntityInsertSQLExpression toExpression(Object entity) {
        if (!(entity instanceof Map)) {
            throw new UnsupportedOperationException("map insert can not been null");
        }

        checkTable();
        Map<String, Object> map = EasyObjectUtil.typeCastNullable(entity);
        EntityTableExpressionBuilder table = getTable(0);
        QueryRuntimeContext runtimeContext = getRuntimeContext();
        ExpressionFactory expressionFactory = runtimeContext.getExpressionFactory();
        EntitySQLExpressionMetadata entitySQLExpressionMetadata = new EntitySQLExpressionMetadata(expressionContext, runtimeContext);
        EntityInsertSQLExpression easyInsertSQLExpression = expressionFactory.createEasyInsertSQLExpression(entitySQLExpressionMetadata, table.toExpression());
        SQLBuilderSegment insertCloneColumns = new ProjectSQLBuilderSegmentImpl();
        SQLSegmentFactory sqlSegmentFactory = runtimeContext.getSQLSegmentFactory();
        //format

        Set<String> ignorePropertySet = new HashSet<>(map.size());
        boolean clearIgnoreProperties = clearIgnoreProperties(ignorePropertySet, getRuntimeContext(), map);
        for (String columnName : map.keySet()) {
            if(clearIgnoreProperties&&ignorePropertySet.contains(columnName)){
                continue;
            }
            InsertUpdateSetColumnSQLSegment columnInsertSegment = sqlSegmentFactory.createInsertMapColumnSegment(columnName, runtimeContext);
            insertCloneColumns.append(columnInsertSegment);
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


        EntityInsertExpressionBuilder insertExpressionBuilder = runtimeContext.getExpressionBuilderFactory().createEntityInsertExpressionBuilder(expressionContext, queryClass);

        if (EasySQLSegmentUtil.isNotEmpty(getColumns())) {
            getColumns().copyTo(insertExpressionBuilder.getColumns());
        }

        for (EntityTableExpressionBuilder table : super.tables) {
            insertExpressionBuilder.getTables().add(table.copyEntityTableExpressionBuilder());
        }
        return insertExpressionBuilder;
    }

    @Override
    public Map<String, ColumnConfigurerContext> getColumnConfigurer() {
        throw new UnsupportedOperationException();
    }
}

