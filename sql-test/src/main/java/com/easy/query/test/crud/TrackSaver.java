package com.easy.query.test.crud;

import com.easy.query.api.proxy.client.EasyEntityQuery;
import com.easy.query.api.proxy.entity.EntityQueryProxyManager;
import com.easy.query.core.api.client.EasyQueryClient;
import com.easy.query.core.basic.extension.track.TrackContext;
import com.easy.query.core.basic.extension.track.TrackManager;
import com.easy.query.core.enums.RelationTypeEnum;
import com.easy.query.core.exception.EasyQueryException;
import com.easy.query.core.exception.EasyQueryInvalidOperationException;
import com.easy.query.core.expression.lambda.Property;
import com.easy.query.core.expression.lambda.SQLFuncExpression1;
import com.easy.query.core.expression.parser.core.PropColumn;
import com.easy.query.core.metadata.ColumnMetadata;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.metadata.NavigateMetadata;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.core.util.EasyClassUtil;
import com.easy.query.core.util.EasyCollectionUtil;
import com.easy.query.core.util.EasyObjectUtil;
import com.easy.query.core.util.EasyTrackUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * create time 2025/9/3 13:52
 * 文件说明
 *
 * @author xuejiaming
 */
public class TrackSaver<TProxy extends ProxyEntity<TProxy, T>, T extends ProxyEntityAvailable<T, TProxy>> {
    private final EasyEntityQuery easyEntityQuery;
    private final EntityMetadata entityMetadata;
    private final TrackManager trackManager;
    private final TrackContext currentTrackContext;
    private final List<T> entities;
    private final Map<String, T> entityMap;
    private final List<T> originals;
    private final List<TrackCrud> cruds;

    public TrackSaver(EasyEntityQuery easyEntityQuery, T entity) {
        this(easyEntityQuery, Collections.singletonList(entity));
    }

    public TrackSaver(EasyEntityQuery easyEntityQuery, List<T> entities) {
        this.easyEntityQuery = easyEntityQuery;
        this.trackManager = easyEntityQuery.getRuntimeContext().getTrackManager();
        this.currentTrackContext = trackManager.getCurrentTrackContext();
        Objects.requireNonNull(currentTrackContext, "currentTrackContext can not be null");
        this.entities = entities;
        Class<?> entityClass = entities.get(0).getClass();
        this.entityMetadata = easyEntityQuery.getRuntimeContext().getEntityMetadataManager().getEntityMetadata(entityClass);
        this.originals = new ArrayList<>(entities.size());
        this.entityMap = new HashMap<>();
        for (T entity : entities) {
            String trackKey = EasyTrackUtil.getTrackKey(entityMetadata, entity);
            if (trackKey == null) {
                throw new EasyQueryInvalidOperationException(EasyClassUtil.getSimpleName(entityClass) + ": current entity cant get track key,primary maybe null");
            }
            T t = EasyObjectUtil.<Object, T>typeCastNotNull(currentTrackContext.createAndCopyValue(entity, entityMetadata));
            originals.add(t);
            T old = entityMap.put(trackKey, entity);
            if (old != null) {
                throw new EasyQueryInvalidOperationException(EasyClassUtil.getSimpleName(entityClass) + ": current entity already tracked key,primary key:" + trackKey);
            }
        }
        this.cruds = new ArrayList<>();
    }

    public TrackSaver<TProxy, T> saveBy(SQLFuncExpression1<TProxy, PropColumn> saveBySelector) {
        TProxy tProxy = EntityQueryProxyManager.create(EasyObjectUtil.typeCastNotNull(entityMetadata.getEntityClass()));
        PropColumn propColumn = saveBySelector.apply(tProxy);
        String navigatePropertyName = propColumn.getValue();
        NavigateMetadata navigateMetadata = entityMetadata.getNavigateNotNull(navigatePropertyName);
        if (navigateMetadata.getRelationType() != RelationTypeEnum.OneToMany) {
            throw new EasyQueryInvalidOperationException("navigate relation table should [OneToMany],now is " + navigateMetadata.getRelationType());
        }
        Property<Object, ?> navigateMetadataGetter = navigateMetadata.getGetter();
        EntityMetadata navigateEntityMetadata = easyEntityQuery.getRuntimeContext().getEntityMetadataManager().getEntityMetadata(navigateMetadata.getNavigatePropertyType());

        easyEntityQuery.loadInclude(originals, saveBySelector);

        for (T original : originals) {
            String trackKey = EasyTrackUtil.getTrackKey(entityMetadata, original);
            T entity = entityMap.get(trackKey);
            if (entity == null) {
                throw new EasyQueryException(EasyClassUtil.getSimpleName(entityMetadata.getEntityClass()) + ": current entity not tracked key,primary key:" + trackKey);
            }
            List<Object> oldEntities = EasyObjectUtil.typeCastNotNull(navigateMetadataGetter.apply(original));
            List<Object> newEntities = EasyObjectUtil.typeCastNotNull(navigateMetadataGetter.apply(entity));
            Map<String, Object> dbEnttiyMap = new HashMap<>();
            for (Object oldEntity : oldEntities) {

                String oldNavigateEntityKey = EasyTrackUtil.getTrackKey(navigateEntityMetadata, oldEntity);
                if (oldNavigateEntityKey == null) {
                    throw new EasyQueryInvalidOperationException(EasyClassUtil.getSimpleName(navigateEntityMetadata.getEntityClass()) + ": current entity cant get track key,primary maybe null");
                }
                dbEnttiyMap.put(oldNavigateEntityKey, oldEntity);
            }
            Set<String> dbKeys = dbEnttiyMap.keySet();
            //请求里面的
            List<Object> inserts = new ArrayList<>();
            List<Object> updates = new ArrayList<>();
            for (Object newEntity : newEntities) {
                String newNavigateEntityKey = EasyTrackUtil.getTrackKey(navigateEntityMetadata, newEntity);
                if (newNavigateEntityKey == null || !dbKeys.contains(newNavigateEntityKey)) {
                    //insert 插入数据
                    inserts.add(newEntity);
                }
                Object updateObject = dbEnttiyMap.get(newNavigateEntityKey);

                Objects.requireNonNull(updateObject, "updateObject cant be null,primary key:" + newNavigateEntityKey);
                dbEnttiyMap.remove(newNavigateEntityKey);
                updates.add(newEntity);
//                for (Map.Entry<String, ColumnMetadata> propertyCopies : navigateEntityMetadata.getProperty2ColumnMap().entrySet()) {
//                    ColumnMetadata columnMetadata = propertyCopies.getValue();
//                    Object value = columnMetadata.getGetterCaller().apply(newEntity);
//                    columnMetadata.getSetterCaller().call(newEntity, value);
//                }
            }
            List<Object> deletes = new ArrayList<>(dbEnttiyMap.values());
            TrackCrud trackCrud = new TrackCrud();
            trackCrud.getInserts().addAll(inserts);
            trackCrud.getUpdates().addAll(updates);
            trackCrud.getDeletes().addAll(deletes);
            this.cruds.add(trackCrud);
        }
        return this;
    }

    public void executeCommand(boolean saveEntities) {
        if (saveEntities) {
            easyEntityQuery.updatable(entities).executeRows();
        }
        for (TrackCrud crud : cruds) {
            crud.executeDb(easyEntityQuery);
        }
    }


    public static class TrackCrud {
        private List<Object> inserts = new ArrayList<>();
        private List<Object> updates = new ArrayList<>();
        private List<Object> deletes = new ArrayList<>();

        public List<Object> getInserts() {
            return inserts;
        }

        public List<Object> getUpdates() {
            return updates;
        }

        public List<Object> getDeletes() {
            return deletes;
        }

        public boolean hasAnyEntity(){
            return EasyCollectionUtil.isNotEmpty(inserts)||EasyCollectionUtil.isNotEmpty(updates)||EasyCollectionUtil.isNotEmpty(deletes);
        }

        public void executeDb(EasyEntityQuery easyEntityQuery){
            if(hasAnyEntity()){
                EasyQueryClient easyQueryClient = easyEntityQuery.getEasyQueryClient();
                easyQueryClient.insertable(inserts).executeRows();
                easyQueryClient.updatable(updates).executeRows();
                easyQueryClient.deletable(deletes).executeRows();
            }
        }
    }
}
