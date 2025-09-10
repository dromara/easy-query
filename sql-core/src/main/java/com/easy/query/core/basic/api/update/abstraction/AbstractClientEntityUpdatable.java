package com.easy.query.core.basic.api.update.abstraction;

import com.easy.query.core.basic.api.internal.AbstractSQLExecuteRows;
import com.easy.query.core.basic.api.update.ClientEntityUpdatable;
import com.easy.query.core.basic.extension.interceptor.EntityInterceptor;
import com.easy.query.core.basic.extension.interceptor.Interceptor;
import com.easy.query.core.basic.jdbc.executor.EntityExpressionPrepareExecutor;
import com.easy.query.core.basic.jdbc.executor.ExecutorContext;
import com.easy.query.core.basic.jdbc.parameter.DefaultToSQLContext;
import com.easy.query.core.basic.jdbc.parameter.ToSQLContext;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.enums.EasyBehaviorEnum;
import com.easy.query.core.enums.ExecuteMethodEnum;
import com.easy.query.core.enums.MultiTableTypeEnum;
import com.easy.query.core.enums.SQLExecuteStrategyEnum;
import com.easy.query.core.exception.EasyQueryException;
import com.easy.query.core.expression.builder.impl.ConfigurerImpl;
import com.easy.query.core.expression.builder.impl.OnlySelectorImpl;
import com.easy.query.core.expression.lambda.SQLActionExpression1;
import com.easy.query.core.expression.parser.core.base.ColumnConfigurer;
import com.easy.query.core.expression.parser.core.base.ColumnOnlySelector;
import com.easy.query.core.expression.parser.core.base.impl.ColumnConfigurerImpl;
import com.easy.query.core.expression.parser.core.base.impl.ColumnOnlySelectorImpl;
import com.easy.query.core.expression.sql.builder.EntityTableExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityUpdateExpressionBuilder;
import com.easy.query.core.expression.sql.builder.internal.ContextConfigurer;
import com.easy.query.core.expression.sql.builder.internal.ContextConfigurerImpl;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.util.EasyCollectionUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * @author xuejiaming
 * @FileName: AbstractUpdate.java
 * @Description: 文件说明
 * create time 2023/2/24 22:06
 */
public abstract class AbstractClientEntityUpdatable<T> extends AbstractSQLExecuteRows<ClientEntityUpdatable<T>> implements ClientEntityUpdatable<T> {
    protected final List<T> entities;
    protected final EntityTableExpressionBuilder table;
    protected final EntityMetadata entityMetadata;
    protected final EntityUpdateExpressionBuilder entityUpdateExpressionBuilder;

    public AbstractClientEntityUpdatable(Class<T> clazz, Collection<T> entities, EntityUpdateExpressionBuilder entityUpdateExpression) {
        super(entityUpdateExpression);
        if (entities == null || entities.isEmpty()) {
            throw new EasyQueryException("不支持空对象的update");
        }
        this.entities = new ArrayList<>(entities);

        this.entityUpdateExpressionBuilder = entityUpdateExpression;
        QueryRuntimeContext runtimeContext = entityUpdateExpressionBuilder.getRuntimeContext();
        entityMetadata = runtimeContext.getEntityMetadataManager().getEntityMetadata(clazz);
        entityMetadata.checkTable();
        table = runtimeContext.getExpressionBuilderFactory().createEntityTableExpressionBuilder(entityMetadata, MultiTableTypeEnum.NONE, entityUpdateExpressionBuilder.getExpressionContext());
        this.entityUpdateExpressionBuilder.addSQLEntityTableExpression(table);
    }

    @Override
    public List<T> getEntities() {
        return entities;
    }

    @Override
    public EntityUpdateExpressionBuilder getUpdateExpressionBuilder() {
        return entityUpdateExpressionBuilder;
    }

    @Override
    public long executeRows() {
        if (EasyCollectionUtil.isNotEmpty(entities)) {
            ExecutorContext executorContext = ExecutorContext.create(entityUpdateExpressionBuilder.getExpressionContext(), false, ExecuteMethodEnum.UPDATE);
            configureUpdate();
            EntityExpressionPrepareExecutor entityExpressionPrepareExecutor = entityUpdateExpressionBuilder.getRuntimeContext().getEntityExpressionPrepareExecutor();
            long executeRows = entityExpressionPrepareExecutor.executeRows(executorContext, entityUpdateExpressionBuilder, entities);
            refreshTrackEntities(entities);
            return executeRows;
        }
        return 0;
    }

    protected void configureUpdate() {
        List<EntityInterceptor> entityInterceptors = getEntityInterceptors();
        Class<?> entityClass = entityMetadata.getEntityClass();
        if (EasyCollectionUtil.isNotEmpty(entityInterceptors)) {
            for (T entity : entities) {
                for (EntityInterceptor entityInterceptor : entityInterceptors) {
                    entityInterceptor.configureUpdate(entityClass, entityUpdateExpressionBuilder, entity);
                }
            }
        }
    }

    protected List<EntityInterceptor> getEntityInterceptors() {
        List<EntityInterceptor> entityInterceptors = entityMetadata.getEntityInterceptors();
        if (EasyCollectionUtil.isNotEmpty(entityInterceptors)) {
            Predicate<Interceptor> interceptorFilter = entityUpdateExpressionBuilder.getExpressionContext().getInterceptorFilter();
            return EasyCollectionUtil.filter(entityInterceptors, interceptorFilter::test);
        }
        return Collections.emptyList();
    }

    @Override
    public ClientEntityUpdatable<T> setColumns(boolean condition, SQLActionExpression1<ColumnOnlySelector<T>>
            columnSelectorExpression) {
        if (condition) {
            ColumnOnlySelectorImpl<T> columnSelector = new ColumnOnlySelectorImpl<>(table.getEntityTable(), new OnlySelectorImpl(entityUpdateExpressionBuilder.getRuntimeContext(), entityUpdateExpressionBuilder.getExpressionContext(), entityUpdateExpressionBuilder.getSetColumns()));
            columnSelectorExpression.apply(columnSelector);
        }
        return this;
    }

    @Override
    public ClientEntityUpdatable<T> setIgnoreColumns(boolean condition, SQLActionExpression1<ColumnOnlySelector<T>>
            columnSelectorExpression) {
        if (condition) {
            ColumnOnlySelectorImpl<T> columnSelector = new ColumnOnlySelectorImpl<>(table.getEntityTable(), new OnlySelectorImpl(entityUpdateExpressionBuilder.getRuntimeContext(), entityUpdateExpressionBuilder.getExpressionContext(), entityUpdateExpressionBuilder.getSetIgnoreColumns()));
            columnSelectorExpression.apply(columnSelector);
        }
        return this;
    }

    @Override
    public ClientEntityUpdatable<T> whereColumns(boolean condition, SQLActionExpression1<ColumnOnlySelector<T>>
            columnSelectorExpression) {
        if (condition) {
            ColumnOnlySelectorImpl<T> columnSelector = new ColumnOnlySelectorImpl<>(table.getEntityTable(), new OnlySelectorImpl(entityUpdateExpressionBuilder.getRuntimeContext(), entityUpdateExpressionBuilder.getExpressionContext(), entityUpdateExpressionBuilder.getWhereColumns()));
            columnSelectorExpression.apply(columnSelector);
        }
        return this;
    }

    @Override
    public ClientEntityUpdatable<T> columnConfigure(SQLActionExpression1<ColumnConfigurer<T>> columnConfigureExpression) {
        ColumnConfigurerImpl<T> columnConfigurer = new ColumnConfigurerImpl<>(table.getEntityTable(), new ConfigurerImpl(this.entityUpdateExpressionBuilder));
        columnConfigureExpression.apply(columnConfigurer);
        return this;
    }

    @Override
    public ClientEntityUpdatable<T> asTable(Function<String, String> tableNameAs) {
        entityUpdateExpressionBuilder.getRecentlyTable().setTableNameAs(tableNameAs);
        return this;
    }

    @Override
    public ClientEntityUpdatable<T> asSchema(Function<String, String> schemaAs) {
        entityUpdateExpressionBuilder.getRecentlyTable().setSchemaAs(schemaAs);
        return this;
    }

    @Override
    public ClientEntityUpdatable<T> asAlias(String alias) {
        entityUpdateExpressionBuilder.getRecentlyTable().asAlias(alias);
        return this;
    }

    @Override
    public ClientEntityUpdatable<T> asTableLink(Function<String, String> linkAs) {
        entityUpdateExpressionBuilder.getRecentlyTable().setTableLinkAs(linkAs);
        return this;
    }

    @Override
    public ClientEntityUpdatable<T> asTableSegment(BiFunction<String, String, String> segmentAs) {
        entityUpdateExpressionBuilder.getRecentlyTable().setTableSegmentAs(segmentAs);
        return this;
    }

    @Override
    public ClientEntityUpdatable<T> configure(SQLActionExpression1<ContextConfigurer> configurer) {
        if (configurer != null) {
            configurer.apply(new ContextConfigurerImpl(entityUpdateExpressionBuilder.getExpressionContext()));
        }
        return this;
    }

    @Override
    public ClientEntityUpdatable<T> setSQLStrategy(boolean condition, SQLExecuteStrategyEnum sqlStrategy) {
        if (condition) {
            entityUpdateExpressionBuilder.getExpressionContext().useSQLStrategy(sqlStrategy);
        }
        return this;
    }

    @Override
    public ClientEntityUpdatable<T> ignoreVersion(boolean ignored) {
        if (ignored) {
            entityUpdateExpressionBuilder.getExpressionContext().getBehavior().addBehavior(EasyBehaviorEnum.IGNORE_VERSION);
        } else {
            entityUpdateExpressionBuilder.getExpressionContext().getBehavior().removeBehavior(EasyBehaviorEnum.IGNORE_VERSION);
        }
        return this;
    }

    @Override
    public String toSQL(Object entity) {
        return toSQL(entity, DefaultToSQLContext.defaultToSQLContext(entityUpdateExpressionBuilder.getExpressionContext().getTableContext()));
    }

    @Override
    public String toSQL(Object entity, ToSQLContext toSQLContext) {
        return entityUpdateExpressionBuilder.toExpression(entity).toSQL(toSQLContext);
    }
}
