package com.easy.query.core.basic.api.update.abstraction;

import com.easy.query.core.basic.api.internal.AbstractSQLExecuteRows;
import com.easy.query.core.basic.api.update.ClientEntityUpdatable;
import com.easy.query.core.basic.extension.track.TrackContext;
import com.easy.query.core.basic.extension.track.TrackManager;
import com.easy.query.core.basic.jdbc.executor.EntityExpressionExecutor;
import com.easy.query.core.basic.jdbc.executor.ExecutorContext;
import com.easy.query.core.basic.jdbc.parameter.DefaultToSQLContext;
import com.easy.query.core.basic.jdbc.parameter.ToSQLContext;
import com.easy.query.core.basic.extension.interceptor.EntityInterceptor;
import com.easy.query.core.basic.extension.interceptor.Interceptor;
import com.easy.query.core.configuration.QueryConfiguration;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.enums.ExecuteMethodEnum;
import com.easy.query.core.enums.MultiTableTypeEnum;
import com.easy.query.core.enums.SQLExecuteStrategyEnum;
import com.easy.query.core.exception.EasyQueryException;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.parser.core.base.ColumnSelector;
import com.easy.query.core.expression.parser.core.base.impl.ColumnSetSelectorImpl;
import com.easy.query.core.expression.sql.builder.EntityTableExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityUpdateExpressionBuilder;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.util.EasyCollectionUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author xuejiaming
 * @FileName: AbstractUpdate.java
 * @Description: 文件说明
 * @Date: 2023/2/24 22:06
 */
public abstract class AbstractClientEntityUpdatable<T> extends AbstractSQLExecuteRows<ClientEntityUpdatable<T>> implements ClientEntityUpdatable<T> {
    protected final List<T> entities = new ArrayList<>();
    protected final EntityTableExpressionBuilder table;
    protected final EntityMetadata entityMetadata;
    protected final EntityUpdateExpressionBuilder entityUpdateExpressionBuilder;

    public AbstractClientEntityUpdatable(Collection<T> entities, EntityUpdateExpressionBuilder entityUpdateExpression) {
        super(entityUpdateExpression);
        if (entities == null || entities.isEmpty()) {
            throw new EasyQueryException("不支持空对象的update");
        }
        this.entities.addAll(entities);

        Class<?> clazz = entities.iterator().next().getClass();
        this.entityUpdateExpressionBuilder = entityUpdateExpression;
        QueryRuntimeContext runtimeContext = entityUpdateExpressionBuilder.getRuntimeContext();
        entityMetadata = runtimeContext.getEntityMetadataManager().getEntityMetadata(clazz);
        entityMetadata.checkTable();
        table = runtimeContext.getExpressionBuilderFactory().createEntityTableExpressionBuilder(entityMetadata, 0, MultiTableTypeEnum.NONE, runtimeContext);
        this.entityUpdateExpressionBuilder.addSQLEntityTableExpression(table);
    }

    @Override
    public long executeRows() {
        if (EasyCollectionUtil.isNotEmpty(entities)) {
            List<Object> trackEntities = configureUpdateAndGetTrackEntities();
            EntityExpressionExecutor entityExpressionExecutor = entityUpdateExpressionBuilder.getRuntimeContext().getEntityExpressionExecutor();
            long executeRows = entityExpressionExecutor.executeRows(ExecutorContext.create(entityUpdateExpressionBuilder.getRuntimeContext(), false, ExecuteMethodEnum.UPDATE), entityUpdateExpressionBuilder, entities);
            removeTrackEntities(trackEntities);
            return executeRows;
        }
        return 0;
    }

    protected void removeTrackEntities(List<Object> trackEntities) {
        if (EasyCollectionUtil.isNotEmpty(trackEntities)) {
            TrackContext trackContext = getTrackContextOrNull();
            if (trackContext != null) {
                for (Object trackEntity : trackEntities) {
                    trackContext.removeTracking(trackEntity);
                }
            }
        }
    }

    protected TrackContext getTrackContextOrNull() {

        QueryRuntimeContext runtimeContext = entityUpdateExpressionBuilder.getRuntimeContext();
        TrackManager trackManager = runtimeContext.getTrackManager();
        return trackManager.currentThreadTracking() ? trackManager.getCurrentTrackContext() : null;
    }

    protected List<Object> configureUpdateAndGetTrackEntities() {
        List<EntityInterceptor> entityInterceptors = getEntityInterceptors();
        Class<?> entityClass = entityMetadata.getEntityClass();
        TrackContext trackContext = getTrackContextOrNull();
        List<Object> trackEntities = new LinkedList<>();
        for (T entity : entities) {
            for (EntityInterceptor entityInterceptor : entityInterceptors) {
                entityInterceptor.configureUpdate(entityClass, entityUpdateExpressionBuilder, entity);
            }
            if (trackContext != null) {
                if (trackContext.isTrack(entity)) {
                    trackEntities.add(entity);
                }
            }
        }
        return trackEntities;
    }

    protected List<EntityInterceptor> getEntityInterceptors() {
        List<String> updateInterceptors = entityMetadata.getEntityInterceptors();
        if (EasyCollectionUtil.isNotEmpty(updateInterceptors)) {
            QueryConfiguration easyQueryConfiguration = entityUpdateExpressionBuilder.getRuntimeContext().getQueryConfiguration();
            return entityUpdateExpressionBuilder.getExpressionContext().getInterceptorFilter(updateInterceptors)
                    .map(interceptor -> (EntityInterceptor) easyQueryConfiguration.getEasyInterceptor(interceptor)).filter(Interceptor::enable).collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    @Override
    public ClientEntityUpdatable<T> setColumns(boolean condition, SQLExpression1<ColumnSelector<T>>
            columnSelectorExpression) {
        if (condition) {
            ColumnSetSelectorImpl<T> columnSelector = new ColumnSetSelectorImpl<>(table.getIndex(), entityUpdateExpressionBuilder, entityUpdateExpressionBuilder.getSetColumns());
            columnSelectorExpression.apply(columnSelector);
        }
        return this;
    }

    @Override
    public ClientEntityUpdatable<T> setIgnoreColumns(boolean condition, SQLExpression1<ColumnSelector<T>>
            columnSelectorExpression) {
        if (condition) {
            ColumnSetSelectorImpl<T> columnSelector = new ColumnSetSelectorImpl<>(table.getIndex(), entityUpdateExpressionBuilder, entityUpdateExpressionBuilder.getSetIgnoreColumns());
            columnSelectorExpression.apply(columnSelector);
        }
        return this;
    }

    @Override
    public ClientEntityUpdatable<T> whereColumns(boolean condition, SQLExpression1<ColumnSelector<T>>
            columnSelectorExpression) {
        if (condition) {
            ColumnSetSelectorImpl<T> columnSelector = new ColumnSetSelectorImpl<>(table.getIndex(), entityUpdateExpressionBuilder, entityUpdateExpressionBuilder.getWhereColumns());
            columnSelectorExpression.apply(columnSelector);
        }
        return this;
    }

    @Override
    public ClientEntityUpdatable<T> asTable(Function<String, String> tableNameAs) {
        entityUpdateExpressionBuilder.getRecentlyTable().setTableNameAs(tableNameAs);
        return this;
    }

    @Override
    public ClientEntityUpdatable<T> asAlias(String alias) {
        entityUpdateExpressionBuilder.getRecentlyTable().asAlias(alias);
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
    public String toSQL(Object entity) {
        return toSQLWithParam(entity, DefaultToSQLContext.defaultToSQLContext(entityUpdateExpressionBuilder.getExpressionContext().getTableContext()));
    }

    private String toSQLWithParam(Object entity, ToSQLContext toSQLContext) {
        return entityUpdateExpressionBuilder.toExpression(entity).toSQL(toSQLContext);
    }
}
