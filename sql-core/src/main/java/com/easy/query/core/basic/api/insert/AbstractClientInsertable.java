package com.easy.query.core.basic.api.insert;

import com.easy.query.core.basic.extension.generated.PrimaryKeyGenerator;
import com.easy.query.core.basic.extension.interceptor.EntityInterceptor;
import com.easy.query.core.basic.extension.interceptor.Interceptor;
import com.easy.query.core.basic.extension.track.EntityState;
import com.easy.query.core.basic.extension.track.TrackContext;
import com.easy.query.core.basic.extension.track.TrackManager;
import com.easy.query.core.basic.jdbc.executor.EntityExpressionPrepareExecutor;
import com.easy.query.core.basic.jdbc.executor.ExecutorContext;
import com.easy.query.core.basic.jdbc.parameter.DefaultToSQLContext;
import com.easy.query.core.basic.jdbc.parameter.ToSQLContext;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.enums.EasyBehaviorEnum;
import com.easy.query.core.enums.ExecuteMethodEnum;
import com.easy.query.core.enums.MultiTableTypeEnum;
import com.easy.query.core.enums.SQLExecuteStrategyEnum;
import com.easy.query.core.exception.EasyQueryInvalidOperationException;
import com.easy.query.core.expression.builder.impl.ConfigurerImpl;
import com.easy.query.core.expression.builder.impl.OnlySelectorImpl;
import com.easy.query.core.expression.lambda.SQLActionExpression1;
import com.easy.query.core.expression.parser.core.base.ColumnConfigurer;
import com.easy.query.core.expression.parser.core.base.ColumnOnlySelector;
import com.easy.query.core.expression.parser.core.base.impl.ColumnConfigurerImpl;
import com.easy.query.core.expression.parser.core.base.impl.ColumnOnlySelectorImpl;
import com.easy.query.core.expression.sql.builder.EntityInsertExpressionBuilder;
import com.easy.query.core.expression.sql.builder.EntityTableExpressionBuilder;
import com.easy.query.core.expression.sql.builder.internal.ContextConfigurer;
import com.easy.query.core.expression.sql.builder.internal.ContextConfigurerImpl;
import com.easy.query.core.metadata.ColumnMetadata;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.util.EasyCollectionUtil;
import com.easy.query.core.util.EasySQLSegmentUtil;
import com.easy.query.core.util.EasyTrackUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * @author xuejiaming
 * @FileName: AbstractInsertable.java
 * @Description: 文件说明
 * create time 2023/2/20 12:30
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

        this.entityTableExpressionBuilder = runtimeContext.getExpressionBuilderFactory().createEntityTableExpressionBuilder(entityMetadata, MultiTableTypeEnum.NONE, entityInsertExpressionBuilder.getExpressionContext());
        this.entityInsertExpressionBuilder.addSQLEntityTableExpression(entityTableExpressionBuilder);
    }

    @Override
    public EntityInsertExpressionBuilder getEntityInsertExpressionBuilder() {
        return entityInsertExpressionBuilder;
    }

    @Override
    public List<T> getEntities() {
        return entities;
    }

    @Override
    public ClientInsertable<T> insert(T entity) {
        if (entity != null) {
            entities.add(entity);
        }
        return this;
    }

    /**
     * 获取本次启用的拦截器
     *
     * @return
     */
    private List<EntityInterceptor> getEntityInterceptors() {
        List<EntityInterceptor> insertInterceptors = entityMetadata.getEntityInterceptors();
        if (EasyCollectionUtil.isNotEmpty(insertInterceptors)) {
            Predicate<Interceptor> interceptorFilter = entityInsertExpressionBuilder.getExpressionContext().getInterceptorFilter();
            return EasyCollectionUtil.filter(insertInterceptors, interceptorFilter::test);
        }
        return Collections.emptyList();

    }

    /**
     * 获取自定义columnMetadata
     *
     * @param entityMetadata
     * @return
     */
    private List<ColumnMetadata> getKeyColumnMetadataList(EntityMetadata entityMetadata) {
        if (entityMetadata.isHasPrimaryKeyGenerator()) {
            Collection<String> keyProperties = entityMetadata.getKeyProperties();
            List<ColumnMetadata> result = new ArrayList<>(keyProperties.size());
            for (String keyProperty : keyProperties) {
                ColumnMetadata columnMetadata = entityMetadata.getColumnNotNull(keyProperty);
                PrimaryKeyGenerator primaryKeyGenerator = columnMetadata.getPrimaryKeyGenerator();
                if (primaryKeyGenerator != null) {
                    result.add(columnMetadata);
                }
            }
            return result;
        }
        return Collections.emptyList();
    }

    protected void insertBefore() {
        //是否使用自定义插入策略
        boolean hasPrimaryKeyGenerator = entityMetadata.isHasPrimaryKeyGenerator();
        List<EntityInterceptor> entityInterceptors = getEntityInterceptors();
        boolean hasInterceptor = EasyCollectionUtil.isNotEmpty(entityInterceptors);
        if (hasPrimaryKeyGenerator || hasInterceptor) {
            Class<?> entityClass = entityMetadata.getEntityClass();
            List<ColumnMetadata> keyColumnMetadataList = getKeyColumnMetadataList(entityMetadata);
            for (T entity : entities) {
                //先进行primaryKey的设置在进行拦截器
                if (hasPrimaryKeyGenerator) {
                    for (ColumnMetadata columnMetadata : keyColumnMetadataList) {
                        PrimaryKeyGenerator primaryKeyGenerator = columnMetadata.getPrimaryKeyGenerator();
                        if(primaryKeyGenerator==null){
                            throw new EasyQueryInvalidOperationException("entity:"+entityClass+" column:"+columnMetadata.getPropertyName()+" primary key generator unknown");
                        }
                        primaryKeyGenerator.setPrimaryKey(entity, columnMetadata);
                    }
                }
                if (hasInterceptor) {
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
            ExecutorContext executorContext = ExecutorContext.create(entityInsertExpressionBuilder.getExpressionContext(), false, ExecuteMethodEnum.INSERT);
            insertBefore();
            EntityExpressionPrepareExecutor entityExpressionPrepareExecutor = entityInsertExpressionBuilder.getRuntimeContext().getEntityExpressionPrepareExecutor();
            long insert = entityExpressionPrepareExecutor.insert(executorContext, entities, entityInsertExpressionBuilder, fillAutoIncrement);
            addTrackEntities(entities);
            return insert;
        }

        return 0;
    }

    protected <TEntity> void addTrackEntities(List<TEntity> entities) {
        TrackContext trackContext = getTrackContextOrNull();
        if (trackContext != null) {
            for (TEntity trackEntity : entities) {
                String trackKey = EasyTrackUtil.getTrackKey(entityMetadata, trackEntity);
                if(trackKey!=null){
                    trackContext.addQueryTracking(trackEntity);
                }
            }
        }
    }

    protected TrackContext getTrackContextOrNull() {

        QueryRuntimeContext runtimeContext = entityInsertExpressionBuilder.getRuntimeContext();
        TrackManager trackManager = runtimeContext.getTrackManager();
        return trackManager.currentThreadTracking() ? trackManager.getCurrentTrackContext() : null;
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
    public ClientInsertable<T> asTableLink(Function<String, String> linkAs) {
        entityInsertExpressionBuilder.getRecentlyTable().setTableLinkAs(linkAs);
        return this;
    }

    @Override
    public ClientInsertable<T> asTableSegment(BiFunction<String, String, String> segmentAs) {
        entityInsertExpressionBuilder.getRecentlyTable().setTableSegmentAs(segmentAs);
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
    public ClientInsertable<T> onConflictThen(SQLActionExpression1<ColumnOnlySelector<T>> updateSetSelector, Collection<String> constraintProperties) {
        onConflictThen0(constraintProperties, updateSetSelector);
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
    public ClientInsertable<T> configure(SQLActionExpression1<ContextConfigurer> configurer) {
        if (configurer != null) {
            configurer.apply(new ContextConfigurerImpl(entityInsertExpressionBuilder.getExpressionContext()));
        }
        return this;
    }

    private void onConflictThen0(Collection<String> constraintProperties, SQLActionExpression1<ColumnOnlySelector<T>> setColumnSelector) {

        if (EasyCollectionUtil.isNotEmpty(constraintProperties)) {
            for (String constraintProperty : constraintProperties) {
                entityInsertExpressionBuilder.addDuplicateKey(constraintProperty);
            }
        }
        entityInsertExpressionBuilder.getDuplicateKeyUpdateColumns().clear();
        if (setColumnSelector != null) {
            ColumnOnlySelectorImpl<T> columnUpdateSetSelector = new ColumnOnlySelectorImpl<>(entityTableExpressionBuilder.getEntityTable(), new OnlySelectorImpl(entityInsertExpressionBuilder.getRuntimeContext(), entityInsertExpressionBuilder.getExpressionContext(), entityInsertExpressionBuilder.getDuplicateKeyUpdateColumns()));
            setColumnSelector.apply(columnUpdateSetSelector);
            if (EasySQLSegmentUtil.isNotEmpty(entityInsertExpressionBuilder.getDuplicateKeyUpdateColumns())) {
                insertOrUpdateBehavior();
            } else {
                insertOrIgnoreBehavior();
            }
        } else {
            insertOrIgnoreBehavior();
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
    public ClientInsertable<T> columnConfigure(SQLActionExpression1<ColumnConfigurer<T>> columnConfigureExpression) {
        columnConfigureExpression.apply(new ColumnConfigurerImpl<>(entityTableExpressionBuilder.getEntityTable(), new ConfigurerImpl(entityInsertExpressionBuilder)));
        return this;
    }

    @Override
    public String toSQL(T entity) {
        return toSQL(entity, DefaultToSQLContext.defaultToSQLContext(entityInsertExpressionBuilder.getExpressionContext().getTableContext()));
    }

    @Override
    public String toSQL(T entity, ToSQLContext toSQLContext) {
        return toSQLWithParam(entity, toSQLContext);
    }

    private String toSQLWithParam(T entity, ToSQLContext toSQLContext) {
        return entityInsertExpressionBuilder.toExpression(entity).toSQL(toSQLContext);
    }

}
