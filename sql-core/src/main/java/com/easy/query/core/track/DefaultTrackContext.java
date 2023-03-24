package com.easy.query.core.track;

import com.easy.query.core.abstraction.metadata.EntityMetadata;
import com.easy.query.core.abstraction.metadata.EntityMetadataManager;
import com.easy.query.core.exception.EasyQueryException;
import com.easy.query.core.util.BeanUtil;
import com.easy.query.core.util.ClassUtil;
import com.easy.query.core.util.StringUtil;
import com.easy.query.core.util.TrackUtil;

import java.util.HashSet;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @FileName: DefaultTrackContext.java
 * @Description: 文件说明
 * @Date: 2023/3/19 21:13
 * @author xuejiaming
 */
public class DefaultTrackContext implements TrackContext {
    private final EntityMetadataManager entityMetadataManager;
    private int beginCount = 0;
    private final ConcurrentHashMap<Class<?>, ConcurrentHashMap<String, EntityState>> trackEntityMap = new ConcurrentHashMap<>();

    public DefaultTrackContext(EntityMetadataManager entityMetadataManager) {

        this.entityMetadataManager = entityMetadataManager;
    }

    @Override
    public void begin() {
        beginCount++;
    }

    @Override
    public boolean isTrack(Object entity) {
        return getTrackEntityState(entity) != null;
    }

    @Override
    public EntityState getTrackEntityState(Object entity) {
        if (entity == null) {
            return null;
        }
        ConcurrentHashMap<String, EntityState> entityStateTrackMap = trackEntityMap.get(entity.getClass());
        if (entityStateTrackMap == null) {
            return null;
        }
        String trackKey = TrackUtil.getTrackKey(entityMetadataManager, entity);
        if (trackKey == null) {
            return null;
        }
        return entityStateTrackMap.get(trackKey);
    }

    @Override
    public void addTracking(Object entity,boolean isQuery) {
        if(entity==null){
            throw new EasyQueryException("cant track null entity");
        }

        Class<?> entityClass = entity.getClass();

        String trackKey = TrackUtil.getTrackKey(entityMetadataManager, entity);
        if (trackKey == null) {
            throw new EasyQueryException(ClassUtil.getSimpleName(entityClass) + ": current entity cant get track key,primary maybe null");
        }
        ConcurrentHashMap<String, EntityState> entityStateMap = trackEntityMap.computeIfAbsent(entityClass, o -> new ConcurrentHashMap<>());
        EntityState originalEntityState = entityStateMap.get(trackKey);
        if (originalEntityState!=null) {
            //查询的情况下直接使用追踪后的数据
            if(!isQuery&&!originalEntityState.getCurrentValue().equals(entity)){
                throw new EasyQueryException(ClassUtil.getSimpleName(entityClass) + ": current entity already tracked key,primary key:" + trackKey);
            }
        }else{
            EntityMetadata entityMetadata = entityMetadataManager.getEntityMetadata(entityClass);
            if(StringUtil.isBlank(entityMetadata.getTableName())){
                throw new EasyQueryException(ClassUtil.getSimpleName(entityClass) + ": is not table entity,cant tracking");
            }
            HashSet<String> propertySet = new HashSet<>(entityMetadata.getProperties());

            Object original =   ClassUtil.newInstance(entityClass);
            BeanUtil.copyProperties(entity,original,propertySet);
            EntityState entityState = new EntityState(entityClass, original, entity);
            entityStateMap.putIfAbsent(trackKey,entityState);
        }
    }

    @Override
    public void release() {
        if (beginCount > 0) {
            beginCount--;
        }
        if (beginCount == 0) {
            trackEntityMap.clear();
        }

    }

}
