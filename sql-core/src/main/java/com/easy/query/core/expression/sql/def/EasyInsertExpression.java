package com.easy.query.core.expression.sql.def;

import com.easy.query.core.abstraction.EasyQueryRuntimeContext;
import com.easy.query.core.basic.plugin.track.EntityState;
import com.easy.query.core.basic.plugin.track.TrackContext;
import com.easy.query.core.basic.plugin.track.TrackManager;
import com.easy.query.core.enums.SqlExecuteStrategyEnum;
import com.easy.query.core.metadata.ColumnMetadata;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.exception.EasyQueryException;
import com.easy.query.core.expression.segment.SqlEntitySegment;
import com.easy.query.core.expression.segment.builder.ProjectSqlBuilderSegment;
import com.easy.query.core.expression.segment.builder.SqlBuilderSegment;
import com.easy.query.core.expression.parser.impl.DefaultInsertSqlColumnSelector;
import com.easy.query.core.expression.sql.internal.AbstractEntityExpression;
import com.easy.query.core.expression.sql.EntityInsertExpression;
import com.easy.query.core.expression.sql.EntityTableExpression;
import com.easy.query.core.expression.sql.ExpressionContext;
import com.easy.query.core.metadata.EntityMetadataManager;
import com.easy.query.core.util.BeanUtil;
import com.easy.query.core.util.ClassUtil;
import com.easy.query.core.util.TrackUtil;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * @FileName: EasySqlInsertExpression.java
 * @Description: 文件说明
 * @Date: 2023/3/4 16:49
 * @author xuejiaming
 */
public abstract class EasyInsertExpression extends AbstractEntityExpression implements EntityInsertExpression {
    private final SqlBuilderSegment columns;

    public EasyInsertExpression(ExpressionContext queryExpressionContext) {
        super(queryExpressionContext);
        this.columns = new ProjectSqlBuilderSegment();
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

    @Override
    public String toSql() {
        return toSql(null);
    }

    private boolean clearIgnoreProperties(Set<String> ignorePropertySet, EasyQueryRuntimeContext runtimeContext, Object entity){

        if (entity != null) {
            //以下应该二选一
            //todo 获取更新策略按需更新
            SqlExecuteStrategyEnum insertStrategy = sqlExpressionContext.getSqlStrategy();
            if (Objects.equals(SqlExecuteStrategyEnum.DEFAULT, insertStrategy)) {
                SqlExecuteStrategyEnum globalUpdateStrategy = runtimeContext.getEasyQueryConfiguration().getEasyQueryOption().getUpdateStrategy();
                getCustomIgnoreProperties(ignorePropertySet,globalUpdateStrategy,runtimeContext.getEntityMetadataManager(),entity);
                return true;
            } else {
                getCustomIgnoreProperties(ignorePropertySet,insertStrategy,runtimeContext.getEntityMetadataManager(),entity);
                return true;
            }
        }
        return false;
    }
    private void getCustomIgnoreProperties(Set<String> ignoreUpdateSet, SqlExecuteStrategyEnum updateStrategy, EntityMetadataManager entityMetadataManager, Object entity){

        if (Objects.equals(SqlExecuteStrategyEnum.ONLY_NOT_NULL_COLUMNS, updateStrategy) || Objects.equals(SqlExecuteStrategyEnum.ONLY_NULL_COLUMNS, updateStrategy)) {
            Set<String> beanMatchProperties = BeanUtil.getBeanMatchProperties(entityMetadataManager, entity, Objects.equals(SqlExecuteStrategyEnum.ONLY_NOT_NULL_COLUMNS, updateStrategy) ? Objects::isNull : Objects::nonNull);
            ignoreUpdateSet.addAll(beanMatchProperties);
        }
    }
    @Override
    public String toSql(Object entity) {
        checkTable();
        getExpressionContext().clearParameters();

        EntityTableExpression table = getTable(0);
        EntityMetadata entityMetadata = table.getEntityMetadata();
        SqlBuilderSegment insertCloneColumns = getColumns().cloneSqlBuilder();
        if (insertCloneColumns.isEmpty()) {
            DefaultInsertSqlColumnSelector<?> columnSelector = new DefaultInsertSqlColumnSelector<>(0, this,insertCloneColumns);
            columnSelector.columnAll();

            Set<String> ignorePropertySet = new HashSet<>(entityMetadata.getProperties().size());
            boolean clearIgnoreProperties = clearIgnoreProperties(ignorePropertySet, getRuntimeContext(), entity);


            insertCloneColumns.getSqlSegments().removeIf(o -> {
                if (o instanceof SqlEntitySegment) {
                    SqlEntitySegment sqlEntitySegment = (SqlEntitySegment) o;
                    String propertyName = sqlEntitySegment.getPropertyName();
                    ColumnMetadata columnMetadata = entityMetadata.getColumnNotNull(propertyName);
                    if(columnMetadata.isIncrement()||columnMetadata.isInsertIgnore()){
                        return true;
                    }
                    if(clearIgnoreProperties){
                        return ignorePropertySet.contains(propertyName);
                    }
                }
                return false;
            });
        }

        int insertColumns = insertCloneColumns.getSqlSegments().size();
        if (insertColumns == 0) {
            throw new EasyQueryException("not found insert columns :"+ ClassUtil.getSimpleName(table.getEntityClass()));
        }
        StringBuilder sql = new StringBuilder("INSERT INTO ");
        String tableName = entityMetadata.getTableName();
        sql.append(tableName).append(" (").append(insertCloneColumns.toSql()).append(") VALUES (");
        sql.append("?");
        for (int i = 0; i < insertColumns - 1; i++) {
            sql.append(",?");
        }
        sql.append(") ");
        return sql.toString();
    }
}
