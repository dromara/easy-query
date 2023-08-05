package com.easy.query.core.basic.api.insert;

import com.easy.query.core.basic.extension.interceptor.EntityInterceptor;
import com.easy.query.core.basic.extension.interceptor.Interceptor;
import com.easy.query.core.basic.jdbc.executor.EntityExpressionExecutor;
import com.easy.query.core.basic.jdbc.executor.ExecutorContext;
import com.easy.query.core.basic.jdbc.parameter.DefaultToSQLContext;
import com.easy.query.core.basic.jdbc.parameter.PropertySQLParameter;
import com.easy.query.core.basic.jdbc.parameter.SQLParameter;
import com.easy.query.core.basic.jdbc.parameter.ToSQLContext;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.enums.EasyBehaviorEnum;
import com.easy.query.core.enums.ExecuteMethodEnum;
import com.easy.query.core.enums.MultiTableTypeEnum;
import com.easy.query.core.enums.SQLExecuteStrategyEnum;
import com.easy.query.core.expression.builder.impl.UpdateSetSelectorImpl;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLExpression2;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.parser.core.base.ColumnUpdateSetSelector;
import com.easy.query.core.expression.parser.core.base.impl.ColumnUpdateSetSelectorImpl;
import com.easy.query.core.expression.parser.core.base.scec.SQLNativePropertyExpressionContext;
import com.easy.query.core.expression.parser.core.base.scec.SQLNativePropertyExpressionContextImpl;
import com.easy.query.core.expression.segment.impl.SQLNativeInsertSegmentImpl;
import com.easy.query.core.expression.segment.scec.context.SQLNativeInsertExpressionContextImpl;
import com.easy.query.core.expression.sql.builder.EntityInsertExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityTableExpressionBuilder;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.util.EasyCollectionUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * @author xuejiaming
 * @FileName: AbstractInsertable.java
 * @Description: 文件说明
 * @Date: 2023/2/20 12:30
 */
public abstract class AbstractClientInsertable<T> implements ClientInsertable<T> {
    protected final List<T> entities;
    protected final EntityMetadata entityMetadata;
    protected final EntityInsertExpressionBuilder entityInsertExpressionBuilder;
    protected final EntityTableExpressionBuilder entityTableExpressionBuilder;

    public AbstractClientInsertable(Class<T> clazz, EntityInsertExpressionBuilder entityInsertExpressionBuilder) {
        this.entityInsertExpressionBuilder = entityInsertExpressionBuilder;
        this.entities = new ArrayList<>();
        QueryRuntimeContext runtimeContext = entityInsertExpressionBuilder.getRuntimeContext();
        entityMetadata = runtimeContext.getEntityMetadataManager().getEntityMetadata(clazz);
        entityMetadata.checkTable();

        this.entityTableExpressionBuilder = runtimeContext.getExpressionBuilderFactory().createEntityTableExpressionBuilder(entityMetadata, MultiTableTypeEnum.NONE, runtimeContext);
        this.entityInsertExpressionBuilder.addSQLEntityTableExpression(entityTableExpressionBuilder);
    }

    @Override
    public ClientInsertable<T> insert(T entity) {
        if (entity != null) {
            entities.add(entity);
        }
        return this;
    }

    protected void insertBefore() {
        //是否使用自定义插入策略
        List<EntityInterceptor> insertInterceptors = entityMetadata.getEntityInterceptors();
        if (EasyCollectionUtil.isNotEmpty(insertInterceptors)) {
            Predicate<Interceptor> interceptorFilter = entityInsertExpressionBuilder.getExpressionContext().getInterceptorFilter();
            List<EntityInterceptor> entityInterceptors = EasyCollectionUtil.filter(insertInterceptors, interceptorFilter::test);
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
            return entityExpressionExecutor.insert(ExecutorContext.create(entityInsertExpressionBuilder.getRuntimeContext(), false, ExecuteMethodEnum.INSERT), entities, entityInsertExpressionBuilder, fillAutoIncrement);
        }

        return 0;
    }

    @Override
    public ClientInsertable<T> asTable(Function<String, String> tableNameAs) {
        entityInsertExpressionBuilder.getRecentlyTable().setTableNameAs(tableNameAs);
        return this;
    }

    @Override
    public ClientInsertable<T> asSchema(Function<String, String> schemaAs) {
        entityInsertExpressionBuilder.getRecentlyTable().setSchemaAs(schemaAs);
        return this;
    }

    @Override
    public ClientInsertable<T> asAlias(String alias) {
        entityInsertExpressionBuilder.getRecentlyTable().asAlias(alias);
        return this;
    }

    @Override
    public ClientInsertable<T> noInterceptor() {
        entityInsertExpressionBuilder.getExpressionContext().noInterceptor();
        return this;
    }

    @Override
    public ClientInsertable<T> useInterceptor(String name) {
        entityInsertExpressionBuilder.getExpressionContext().useInterceptor(name);
        return this;
    }

    @Override
    public ClientInsertable<T> noInterceptor(String name) {
        entityInsertExpressionBuilder.getExpressionContext().noInterceptor(name);
        return this;
    }

    @Override
    public ClientInsertable<T> useInterceptor() {
        entityInsertExpressionBuilder.getExpressionContext().useInterceptor();
        return this;
    }

    @Override
    public ClientInsertable<T> setSQLStrategy(boolean condition, SQLExecuteStrategyEnum sqlStrategy) {
        if (condition) {
            entityInsertExpressionBuilder.getExpressionContext().useSQLStrategy(sqlStrategy);
        }
        return this;
    }

    @Override
    public ClientInsertable<T> onDuplicateKeyIgnore() {
        insertOrIgnoreBehavior();
        return this;
    }

    private void insertOrIgnoreBehavior() {
        entityInsertExpressionBuilder.getExpressionContext().getBehavior().removeBehavior(EasyBehaviorEnum.ON_DUPLICATE_KEY_UPDATE);
        entityInsertExpressionBuilder.getExpressionContext().getBehavior().addBehavior(EasyBehaviorEnum.ON_DUPLICATE_KEY_IGNORE);
    }

    private void insertOrUpdateBehavior() {
        entityInsertExpressionBuilder.getExpressionContext().getBehavior().removeBehavior(EasyBehaviorEnum.ON_DUPLICATE_KEY_IGNORE);
        entityInsertExpressionBuilder.getExpressionContext().getBehavior().addBehavior(EasyBehaviorEnum.ON_DUPLICATE_KEY_UPDATE);
    }

    @Override
    public ClientInsertable<T> onConflictDoUpdate() {
        doOnDuplicateKeyUpdate(null, null);
        return this;
    }

    @Override
    public ClientInsertable<T> onConflictDoUpdate(String constraintProperty) {
        doOnDuplicateKeyUpdate(constraintProperty, null);
        return this;
    }

    @Override
    public ClientInsertable<T> onConflictDoUpdate(String constraintProperty, SQLExpression1<ColumnUpdateSetSelector<T>> setColumnSelector) {
        doOnDuplicateKeyUpdate(constraintProperty, setColumnSelector);
        return this;
    }

    @Override
    public ClientInsertable<T> onConflictDoUpdate(SQLExpression1<ColumnUpdateSetSelector<T>> setColumnSelector) {
        doOnDuplicateKeyUpdate(null, setColumnSelector);
        return this;
    }

    @Override
    public ClientInsertable<T> onDuplicateKeyUpdate() {
        doOnDuplicateKeyUpdate(null, null);
        return this;
    }

    @Override
    public ClientInsertable<T> onDuplicateKeyUpdate(SQLExpression1<ColumnUpdateSetSelector<T>> setColumnSelector) {
        doOnDuplicateKeyUpdate(null, setColumnSelector);
        return this;
    }

    private void doOnDuplicateKeyUpdate(String constraintProperty, SQLExpression1<ColumnUpdateSetSelector<T>> setColumnSelector) {
        insertOrUpdateBehavior();
        entityInsertExpressionBuilder.setDuplicateKey(constraintProperty);
        entityInsertExpressionBuilder.getDuplicateKeyUpdateColumns().clear();
        if (setColumnSelector != null) {
            ColumnUpdateSetSelectorImpl<T> columnUpdateSetSelector = new ColumnUpdateSetSelectorImpl<>(entityTableExpressionBuilder.getEntityTable(), new UpdateSetSelectorImpl(entityInsertExpressionBuilder.getRuntimeContext(), entityInsertExpressionBuilder.getDuplicateKeyUpdateColumns()));
            setColumnSelector.apply(columnUpdateSetSelector);
        }

    }

    @Override
    public ClientInsertable<T> batch(boolean use) {
        if (use) {
            entityInsertExpressionBuilder.getExpressionContext().getBehavior().removeBehavior(EasyBehaviorEnum.EXECUTE_NO_BATCH);
            entityInsertExpressionBuilder.getExpressionContext().getBehavior().addBehavior(EasyBehaviorEnum.EXECUTE_BATCH);
        } else {
            entityInsertExpressionBuilder.getExpressionContext().getBehavior().removeBehavior(EasyBehaviorEnum.EXECUTE_BATCH);
            entityInsertExpressionBuilder.getExpressionContext().getBehavior().addBehavior(EasyBehaviorEnum.EXECUTE_NO_BATCH);
        }
        return this;
    }

    @Override
    public ClientInsertable<T> columnSQL(String property, String sqlSegment, SQLExpression2<SQLNativePropertyExpressionContext, SQLParameter> contextConsume) {
        Objects.requireNonNull(contextConsume, "sql native context consume cannot be null");
        TableAvailable entityTable = entityTableExpressionBuilder.getEntityTable();
        SQLNativeInsertExpressionContextImpl sqlNativeExpressionContext = new SQLNativeInsertExpressionContextImpl();
        SQLNativePropertyExpressionContextImpl sqlNativePropertyExpressionContext = new SQLNativePropertyExpressionContextImpl(entityTable, sqlNativeExpressionContext);
        contextConsume.apply(sqlNativePropertyExpressionContext, new PropertySQLParameter(entityTable, property));
        SQLNativeInsertSegmentImpl sqlNativeInsertSegment = new SQLNativeInsertSegmentImpl(entityTable, property, entityInsertExpressionBuilder.getRuntimeContext(), sqlSegment, sqlNativeExpressionContext);
        entityInsertExpressionBuilder.insertColumnSQLs().put(property, sqlNativeInsertSegment);
        return this;
    }

    @Override
    public String toSQL(Object entity) {
        return toSQL(entity, DefaultToSQLContext.defaultToSQLContext(entityInsertExpressionBuilder.getExpressionContext().getTableContext()));
    }

    @Override
    public String toSQL(Object entity, ToSQLContext toSQLContext) {
        return toSQLWithParam(entity, toSQLContext);
    }

    private String toSQLWithParam(Object entity, ToSQLContext toSQLContext) {
        return entityInsertExpressionBuilder.toExpression(entity).toSQL(toSQLContext);
    }

}
