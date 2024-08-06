package com.easy.query.core.expression.sql.builder.impl;

import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.enums.SQLExecuteStrategyEnum;
import com.easy.query.core.enums.SQLPredicateCompareEnum;
import com.easy.query.core.exception.EasyQueryException;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.segment.builder.SQLBuilderSegment;
import com.easy.query.core.expression.segment.builder.UpdateSetSQLBuilderSegment;
import com.easy.query.core.expression.segment.condition.AndPredicateSegment;
import com.easy.query.core.expression.segment.condition.PredicateSegment;
import com.easy.query.core.expression.segment.condition.predicate.MapColumnNullAssertPredicate;
import com.easy.query.core.expression.segment.condition.predicate.MapColumnValuePredicate;
import com.easy.query.core.expression.segment.impl.InsertUpdateColumnConfigureSegmentImpl;
import com.easy.query.core.expression.segment.impl.UpdateMapColumnSegmentImpl;
import com.easy.query.core.expression.sql.builder.ColumnConfigurerContext;
import com.easy.query.core.expression.sql.builder.EntityTableExpressionBuilder;
import com.easy.query.core.expression.sql.builder.ExpressionContext;
import com.easy.query.core.expression.sql.builder.MapUpdateExpressionBuilder;
import com.easy.query.core.expression.sql.builder.internal.AbstractPredicateEntityExpressionBuilder;
import com.easy.query.core.expression.sql.expression.EntityUpdateSQLExpression;
import com.easy.query.core.expression.sql.expression.factory.ExpressionFactory;
import com.easy.query.core.expression.sql.expression.impl.EntitySQLExpressionMetadata;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.metadata.EntityMetadataManager;
import com.easy.query.core.util.EasyCollectionUtil;
import com.easy.query.core.util.EasyObjectUtil;
import com.easy.query.core.util.EasySQLSegmentUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Predicate;

/**
 * create time 2023/10/3 16:35
 * 文件说明
 *
 * @author xuejiaming
 */
public class UpdateMapExpressionBuilder extends AbstractPredicateEntityExpressionBuilder implements MapUpdateExpressionBuilder {

    private final List<String> whereColumns;
    protected Map<String, ColumnConfigurerContext> columnConfigurers;

    public UpdateMapExpressionBuilder(ExpressionContext expressionContext) {
        super(expressionContext, Map.class);
        this.whereColumns = new ArrayList<>(4);
    }

    @Override
    public Map<String, ColumnConfigurerContext> getColumnConfigurer() {
        if (columnConfigurers == null) {
            columnConfigurers = new HashMap<>();
        }
        return columnConfigurers;
    }

    @Override
    public boolean isExpression() {
        return false;
    }

    @Override
    public void addWhereColumns(String... columnNames) {
        whereColumns.addAll(Arrays.asList(columnNames));
    }

    @Override
    public SQLBuilderSegment getSetColumns() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean hasSetColumns() {
        throw new UnsupportedOperationException();
    }

    @Override
    public PredicateSegment getWhere() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean hasWhere() {
        throw new UnsupportedOperationException();
    }

    @Override
    public SQLBuilderSegment getSetIgnoreColumns() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean hasSetIgnoreColumns() {
        return false;
    }

    @Override
    public SQLBuilderSegment getWhereColumns() {
        throw new UnsupportedOperationException();
    }

    @Override
    public MapUpdateExpressionBuilder cloneEntityExpressionBuilder() {
        UpdateMapExpressionBuilder mapToUpdateExpressionBuilder = new UpdateMapExpressionBuilder(expressionContext);
        mapToUpdateExpressionBuilder.whereColumns.addAll(this.whereColumns);
        for (EntityTableExpressionBuilder table : super.tables) {
            mapToUpdateExpressionBuilder.getTables().add(table.copyEntityTableExpressionBuilder());
        }
        if (columnConfigurers != null) {
            mapToUpdateExpressionBuilder.getColumnConfigurer().putAll(columnConfigurers);
        }
        return mapToUpdateExpressionBuilder;
    }

    @Override
    public EntityUpdateSQLExpression toExpression() {
        throw new UnsupportedOperationException();
    }

    @Override
    public EntityUpdateSQLExpression toExpression(Object entity) {
        if (!(entity instanceof Map)) {
            throw new UnsupportedOperationException("map insert can not been null");
        }
        checkTable();
        Map<String, Object> map = EasyObjectUtil.typeCastNullable(entity);
        EntityTableExpressionBuilder entityTableExpressionBuilder = getTables().get(0);
        return entityToExpression(map, entityTableExpressionBuilder);
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

    public EntityUpdateSQLExpression entityToExpression(Map<String, Object> map, EntityTableExpressionBuilder tableExpressionBuilder) {
        PredicateSegment where = buildPropertyWhere(tableExpressionBuilder, map);//构建指定的where列或者主键key
        if (EasySQLSegmentUtil.isEmpty(where)) {
            throw new EasyQueryException("'UPDATE' statement without 'WHERE'");
        }


        //替换掉配置的片段
        SQLBuilderSegment updateSet = buildUpdateSetByWhere(where, map, tableExpressionBuilder);

        ExpressionFactory expressionFactory = runtimeContext.getExpressionFactory();
        EntitySQLExpressionMetadata entitySQLExpressionMetadata = new EntitySQLExpressionMetadata(expressionContext, runtimeContext);
        EntityUpdateSQLExpression easyUpdateSQLExpression = expressionFactory.createEasyUpdateSQLExpression(entitySQLExpressionMetadata, tableExpressionBuilder.toExpression());
        updateSet.copyTo(easyUpdateSQLExpression.getSetColumns());
        where.copyTo(easyUpdateSQLExpression.getWhere());
        return easyUpdateSQLExpression;
    }

    protected SQLBuilderSegment buildUpdateSetByWhere(PredicateSegment sqlWhere, Map<String, Object> map, EntityTableExpressionBuilder tableExpressionBuilder) {
        SQLBuilderSegment updateSetSQLBuilderSegment = new UpdateSetSQLBuilderSegment();
        TableAvailable entityTable = tableExpressionBuilder.getEntityTable();
        EntityMetadata entityMetadata = entityTable.getEntityMetadata();
        //查询其他所有列除了在where里面的
        Set<String> columns = map.keySet();


        boolean hasConfigure = columnConfigurers != null && !columnConfigurers.isEmpty();
        Set<String> ignorePropertySet = new HashSet<>(map.size());
        boolean clearIgnoreProperties = clearIgnoreProperties(ignorePropertySet, getRuntimeContext(), map);
        for (String column : columns) {
            if (clearIgnoreProperties && ignorePropertySet.contains(column)) {
                continue;
            }
            if (whereColumns.contains(column)) {
                continue;
            }
            UpdateMapColumnSegmentImpl updateMapColumnSegment = new UpdateMapColumnSegmentImpl(entityTable, column, runtimeContext);
            if (hasConfigure) {
                ColumnConfigurerContext columnConfigurerContext = columnConfigurers.get(column);
                if (columnConfigurerContext != null) {
                    InsertUpdateColumnConfigureSegmentImpl insertUpdateColumnConfigureSegment = new InsertUpdateColumnConfigureSegmentImpl(updateMapColumnSegment, getExpressionContext(), columnConfigurerContext.getSqlSegment(), columnConfigurerContext.getSqlNativeExpressionContext());
                    updateSetSQLBuilderSegment.append(insertUpdateColumnConfigureSegment);
                    continue;
                }
            }
            updateSetSQLBuilderSegment.append(updateMapColumnSegment);
        }
        return updateSetSQLBuilderSegment;
    }

    private boolean clearIgnoreProperties(Set<String> ignorePropertySet, QueryRuntimeContext runtimeContext, Map<String, Object> map) {

        if (map != null) {
            //以下应该二选一
            //todo 获取更新策略按需更新
            SQLExecuteStrategyEnum sqlStrategy = expressionContext.getSQLStrategy();
            if (SQLExecuteStrategyEnum.DEFAULT == sqlStrategy) {
                SQLExecuteStrategyEnum globalSQLStrategy = runtimeContext.getQueryConfiguration().getEasyQueryOption().getUpdateStrategy();
                getCustomIgnoreProperties(ignorePropertySet, globalSQLStrategy, runtimeContext.getEntityMetadataManager(), map);
                return true;
            } else {
                getCustomIgnoreProperties(ignorePropertySet, sqlStrategy, runtimeContext.getEntityMetadataManager(), map);
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

    protected PredicateSegment buildPropertyWhere(EntityTableExpressionBuilder tableExpressionBuilder, Map<String, Object> map) {
        AndPredicateSegment where = new AndPredicateSegment(true);
        if (EasyCollectionUtil.isEmpty(whereColumns)) {
            throw new EasyQueryException("map update whereColumns is empty");
        }
        for (String whereColumn : whereColumns) {
            Object predicateValue = map.get(whereColumn);
            if (predicateValue == null) {
                MapColumnNullAssertPredicate columnPredicate = new MapColumnNullAssertPredicate(tableExpressionBuilder.getEntityTable(), whereColumn, SQLPredicateCompareEnum.IS_NULL, runtimeContext);
                AndPredicateSegment andPredicateSegment = new AndPredicateSegment(columnPredicate);
                where.addPredicateSegment(andPredicateSegment);
            } else {
                MapColumnValuePredicate columnValuePredicate = new MapColumnValuePredicate(tableExpressionBuilder.getEntityTable(), whereColumn, predicateValue, SQLPredicateCompareEnum.EQ, runtimeContext);
                AndPredicateSegment andPredicateSegment = new AndPredicateSegment(columnValuePredicate);
                where.addPredicateSegment(andPredicateSegment);
            }
        }
        return where;
    }
}
