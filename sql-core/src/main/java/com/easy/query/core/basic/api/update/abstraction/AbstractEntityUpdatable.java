package com.easy.query.core.basic.api.update.abstraction;

import com.easy.query.core.basic.api.internal.AbstractSqlExecuteRows;
import com.easy.query.core.basic.api.update.EntityUpdatable;
import com.easy.query.core.basic.jdbc.executor.EntityExpressionExecutor;
import com.easy.query.core.basic.jdbc.parameter.SqlParameterCollector;
import com.easy.query.core.basic.plugin.interceptor.EasyInterceptorEntry;
import com.easy.query.core.configuration.EasyQueryConfiguration;
import com.easy.query.core.enums.MultiTableTypeEnum;
import com.easy.query.core.enums.SqlExecuteStrategyEnum;
import com.easy.query.core.exception.EasyQueryException;
import com.easy.query.core.expression.lambda.SqlExpression;
import com.easy.query.core.expression.parser.abstraction.SqlColumnSelector;
import com.easy.query.core.expression.parser.impl.DefaultSqlColumnSetSelector;
import com.easy.query.core.basic.plugin.interceptor.EasyEntityInterceptor;
import com.easy.query.core.expression.sql.builder.impl.TableExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityTableExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityUpdateExpressionBuilder;
import com.easy.query.core.basic.jdbc.executor.ExecutorContext;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.util.EasyCollectionUtil;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author xuejiaming
 * @FileName: AbstractUpdate.java
 * @Description: 文件说明
 * @Date: 2023/2/24 22:06
 */
public abstract class AbstractEntityUpdatable<T> extends AbstractSqlExecuteRows<EntityUpdatable<T>> implements EntityUpdatable<T> {
    protected final List<T> entities = new ArrayList<>();
    protected final EntityTableExpressionBuilder table;
    protected final EntityMetadata entityMetadata;
    protected final EntityUpdateExpressionBuilder entityUpdateExpressionBuilder;

    public AbstractEntityUpdatable(Collection<T> entities, EntityUpdateExpressionBuilder entityUpdateExpression) {
        super(entityUpdateExpression);
        if (entities == null || entities.isEmpty()) {
            throw new EasyQueryException("不支持空对象的update");
        }
        this.entities.addAll(entities);

        Class<?> clazz = entities.iterator().next().getClass();
        this.entityUpdateExpressionBuilder = entityUpdateExpression;

        entityMetadata = this.entityUpdateExpressionBuilder.getRuntimeContext().getEntityMetadataManager().getEntityMetadata(clazz);
        entityMetadata.checkTable();
        table = new TableExpressionBuilder(entityMetadata, 0, null, MultiTableTypeEnum.FROM,entityUpdateExpressionBuilder.getRuntimeContext());
        this.entityUpdateExpressionBuilder.addSqlEntityTableExpression(table);
    }
    @Override
    public long executeRows() {
        if (!entities.isEmpty()) {
            updateBefore();
            EntityExpressionExecutor entityExpressionExecutor = entityUpdateExpressionBuilder.getRuntimeContext().getEntityExpressionExecutor();
            return entityExpressionExecutor.executeRows(ExecutorContext.create(entityUpdateExpressionBuilder.getRuntimeContext(),true), entityUpdateExpressionBuilder,entities);
        }
        return 0;
    }

    protected void updateBefore() {
        List<EasyInterceptorEntry> updateInterceptors = entityMetadata.getEntityInterceptors();
        if (EasyCollectionUtil.isNotEmpty(updateInterceptors)) {
            EasyQueryConfiguration easyQueryConfiguration = entityUpdateExpressionBuilder.getRuntimeContext().getEasyQueryConfiguration();
            List<EasyEntityInterceptor> entityInterceptors = entityUpdateExpressionBuilder.getExpressionContext().getInterceptorFilter(updateInterceptors)
                    .map(interceptor -> (EasyEntityInterceptor) easyQueryConfiguration.getEasyInterceptor(interceptor.getName())).collect(Collectors.toList());
            if (EasyCollectionUtil.isNotEmpty(entityInterceptors)) {
                Class<?> entityClass = entityMetadata.getEntityClass();
                for (T entity : entities) {
                    for (EasyEntityInterceptor entityInterceptor : entityInterceptors) {
                        entityInterceptor.configureUpdate(entityClass, entityUpdateExpressionBuilder, entity);
                    }
                }
            }
        }
    }

    @Override
    public EntityUpdatable<T> setColumns(boolean condition, SqlExpression<SqlColumnSelector<T>>
            columnSelectorExpression) {
        if (condition) {
            DefaultSqlColumnSetSelector<T> columnSelector = new DefaultSqlColumnSetSelector<>(0, entityUpdateExpressionBuilder, entityUpdateExpressionBuilder.getSetColumns());
            columnSelectorExpression.apply(columnSelector);
        }
        return this;
    }

    @Override
    public EntityUpdatable<T> setIgnoreColumns(boolean condition, SqlExpression<SqlColumnSelector<T>>
            columnSelectorExpression) {
        if (condition) {
            DefaultSqlColumnSetSelector<T> columnSelector = new DefaultSqlColumnSetSelector<>(0, entityUpdateExpressionBuilder, entityUpdateExpressionBuilder.getSetIgnoreColumns());
            columnSelectorExpression.apply(columnSelector);
        }
        return this;
    }

    @Override
    public EntityUpdatable<T> whereColumns(boolean condition, SqlExpression<SqlColumnSelector<T>>
            columnSelectorExpression) {
        if (condition) {
            DefaultSqlColumnSetSelector<T> columnSelector = new DefaultSqlColumnSetSelector<>(0, entityUpdateExpressionBuilder, entityUpdateExpressionBuilder.getWhereColumns());
            columnSelectorExpression.apply(columnSelector);
        }
        return this;
    }

    @Override
    public EntityUpdatable<T> asTable(Function<String, String> tableNameAs) {
        entityUpdateExpressionBuilder.getRecentlyTable().setTableNameAs(tableNameAs);
        return this;
    }

    @Override
    public EntityUpdatable<T> setSqlStrategy(boolean condition, SqlExecuteStrategyEnum sqlStrategy) {
        if (condition) {
            entityUpdateExpressionBuilder.getExpressionContext().useSqlStrategy(sqlStrategy);
        }
        return this;
    }

    public String toSql(Object entity) {
        return toSqlWithParam(entity,null);
    }
    private String toSqlWithParam(Object entity, SqlParameterCollector sqlParameterCollector){
        return entityUpdateExpressionBuilder.toExpression(entity).toSql(sqlParameterCollector);
    }
}
