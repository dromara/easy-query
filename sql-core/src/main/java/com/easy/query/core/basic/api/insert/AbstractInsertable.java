package com.easy.query.core.basic.api.insert;

import com.easy.query.core.basic.jdbc.executor.EntityExpressionExecutor;
import com.easy.query.core.basic.jdbc.parameter.SqlParameterCollector;
import com.easy.query.core.basic.plugin.interceptor.EasyInterceptorEntry;
import com.easy.query.core.configuration.EasyQueryConfiguration;
import com.easy.query.core.enums.ExecuteMethodEnum;
import com.easy.query.core.enums.SqlExecuteStrategyEnum;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.enums.MultiTableTypeEnum;
import com.easy.query.core.basic.plugin.interceptor.EasyEntityInterceptor;
import com.easy.query.core.expression.sql.builder.impl.TableExpressionBuilder;
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
    protected final EntityInsertExpressionBuilder entityInsertExpression;

    public AbstractInsertable(Class<T> clazz, EntityInsertExpressionBuilder entityInsertExpression) {
        this.entityInsertExpression = entityInsertExpression;
        this.entities = new ArrayList<>();
        entityMetadata = this.entityInsertExpression.getRuntimeContext().getEntityMetadataManager().getEntityMetadata(clazz);
        entityMetadata.checkTable();

        EntityTableExpressionBuilder table = new TableExpressionBuilder(entityMetadata, 0, null, MultiTableTypeEnum.FROM,entityInsertExpression.getRuntimeContext());
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
        //是否使用自定义插入策略
        List<EasyInterceptorEntry> insertInterceptors = entityMetadata.getEntityInterceptors();
        if (EasyCollectionUtil.isNotEmpty(insertInterceptors)) {
            EasyQueryConfiguration easyQueryConfiguration = entityInsertExpression.getRuntimeContext().getEasyQueryConfiguration();
            List<EasyEntityInterceptor> entityInterceptors = entityInsertExpression.getExpressionContext().getInterceptorFilter(insertInterceptors)
                    .map(interceptor -> (EasyEntityInterceptor) easyQueryConfiguration.getEasyInterceptor(interceptor.getName())).collect(Collectors.toList());
            if (EasyCollectionUtil.isNotEmpty(entityInterceptors)) {
                Class<?> entityClass = entityMetadata.getEntityClass();
                for (T entity : entities) {
                    for (EasyEntityInterceptor entityInterceptor : entityInterceptors) {
                        entityInterceptor.configureInsert(entityClass, entityInsertExpression, entity);
                    }
                }
            }
        }
    }

    @Override
    public long executeRows(boolean fillAutoIncrement) {
        if (!entities.isEmpty()) {
            insertBefore();
            EntityExpressionExecutor entityExpressionExecutor = entityInsertExpression.getRuntimeContext().getEntityExpressionExecutor();
            return entityExpressionExecutor.insert(ExecutorContext.create(entityInsertExpression.getRuntimeContext(),false, ExecuteMethodEnum.INSERT),entities,entityInsertExpression,fillAutoIncrement);
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

    public String toSql(Object entity) {
        return toSqlWithParam(entity,null);
    }
    private String toSqlWithParam(Object entity,SqlParameterCollector sqlParameterCollector){
        return entityInsertExpression.toExpression(entity).toSql(sqlParameterCollector);
    }
}
