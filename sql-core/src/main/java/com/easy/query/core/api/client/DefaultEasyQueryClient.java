package com.easy.query.core.api.client;

import com.easy.query.core.api.SQLClientApiFactory;
import com.easy.query.core.basic.api.database.DatabaseCodeFirst;
import com.easy.query.core.basic.api.delete.ClientEntityDeletable;
import com.easy.query.core.basic.api.delete.ClientExpressionDeletable;
import com.easy.query.core.basic.api.flat.MapQueryable;
import com.easy.query.core.basic.api.flat.impl.DefaultMapQueryable;
import com.easy.query.core.basic.api.insert.ClientInsertable;
import com.easy.query.core.basic.api.insert.map.MapClientInsertable;
import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.basic.api.select.Query;
import com.easy.query.core.basic.api.update.ClientEntityUpdatable;
import com.easy.query.core.basic.api.update.ClientExpressionUpdatable;
import com.easy.query.core.basic.api.update.map.MapClientUpdatable;
import com.easy.query.core.basic.extension.track.EntityState;
import com.easy.query.core.basic.extension.track.TrackContext;
import com.easy.query.core.basic.extension.track.TrackManager;
import com.easy.query.core.basic.jdbc.conn.ConnectionManager;
import com.easy.query.core.basic.jdbc.parameter.SQLParameter;
import com.easy.query.core.basic.jdbc.tx.Transaction;
import com.easy.query.core.common.IncludeRelationIdContext;
import com.easy.query.core.configuration.EasyQueryOption;
import com.easy.query.core.configuration.LoadIncludeConfiguration;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.enums.RelationTypeEnum;
import com.easy.query.core.exception.EasyQueryInvalidOperationException;
import com.easy.query.core.expression.include.IncludeProcessor;
import com.easy.query.core.expression.include.IncludeProcessorFactory;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.lambda.SQLFuncExpression;
import com.easy.query.core.expression.sql.include.DefaultIncludeParserResult;
import com.easy.query.core.expression.sql.include.IncludeParserResult;
import com.easy.query.core.expression.sql.include.MultiRelationValue;
import com.easy.query.core.expression.sql.include.RelationEntityImpl;
import com.easy.query.core.expression.sql.include.RelationExtraEntity;
import com.easy.query.core.metadata.ColumnMetadata;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.metadata.EntityMetadataManager;
import com.easy.query.core.metadata.NavigateMetadata;
import com.easy.query.core.util.EasyCollectionUtil;
import com.easy.query.core.util.EasyIncludeUtil;
import com.easy.query.core.util.EasyObjectUtil;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author xuejiaming
 * @Date: 2023/3/6 13:30
 */
public class DefaultEasyQueryClient implements EasyQueryClient {
    private final QueryRuntimeContext runtimeContext;
    private final SQLClientApiFactory easySQLApiFactory;

    public DefaultEasyQueryClient(QueryRuntimeContext runtimeContext) {

        this.runtimeContext = runtimeContext;
        easySQLApiFactory = runtimeContext.getSQLClientApiFactory();
    }

    @Override
    public QueryRuntimeContext getRuntimeContext() {
        return runtimeContext;
    }

    @Override
    public <T> List<T> sqlEasyQuery(String sql, Class<T> clazz, List<SQLParameter> parameters) {
        return easySQLApiFactory.createJdbcExecutor(runtimeContext).sqlQuery(sql, clazz, parameters);
    }

    @Override
    public List<Map<String, Object>> sqlQueryMap(String sql, List<Object> parameters) {
        return easySQLApiFactory.createJdbcExecutor(runtimeContext).sqlQueryMap(sql, parameters);
    }

    @Override
    public long sqlExecute(String sql, List<Object> parameters) {
        return easySQLApiFactory.createJdbcExecutor(runtimeContext).sqlExecute(sql, parameters);
    }

    @Override
    public <T> ClientQueryable<T> queryable(Class<T> clazz) {
        return easySQLApiFactory.createQueryable(clazz, runtimeContext);
    }

    @Override
    public <T> ClientQueryable<T> queryable(String sql, Class<T> clazz, Collection<Object> sqlParams) {
        return easySQLApiFactory.createQueryable(sql, sqlParams, clazz, runtimeContext);
    }

    @Override
    public MapQueryable mapQueryable() {
        ClientQueryable<Map> queryable = easySQLApiFactory.createQueryable(Map.class, runtimeContext);
        return new DefaultMapQueryable(EasyObjectUtil.typeCastNullable(queryable));
    }

    @Override
    public MapQueryable mapQueryable(String sql) {
        ClientQueryable<Map> queryable = easySQLApiFactory.createQueryable(sql, Map.class, runtimeContext);
        return new DefaultMapQueryable(EasyObjectUtil.typeCastNullable(queryable));
    }

    @Override
    public Transaction beginTransaction(Integer isolationLevel) {
        ConnectionManager connectionManager = runtimeContext.getConnectionManager();
        return connectionManager.beginTransaction(isolationLevel);
    }

//    @Override
//    public <T> Insert<T> insert(Class<T> clazz) {
//        return new MySQLInsert<>(clazz,new InsertContext(runtimeContext));
//    }

    @Override
    public <T> ClientInsertable<T> insertable(T entity) {
        if (entity == null) {
            return easySQLApiFactory.createEmptyInsertable(runtimeContext);
        }
        return easySQLApiFactory.createInsertable((Class<T>) entity.getClass(), runtimeContext).insert(entity);
    }

    @Override
    public <T> ClientInsertable<T> insertable(Collection<T> entities) {
        if (entities == null || entities.isEmpty()) {
            return easySQLApiFactory.createEmptyInsertable(runtimeContext);
        }
        return easySQLApiFactory.createInsertable((Class<T>) entities.iterator().next().getClass(), runtimeContext).insert(entities);
    }

    @Override
    public MapClientInsertable<Map<String, Object>> mapInsertable(Map<String, Object> map) {
        if (map == null) {
            return easySQLApiFactory.createEmptyMapInsertable(runtimeContext);
        }
        return easySQLApiFactory.createMapInsertable(runtimeContext).insert(map);
    }

    @Override
    public MapClientInsertable<Map<String, Object>> mapInsertable(Collection<Map<String, Object>> maps) {
        if (maps == null || maps.isEmpty()) {
            return easySQLApiFactory.createEmptyMapInsertable(runtimeContext);
        }
        return easySQLApiFactory.createMapInsertable(runtimeContext).insert(maps);
    }

    @Override
    public <T> ClientExpressionUpdatable<T> updatable(Class<T> entityClass) {
        return easySQLApiFactory.createExpressionUpdatable(entityClass, runtimeContext);
    }

    @Override
    public <T> ClientEntityUpdatable<T> updatable(T entity) {
        if (entity == null) {
            return easySQLApiFactory.createEmptyEntityUpdatable();
        }
        return easySQLApiFactory.createEntityUpdatable(entity, runtimeContext);
    }

    @Override
    public <T> ClientEntityUpdatable<T> updatable(Collection<T> entities) {
        if (entities == null || entities.isEmpty()) {
            return easySQLApiFactory.createEmptyEntityUpdatable();
        }
        return easySQLApiFactory.createEntityUpdatable(entities, runtimeContext);
    }

    @Override
    public MapClientUpdatable<Map<String, Object>> mapUpdatable(Map<String, Object> map) {
        if (map == null) {
            return easySQLApiFactory.createEmptyMapUpdatable();
        }
        return easySQLApiFactory.createMapUpdatable(map, runtimeContext);
    }

    @Override
    public MapClientUpdatable<Map<String, Object>> mapUpdatable(Collection<Map<String, Object>> maps) {
        if (maps == null || maps.isEmpty()) {
            return easySQLApiFactory.createEmptyMapUpdatable();
        }
        return easySQLApiFactory.createMapUpdatable(maps, runtimeContext);
    }

    @Override
    public <T> ClientEntityDeletable<T> deletable(T entity) {
        if (entity == null) {
            return easySQLApiFactory.createEmptyEntityDeletable();
        }
        return easySQLApiFactory.createEntityDeletable(entity, runtimeContext);
    }

    @Override
    public <T> ClientEntityDeletable<T> deletable(Collection<T> entities) {
        if (entities == null || entities.isEmpty()) {
            return easySQLApiFactory.createEmptyEntityDeletable();
        }
        return easySQLApiFactory.createEntityDeletable(entities, runtimeContext);
    }


    @Override
    public <T> ClientExpressionDeletable<T> deletable(Class<T> entityClass) {
        return easySQLApiFactory.createExpressionDeletable(entityClass, runtimeContext);
    }

    @Override
    public boolean addTracking(Object entity) {
        TrackManager trackManager = runtimeContext.getTrackManager();
        TrackContext currentTrackContext = trackManager.getCurrentTrackContext();
        if (currentTrackContext != null) {
            return currentTrackContext.addTracking(entity);
        }
        return false;
    }

    @Override
    public boolean removeTracking(Object entity) {
        TrackManager trackManager = runtimeContext.getTrackManager();
        TrackContext currentTrackContext = trackManager.getCurrentTrackContext();
        if (currentTrackContext != null) {
            return currentTrackContext.removeTracking(entity);
        }
        return false;
    }

    @Override
    public EntityState getTrackEntityStateNotNull(Object entity) {
        TrackManager trackManager = runtimeContext.getTrackManager();
        TrackContext currentTrackContext = trackManager.getCurrentTrackContext();
        if (currentTrackContext == null) {
            throw new EasyQueryInvalidOperationException("currentTrackContext can not be null ");
        }
        return currentTrackContext.getTrackEntityStateNotNull(entity);
    }

    private <T> IncludeParserResult getIncludeParserResult(List<T> entities, NavigateMetadata navigateMetadata, LoadIncludeConfiguration loadIncludeConfiguration) {
        RelationTypeEnum relationType = navigateMetadata.getRelationType();
        List<ColumnMetadata> columnMetadataList = Arrays.stream(navigateMetadata.getSelfPropertiesOrPrimary()).map(self -> navigateMetadata.getEntityMetadata().getColumnNotNull(self)).collect(Collectors.toList());
        List<List<Object>> relationIds = entities.stream().map(o -> {
            List<Object> values = EasyCollectionUtil.select(columnMetadataList, (columnMetadata, i) -> columnMetadata.getGetterCaller().apply(o));
            return new MultiRelationValue(values);
        }).filter(o -> !o.isNull()).distinct().map(MultiRelationValue::getValues).collect(Collectors.toList());
        IncludeRelationIdContext includeRelationIdContext = new IncludeRelationIdContext();
        Integer queryRelationGroupSize = loadIncludeConfiguration.getGroupSize();
        List<Map<String, Object>> mappingRows = null;
        if (RelationTypeEnum.ManyToMany == relationType && navigateMetadata.getMappingClass() != null) {

            SQLFuncExpression<ClientQueryable<?>> mappingQueryable = ()-> queryable(navigateMetadata.getMappingClass())
                    .where(o -> {
                        o.relationIn(navigateMetadata.getSelfMappingProperties(), includeRelationIdContext.getRelationIds());
                        navigateMetadata.predicateMappingClassFilterApply(o);
                    })
                    .select(o -> {
                        for (String selfMappingProperty : navigateMetadata.getSelfMappingProperties()) {
                            o.column(selfMappingProperty);
                        }
                        for (String targetMappingProperty : navigateMetadata.getTargetMappingProperties()) {
                            o.column(targetMappingProperty);
                        }
//                        o.column(navigateMetadata.getSelfMappingProperties()).column(navigateMetadata.getTargetMappingProperties())
                    });

            mappingRows = EasyIncludeUtil.queryableExpressionGroupExecute(queryRelationGroupSize, mappingQueryable, includeRelationIdContext, relationIds, Query::toMaps);
            EntityMetadata mappingEntityMetadata = runtimeContext.getEntityMetadataManager().getEntityMetadata(navigateMetadata.getMappingClass());
            List<ColumnMetadata> columnMetadataTargetList = Arrays.stream(navigateMetadata.getTargetMappingProperties()).map(target -> mappingEntityMetadata.getColumnNotNull(target)).collect(Collectors.toList());

            List<List<Object>> targetIds = mappingRows.stream()
                    .map(o -> {
                        List<Object> values = EasyCollectionUtil.select(columnMetadataTargetList, (targetColumnMetadata, i) -> o.get(targetColumnMetadata.getName()));
                        return new MultiRelationValue(values);
                    }).filter(o -> !o.isNull())
                    .distinct()
                    .map(MultiRelationValue::getValues)
                    .collect(Collectors.toList());
            relationIds.clear();
            relationIds.addAll(targetIds);
        }

        SQLFuncExpression<ClientQueryable<?>> includeQueryableExpression = createIncludeQueryableExpression(includeRelationIdContext, navigateMetadata, loadIncludeConfiguration);


        List<?> entityResult = EasyIncludeUtil.queryableExpressionGroupExecute(queryRelationGroupSize, includeQueryableExpression, includeRelationIdContext, relationIds, q -> q.toList());
        EntityMetadata entityMetadata = runtimeContext.getEntityMetadataManager().getEntityMetadata(navigateMetadata.getNavigatePropertyType());
        List<RelationExtraEntity> includeResult = entityResult.stream().map(o -> new RelationEntityImpl(o, entityMetadata, runtimeContext.getRelationValueFactory())).collect(Collectors.toList());
        List<RelationExtraEntity> relationExtraEntities = entities.stream().map(o -> new RelationEntityImpl(o, navigateMetadata.getEntityMetadata(), runtimeContext.getRelationValueFactory())).collect(Collectors.toList());

        return new DefaultIncludeParserResult(entityMetadata, relationExtraEntities, navigateMetadata.getRelationType(),
                navigateMetadata.getPropertyName(),
                navigateMetadata.getNavigateOriginalPropertyType(),
                navigateMetadata.getNavigatePropertyType(),
                navigateMetadata.getSelfPropertiesOrPrimary(),
                navigateMetadata.getTargetPropertiesOrPrimary(runtimeContext),
                navigateMetadata.getMappingClass(),
                navigateMetadata.getSelfMappingProperties(),
                navigateMetadata.getTargetMappingProperties(),
                includeResult,
                mappingRows,
                navigateMetadata.getSetter(),navigateMetadata.getGetter(), null, null);
    }

    private SQLFuncExpression<ClientQueryable<?>> createIncludeQueryableExpression(IncludeRelationIdContext includeRelationIdContext, NavigateMetadata navigateMetadata, LoadIncludeConfiguration loadIncludeConfiguration) {

        Class<?> navigatePropertyType = navigateMetadata.getNavigatePropertyType();
        ClientQueryable<?> queryable = runtimeContext.getSQLClientApiFactory().createQueryable(EasyObjectUtil.typeCastNullable(navigatePropertyType), runtimeContext);
        Boolean tracking = loadIncludeConfiguration.getTracking();
        if (tracking != null) {
            if (tracking) {
                queryable.asTracking();
            } else {
                queryable.asNoTracking();
            }
        }
        SQLFuncExpression<ClientQueryable<?>> queryableExpression = () -> {
            List<List<Object>> relationIds = includeRelationIdContext.getRelationIds();
            return queryable.cloneQueryable().where(o -> {
                o.and(() -> {
                    o.relationIn(navigateMetadata.getTargetPropertiesOrPrimary(runtimeContext), relationIds);
                    if (navigateMetadata.getRelationType() != RelationTypeEnum.ManyToMany) {
                        navigateMetadata.predicateFilterApply(o);
                    }
                });
            });
        };
        return queryableExpression;
    }

    @Override
    public <T> void loadInclude(List<T> entities, String navigateProperty, SQLExpression1<LoadIncludeConfiguration> configure) {
        if (EasyCollectionUtil.isEmpty(entities)) {
            return;
        }
        T first = EasyCollectionUtil.first(entities);
        Class<?> firstClass = first.getClass();
        EntityMetadataManager entityMetadataManager = runtimeContext.getEntityMetadataManager();
        EntityMetadata entityMetadata = entityMetadataManager.getEntityMetadata(firstClass);
        NavigateMetadata navigateMetadata = entityMetadata.getNavigateNotNull(navigateProperty);
        LoadIncludeConfiguration loadIncludeConfiguration = new LoadIncludeConfiguration();
        EasyQueryOption easyQueryOption = runtimeContext.getQueryConfiguration().getEasyQueryOption();
        loadIncludeConfiguration.setGroupSize(easyQueryOption.getRelationGroupSize());
        if (configure != null) {
            configure.apply(loadIncludeConfiguration);
        }
        IncludeParserResult includeParserResult = getIncludeParserResult(entities, navigateMetadata, loadIncludeConfiguration);
        IncludeProcessorFactory includeProcessorFactory = runtimeContext.getIncludeProcessorFactory();
        IncludeProcessor includeProcess = includeProcessorFactory.createIncludeProcess(includeParserResult, runtimeContext);
        includeProcess.process();
    }

    @Override
    public DatabaseCodeFirst getDatabaseCodeFirst() {
        return easySQLApiFactory.createDatabaseCodeFirst(runtimeContext);
    }
}
