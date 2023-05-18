package com.easy.query.core.basic.plugin.track;

import com.easy.query.core.common.bean.FastBean;
import com.easy.query.core.expression.lambda.Property;
import com.easy.query.core.expression.lambda.PropertySetterCaller;
import com.easy.query.core.metadata.ColumnMetadata;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.metadata.EntityMetadataManager;
import com.easy.query.core.exception.EasyQueryException;
import com.easy.query.core.util.EasyBeanUtil;
import com.easy.query.core.util.EasyClassUtil;
import com.easy.query.core.util.EasyStringUtil;
import com.easy.query.core.util.EasyTrackUtil;

import java.util.Map;
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
        if (entityStateTrackMap == null||entityStateTrackMap.isEmpty()) {
            return null;
        }
        EntityMetadata entityMetadata = entityMetadataManager.getEntityMetadata(entity.getClass());
        String trackKey = EasyTrackUtil.getTrackKey(entityMetadata, entity);
        if (trackKey == null) {
            return null;
        }
        return entityStateTrackMap.get(trackKey);
    }

    @Override
    public boolean addTracking(Object entity,boolean isQuery) {
        if(entity==null){
            throw new EasyQueryException("cant track null entity");
        }

        Class<?> entityClass = entity.getClass();
        EntityMetadata entityMetadata = entityMetadataManager.getEntityMetadata(entityClass);
        String trackKey = EasyTrackUtil.getTrackKey(entityMetadata, entity);
        if (trackKey == null) {
            throw new EasyQueryException(EasyClassUtil.getSimpleName(entityClass) + ": current entity cant get track key,primary maybe null");
        }
        ConcurrentHashMap<String, EntityState> entityStateMap = trackEntityMap.computeIfAbsent(entityClass, o -> new ConcurrentHashMap<>());
        EntityState originalEntityState = entityStateMap.get(trackKey);
        if (originalEntityState!=null) {
            //查询的情况下直接使用追踪后的数据
            if(!isQuery&&!originalEntityState.getCurrentValue().equals(entity)){
                throw new EasyQueryException(EasyClassUtil.getSimpleName(entityClass) + ": current entity already tracked key,primary key:" + trackKey);
            }
            return false;
        }else{
            if(EasyStringUtil.isBlank(entityMetadata.getTableName())){
                throw new EasyQueryException(EasyClassUtil.getSimpleName(entityClass) + ": is not table entity,cant tracking");
            }
            Object original = createAndCopyValue(entity,entityMetadata);
            EntityState entityState = new EntityState(entityClass, original, entity);
            entityStateMap.putIfAbsent(trackKey,entityState);
            return true;
        }
    }

    /**
     * 实体对象零拷贝
     * @param entity
     * @param entityMetadata
     * @return
     */
    private Object createAndCopyValue(Object entity, EntityMetadata entityMetadata){

        Object original = EasyClassUtil.newInstance(entity.getClass());
        FastBean fastBean = EasyBeanUtil.getFastBean(entity.getClass());
        for (Map.Entry<String, ColumnMetadata> columnMetadataEntry : entityMetadata.getProperty2ColumnMap().entrySet()) {

            PropertySetterCaller<Object> beanSetter = fastBean.getBeanSetter(columnMetadataEntry.getValue().getProperty());
            Property<Object, ?> beanGetter = fastBean.getBeanGetter(columnMetadataEntry.getValue().getProperty());
            Object value = beanGetter.apply(entity);
            beanSetter.call(original,value);
        }
        return original;
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
