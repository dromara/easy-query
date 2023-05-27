package com.easy.query.core.basic.api.insert;

import com.easy.query.core.basic.jdbc.executor.EntityExpressionExecutor;
import com.easy.query.core.basic.jdbc.parameter.ToSQLContext;
import com.easy.query.core.basic.plugin.interceptor.InterceptorEntry;
import com.easy.query.core.configuration.QueryConfiguration;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.enums.ExecuteMethodEnum;
import com.easy.query.core.enums.SQLExecuteStrategyEnum;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.enums.MultiTableTypeEnum;
import com.easy.query.core.basic.plugin.interceptor.EntityInterceptor;
import com.easy.query.core.expression.sql.builder.EntityInsertExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityTableExpressionBuilder;
import com.easy.query.core.util.EasyCollectionUtil;
import com.easy.query.core.basic.jdbc.executor.ExecutorContext;

import java.util.ArrayList;
import java.util.List;
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
    protected final EntityInsertExpressionBuilder entityInsertExpressionBuilder;

    public AbstractInsertable(Class<T> clazz, EntityInsertExpressionBuilder entityInsertExpressionBuilder) {
        this.entityInsertExpressionBuilder = entityInsertExpressionBuilder;
        this.entities = new ArrayList<>();
        QueryRuntimeContext runtimeContext = entityInsertExpressionBuilder.getRuntimeContext();
        entityMetadata = runtimeContext.getEntityMetadataManager().getEntityMetadata(clazz);
        entityMetadata.checkTable();

        EntityTableExpressionBuilder table = runtimeContext.getExpressionBuilderFactory().createEntityTableExpressionBuilder(entityMetadata, 0, MultiTableTypeEnum.NONE, runtimeContext);
        this.entityInsertExpressionBuilder.addSQLEntityTableExpression(table);
    }

    @Override
    public Insertable<T> insert(T entity) {
        if (entity != null) {
            entities.add(entity);
        }
        return this;
    }

    protected void insertBefore() {
        //是否使用自定义插入策略
        List<InterceptorEntry> insertInterceptors = entityMetadata.getEntityInterceptors();
        if (EasyCollectionUtil.isNotEmpty(insertInterceptors)) {
            QueryConfiguration easyQueryConfiguration = entityInsertExpressionBuilder.getRuntimeContext().getQueryConfiguration();
            List<EntityInterceptor> entityInterceptors = entityInsertExpressionBuilder.getExpressionContext().getInterceptorFilter(insertInterceptors)
                    .map(interceptor -> (EntityInterceptor) easyQueryConfiguration.getEasyInterceptor(interceptor.getName())).collect(Collectors.toList());
            if (EasyCollectionUtil.isNotEmpty(entityInterceptors)) {
                Class<?> entityClass = entityMetadata.getEntityClass();
                for (T entity : entities) {
                    for (EntityInterceptor entityInterceptor : entityInterceptors) {
                        entityInterceptor.configureInsert(entityClass, entityInsertExpressionBuilder, entity);
                    }
                }
            }
        }
    }

    @Override
    public long executeRows(boolean fillAutoIncrement) {
        if (!entities.isEmpty()) {
            insertBefore();
            EntityExpressionExecutor entityExpressionExecutor = entityInsertExpressionBuilder.getRuntimeContext().getEntityExpressionExecutor();
            return entityExpressionExecutor.insert(ExecutorContext.create(entityInsertExpressionBuilder.getRuntimeContext(),false, ExecuteMethodEnum.INSERT),entities, entityInsertExpressionBuilder,fillAutoIncrement);
        }

        return 0;
    }

    @Override
    public Insertable<T> asTable(Function<String, String> tableNameAs) {
        entityInsertExpressionBuilder.getRecentlyTable().setTableNameAs(tableNameAs);
        return this;
    }

    @Override
    public Insertable<T> asAlias(String alias) {
        entityInsertExpressionBuilder.getRecentlyTable().asAlias(alias);
        return this;
    }

    @Override
    public Insertable<T> noInterceptor() {
        entityInsertExpressionBuilder.getExpressionContext().noInterceptor();
        return this;
    }
    @Override
    public Insertable<T> useInterceptor(String name) {
        entityInsertExpressionBuilder.getExpressionContext().useInterceptor(name);
        return this;
    }
    @Override
    public Insertable<T> noInterceptor(String name) {
        entityInsertExpressionBuilder.getExpressionContext().noInterceptor(name);
        return this;
    }

    @Override
    public Insertable<T> useInterceptor() {
        entityInsertExpressionBuilder.getExpressionContext().useInterceptor();
        return this;
    }

    @Override
    public Insertable<T> setSQLStrategy(boolean condition, SQLExecuteStrategyEnum sqlStrategy) {
        if(condition){
            entityInsertExpressionBuilder.getExpressionContext().useSQLStrategy(sqlStrategy);
        }
        return this;
    }

    public String toSQL(Object entity) {
        return toSQLWithParam(entity,null);
    }
    private String toSQLWithParam(Object entity, ToSQLContext toSQLContext){
        return entityInsertExpressionBuilder.toExpression(entity).toSQL(toSQLContext);
    }
}
