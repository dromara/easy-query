package com.easy.query.core.basic.api.insert;

import com.easy.query.core.basic.api.internal.SqlEntityNode;
import com.easy.query.core.basic.jdbc.parameter.SQLParameter;
import com.easy.query.core.basic.plugin.interceptor.EasyInterceptorEntry;
import com.easy.query.core.configuration.EasyQueryConfiguration;
import com.easy.query.core.enums.SqlExecuteStrategyEnum;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.enums.MultiTableTypeEnum;
import com.easy.query.core.basic.plugin.interceptor.EasyEntityInterceptor;
import com.easy.query.core.expression.sql.def.EasyEntityTableExpression;
import com.easy.query.core.expression.sql.EntityInsertExpression;
import com.easy.query.core.expression.sql.EntityTableExpression;
import com.easy.query.core.util.ArrayUtil;
import com.easy.query.core.util.SqlExpressionUtil;
import com.easy.query.core.basic.jdbc.executor.EasyExecutor;
import com.easy.query.core.basic.jdbc.executor.ExecutorContext;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author xuejiaming
 * @FileName: AbstractInsertable.java
 * @Description: 文件说明
 * @Date: 2023/2/20 12:30
 */
public abstract class AbstractInsertable<T> implements Insertable<T> {
    protected final List<T> entities;
    protected final EntityMetadata entityMetadata;
    protected final EntityInsertExpression entityInsertExpression;

    public AbstractInsertable(Class<T> clazz, EntityInsertExpression entityInsertExpression) {
        this.entityInsertExpression = entityInsertExpression;
        this.entities = new ArrayList<>();
        entityMetadata = this.entityInsertExpression.getRuntimeContext().getEntityMetadataManager().getEntityMetadata(clazz);
        entityMetadata.checkTable();

        EntityTableExpression table = new EasyEntityTableExpression(entityMetadata, 0, null, MultiTableTypeEnum.FROM);
        this.entityInsertExpression.addSqlEntityTableExpression(table);
    }

    @Override
    public Insertable<T> insert(T entity) {
        if (entity != null) {
            entities.add(entity);
        }
        return this;
    }

//
//    @Override
//    public Insert<T> ignoreColumns(SqlExpression<SqlColumnSelector<T>> columnExpression) {
//        return null;
//    }

    protected void insertBefore() {

        List<EasyInterceptorEntry> insertInterceptors = entityMetadata.getEntityInterceptors();
        if (ArrayUtil.isNotEmpty(insertInterceptors)) {
            EasyQueryConfiguration easyQueryConfiguration = entityInsertExpression.getRuntimeContext().getEasyQueryConfiguration();
            List<EasyEntityInterceptor> entityInterceptors = entityInsertExpression.getExpressionContext().getInterceptorFilter(insertInterceptors)
                    .map(interceptor -> (EasyEntityInterceptor) easyQueryConfiguration.getEasyInterceptor(interceptor.getName())).collect(Collectors.toList());
            if (ArrayUtil.isNotEmpty(entityInterceptors)) {
                Class<?> entityClass = entityMetadata.getEntityClass();
                for (T entity : entities) {
                    for (EasyEntityInterceptor entityInterceptor : entityInterceptors) {
                        entityInterceptor.configureInsert(entityClass, entityInsertExpression, entity);
                    }
                }
            }
        }
    }
    /**
     * 不分组就生成一条sql
     *
     * @return
     */
    private boolean useInsertSqlGroup() {
        SqlExecuteStrategyEnum sqlStrategy = SqlExpressionUtil.getExecuteStrategy(entityInsertExpression.getExpressionContext());
        return !SqlExecuteStrategyEnum.ALL_COLUMNS.equals(sqlStrategy);
    }
    private List<SqlEntityNode> createInsertEntityNode() {
        if (useInsertSqlGroup()) {
            Map<String, SqlEntityNode> updateEntityNodeMap = new LinkedHashMap<>();
            for (T entity : entities) {
                String updateSql = toSql(entity);
                //如果当前对象没有需要更新的列直接忽略
                if (updateSql == null) {
                    continue;
                }
                List<SQLParameter> parameters = new ArrayList<>(entityInsertExpression.getParameters());
                SqlEntityNode updateEntityNode = updateEntityNodeMap.computeIfAbsent(updateSql, k -> new SqlEntityNode(updateSql, parameters));
                updateEntityNode.getEntities().add(entity);
            }
            return new ArrayList<>(updateEntityNodeMap.values());
        } else {
            String updateSql = toSql(null);
            SqlEntityNode updateEntityNode = new SqlEntityNode(updateSql, new ArrayList<>(entityInsertExpression.getParameters()), entities.size());
            updateEntityNode.getEntities().addAll(entities);
            return Collections.singletonList(updateEntityNode);
        }
    }

    @Override
    public long executeRows(boolean fillAutoIncrement) {
        if (!entities.isEmpty()) {
            insertBefore();
            List<SqlEntityNode> updateEntityNodes = createInsertEntityNode();
            EasyExecutor easyExecutor = entityInsertExpression.getRuntimeContext().getEasyExecutor();
            int i = 0;
            for (SqlEntityNode updateEntityNode : updateEntityNodes) {
                i += easyExecutor.insert(ExecutorContext.create(entityInsertExpression.getRuntimeContext()), updateEntityNode.getSql(), updateEntityNode.getEntities(), updateEntityNode.getSqlParameters(), fillAutoIncrement);
            }
            return i;
        }

        return 0;
    }

    @Override
    public Insertable<T> asTable(Function<String, String> tableNameAs) {
        entityInsertExpression.getRecentlyTable().setTableNameAs(tableNameAs);
        return this;
    }

    @Override
    public Insertable<T> noInterceptor() {
        entityInsertExpression.getExpressionContext().noInterceptor();
        return this;
    }
    @Override
    public Insertable<T> useInterceptor(String name) {
        entityInsertExpression.getExpressionContext().useInterceptor(name);
        return this;
    }
    @Override
    public Insertable<T> noInterceptor(String name) {
        entityInsertExpression.getExpressionContext().noInterceptor(name);
        return this;
    }

    @Override
    public Insertable<T> useInterceptor() {
        entityInsertExpression.getExpressionContext().useInterceptor();
        return this;
    }

    @Override
    public Insertable<T> setSqlStrategy(boolean condition, SqlExecuteStrategyEnum sqlStrategy) {
        if(condition){
            entityInsertExpression.getExpressionContext().useSqlStrategy(sqlStrategy);
        }
        return this;
    }

    public abstract String toSql(Object entity);
}
