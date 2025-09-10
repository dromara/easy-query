package com.easy.query.core.api.client;

import com.easy.query.core.annotation.Table;
import com.easy.query.core.api.SQLClientApiFactory;
import com.easy.query.core.basic.api.database.CodeFirstCommand;
import com.easy.query.core.basic.api.database.DatabaseCodeFirst;
import com.easy.query.core.basic.api.delete.ClientEntityDeletable;
import com.easy.query.core.basic.api.delete.ClientExpressionDeletable;
import com.easy.query.core.basic.api.flat.MapQueryable;
import com.easy.query.core.basic.api.flat.impl.DefaultMapQueryable;
import com.easy.query.core.basic.api.insert.ClientInsertable;
import com.easy.query.core.basic.api.insert.map.MapClientInsertable;
import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.basic.api.update.ClientEntityUpdatable;
import com.easy.query.core.basic.api.update.ClientExpressionUpdatable;
import com.easy.query.core.basic.api.update.map.MapClientUpdatable;
import com.easy.query.core.basic.extension.track.EntityState;
import com.easy.query.core.basic.extension.track.TrackContext;
import com.easy.query.core.basic.extension.track.TrackManager;
import com.easy.query.core.basic.jdbc.conn.ConnectionManager;
import com.easy.query.core.basic.jdbc.parameter.SQLParameter;
import com.easy.query.core.basic.jdbc.tx.Transaction;
import com.easy.query.core.configuration.LoadIncludeConfiguration;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.enums.ContextTypeEnum;
import com.easy.query.core.exception.EasyQueryInvalidOperationException;
import com.easy.query.core.expression.include.IncludeProcessor;
import com.easy.query.core.expression.include.IncludeProcessorFactory;
import com.easy.query.core.expression.lambda.SQLActionExpression1;
import com.easy.query.core.expression.sql.builder.ExpressionContext;
import com.easy.query.core.expression.sql.builder.factory.ExpressionBuilderFactory;
import com.easy.query.core.expression.sql.include.IncludeParserEngine;
import com.easy.query.core.expression.sql.include.IncludeParserResult;
import com.easy.query.core.expression.sql.include.IncludeProvider;
import com.easy.query.core.logging.Log;
import com.easy.query.core.logging.LogFactory;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.metadata.EntityMetadataManager;
import com.easy.query.core.metadata.IncludeNavigateExpression;
import com.easy.query.core.metadata.NavigateMetadata;
import com.easy.query.core.migration.DatabaseMigrationProvider;
import com.easy.query.core.migration.MigrationEntityParser;
import com.easy.query.core.trigger.EntityExpressionTrigger;
import com.easy.query.core.trigger.TriggerEvent;
import com.easy.query.core.util.EasyClassUtil;
import com.easy.query.core.util.EasyCollectionUtil;
import com.easy.query.core.util.EasyObjectUtil;
import com.easy.query.core.util.EasyPackageUtil;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * @author xuejiaming
 * create time 2023/3/6 13:30
 */
public class DefaultEasyQueryClient implements EasyQueryClient {
    private final Log log = LogFactory.getLog(DefaultEasyQueryClient.class);
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

    @Override
    public <T> void loadInclude(List<T> entities, String navigateProperty, SQLActionExpression1<LoadIncludeConfiguration> configure) {
        if (EasyCollectionUtil.isEmpty(entities)) {
            return;
        }
        T first = EasyCollectionUtil.first(entities);
        Class<?> firstClass = first.getClass();
        EntityMetadataManager entityMetadataManager = runtimeContext.getEntityMetadataManager();
        EntityMetadata entityMetadata = entityMetadataManager.getEntityMetadata(firstClass);
        NavigateMetadata navigateMetadata = entityMetadata.getNavigateNotNull(navigateProperty);

        IncludeProvider includeProvider = runtimeContext.getIncludeProvider();
        ExpressionBuilderFactory expressionBuilderFactory = runtimeContext.getExpressionBuilderFactory();
        ExpressionContext expressionContext = expressionBuilderFactory.createExpressionContext(runtimeContext, ContextTypeEnum.QUERY);
        LoadIncludeConfiguration loadIncludeConfiguration = new LoadIncludeConfiguration();
        if(configure!=null){
            configure.apply(loadIncludeConfiguration);
        }
//        for (String selfProperty : navigateMetadata.getSelfPropertiesOrPrimary()) {
//            ColumnMetadata columnMetadata = entityMetadata.getColumnNotNull(selfProperty);
//            EasySQLExpressionUtil.addRelationExtraColumn(columnMetadata,selfProperty,expressionContext.getRelationExtraMetadata(),false);
//        }

        Boolean tracking = loadIncludeConfiguration.getTracking();
        boolean isNoTracking = tracking != null && !tracking;
        includeProvider.include(null, entityMetadata, expressionContext, ic -> {
            ClientQueryable<Object> with = ic.with(navigateProperty, loadIncludeConfiguration.getGroupSize());
            if (isNoTracking) {
                return with.asNoTracking();
            }
            return with;
        });

        IncludeProcessorFactory includeProcessorFactory = runtimeContext.getIncludeProcessorFactory();
        IncludeParserEngine includeParserEngine = runtimeContext.getIncludeParserEngine();
        for (IncludeNavigateExpression include : expressionContext.getIncludes().values()) {

            IncludeParserResult includeParserResult = includeParserEngine.process(expressionContext, entityMetadata, entities, include);

            IncludeProcessor includeProcess = includeProcessorFactory.createIncludeProcess(includeParserResult, runtimeContext);
            includeProcess.process();
        }
        TrackManager trackManager = runtimeContext.getTrackManager();
        TrackContext currentTrackContext = trackManager.getCurrentTrackContext();
        if (currentTrackContext != null) {
            for (T entity : entities) {
                EntityState trackEntityState = currentTrackContext.getTrackEntityState(entity);
                if (trackEntityState != null) {
                    trackEntityState.addInclude(navigateMetadata);
                }
            }
        }
    }

    @Override
    public DatabaseCodeFirst getDatabaseCodeFirst() {
        return runtimeContext.getDatabaseCodeFirst();
    }

    @Override
    public void setMigrationParser(MigrationEntityParser migrationParser) {
        Objects.requireNonNull(migrationParser, "migrationParser is null");
        DatabaseMigrationProvider databaseMigrationProvider = runtimeContext.getService(DatabaseMigrationProvider.class);
        databaseMigrationProvider.setMigrationParser(migrationParser);
    }

    @Override
    public void addTriggerListener(Consumer<TriggerEvent> eventConsumer) {
        Objects.requireNonNull(eventConsumer, "eventConsumer is null");
        EntityExpressionTrigger entityExpressionTrigger = runtimeContext.getService(EntityExpressionTrigger.class);
        entityExpressionTrigger.addTriggerListener(eventConsumer);
    }

    @Override
    public void loadTableEntityByPackage(String... packageNames) {
        if (packageNames == null) {
            return;
        }
        for (String packageName : packageNames) {
            loadTableEntity(packageName);
        }
    }

    private void loadTableEntity(String packageName) {
        Set<String> scanClasses = EasyPackageUtil.scanClasses(packageName, true, false);
        EntityMetadataManager entityMetadataManager = runtimeContext.getEntityMetadataManager();
        for (String scanClass : scanClasses) {
            try {
                Class<?> aClass = Class.forName(scanClass);
                Table tableAnnotation = EasyClassUtil.getAnnotation(aClass, Table.class);
                if (tableAnnotation != null) {
                    entityMetadataManager.getEntityMetadata(aClass);
                }
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void syncTableByPackage(int groupSize, String... packageNames) {
        loadTableEntityByPackage(packageNames);
        EntityMetadataManager entityMetadataManager = runtimeContext.getEntityMetadataManager();
        DatabaseCodeFirst databaseCodeFirst = getDatabaseCodeFirst();
        List<EntityMetadata> entityMetadata = entityMetadataManager.getAllLoadedEntityMetadata();
        List<Class<?>> tableEntities = entityMetadata.stream().filter(s -> s.getTableName() != null).map(s -> s.getEntityClass()).collect(Collectors.toList());
        List<List<Class<?>>> partition = EasyCollectionUtil.partition(tableEntities, groupSize);
        for (List<Class<?>> entityMetadataList : partition) {
            CodeFirstCommand codeFirstCommand = databaseCodeFirst.syncTableCommand(entityMetadataList);
            codeFirstCommand.executeWithTransaction(s -> {
                log.info("执行sql:" + s.getSQL());
                s.commit();
            });
        }
    }
}
