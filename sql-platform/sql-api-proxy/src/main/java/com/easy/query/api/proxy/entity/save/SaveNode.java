package com.easy.query.api.proxy.entity.save;

import com.easy.query.core.exception.EasyQueryInvalidOperationException;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.util.EasyClassUtil;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * create time 2025/9/5 20:31
 * 文件说明
 *
 * @author xuejiaming
 */
public class SaveNode {
    private final Map<MemoryAddressCompareValue, EntitySaveSate> entityItems;
    private final List<Object> deleteBys;
    private final int index;
    /**
     * 和object实体一样的entity metadata
     */
    private final EntityMetadata entityMetadata;

    public SaveNode(int index, EntityMetadata entityMetadata) {
        this.index = index;
        this.entityMetadata = entityMetadata;
        this.deleteBys = new ArrayList<>();
        this.entityItems = new LinkedHashMap<>();
    }

    public int getIndex() {
        return index;
    }

    public EntityMetadata getEntityMetadata() {
        return entityMetadata;
    }

    public Map<MemoryAddressCompareValue, EntitySaveSate> getEntityItems() {
        return entityItems;
    }

    public void putDeleteItem(MemoryAddressCompareValue valueObject, Object aggregateRoot, Consumer<Object> consumer, SaveNodeDbTypeEnum saveNodeDbType) {
        EntitySaveSate entitySaveSate = entityItems.computeIfAbsent(valueObject, key -> new EntitySaveSate());

        if (entitySaveSate.getType() == SaveNodeTypeEnum.INIT) {
            entitySaveSate.setAggregateRoot(aggregateRoot);
            entitySaveSate.setConsumer(consumer);
            entitySaveSate.setType(SaveNodeTypeEnum.DELETE);
        } else {
            if (entitySaveSate.getType() != SaveNodeTypeEnum.DELETE) {
                if (entitySaveSate.getType() == SaveNodeTypeEnum.INSERT) {
                    //存在变更聚合根的操作
                    entitySaveSate.setType(SaveNodeTypeEnum.CHANGE);
                    entitySaveSate.setDbType(SaveNodeDbTypeEnum.UPDATE);
                } else {
                    throw new EasyQueryInvalidOperationException("The current object:[" + EasyClassUtil.getInstanceSimpleName(valueObject.getEntity()) + "] has a conflicting save state and cannot be changed from [" + getEntitySaveStateAggregateRootDisplayName(entitySaveSate) + "." + entitySaveSate.getType() + "] to [" + SaveNodeTypeEnum.DELETE + "].");
                }
            } else if (entitySaveSate.getDbType() != saveNodeDbType) {
                throw new EasyQueryInvalidOperationException("The current object:[" + EasyClassUtil.getInstanceSimpleName(valueObject.getEntity()) + "] has a conflicting save state and cannot be changed from [" + getEntitySaveStateAggregateRootDisplayName(entitySaveSate) + "." + entitySaveSate.getDbType() + "] to [" + saveNodeDbType + "].");
            }
        }
        entitySaveSate.setDbType(saveNodeDbType);
    }

    public void putInsertItem(MemoryAddressCompareValue valueObject, Object aggregateRoot, Consumer<Object> consumer) {
        EntitySaveSate entitySaveSate = entityItems.computeIfAbsent(valueObject, key -> new EntitySaveSate());
        entitySaveSate.setDbType(SaveNodeDbTypeEnum.INSERT);
        if (entitySaveSate.getType() == SaveNodeTypeEnum.INIT) {
            entitySaveSate.setAggregateRoot(aggregateRoot);
            entitySaveSate.setConsumer(consumer);
            entitySaveSate.setType(SaveNodeTypeEnum.INSERT);
        } else {
            if (entitySaveSate.getType() == SaveNodeTypeEnum.DELETE) {
                //存在变更聚合根的操作
                entitySaveSate.setAggregateRoot(aggregateRoot);
                entitySaveSate.setConsumer(consumer);
                entitySaveSate.setType(SaveNodeTypeEnum.CHANGE);
                entitySaveSate.setDbType(SaveNodeDbTypeEnum.UPDATE);
            } else {
                throw new EasyQueryInvalidOperationException("The current object:[" + EasyClassUtil.getInstanceSimpleName(valueObject.getEntity()) + "] has a conflicting save state and cannot be changed from [" + getEntitySaveStateAggregateRootDisplayName(entitySaveSate) + "." + entitySaveSate.getType() + "] to [" + EasyClassUtil.getInstanceSimpleName(aggregateRoot) + "." + SaveNodeTypeEnum.INSERT + "].");
            }
        }
    }

    public void putUpdateItem(MemoryAddressCompareValue valueObject, Object aggregateRoot, Consumer<Object> consumer) {
        EntitySaveSate entitySaveSate = entityItems.computeIfAbsent(valueObject, key -> new EntitySaveSate());
        entitySaveSate.setDbType(SaveNodeDbTypeEnum.UPDATE);
        if (entitySaveSate.getType() == SaveNodeTypeEnum.INIT) {
            entitySaveSate.setAggregateRoot(aggregateRoot);
            entitySaveSate.setConsumer(consumer);
            entitySaveSate.setType(SaveNodeTypeEnum.UPDATE);
        } else {
            throw new EasyQueryInvalidOperationException("The current object:[" + EasyClassUtil.getInstanceSimpleName(valueObject.getEntity()) + "] has a conflicting save state and cannot be changed from [" + getEntitySaveStateAggregateRootDisplayName(entitySaveSate) + "." + entitySaveSate.getType() + "] to [" + EasyClassUtil.getInstanceSimpleName(aggregateRoot) + "." + SaveNodeTypeEnum.UPDATE + "].");
        }
    }

    private String getEntitySaveStateAggregateRootDisplayName(EntitySaveSate entitySaveSate) {
        if (entitySaveSate.getAggregateRoot() == null) {
            return "-";
        }
        return EasyClassUtil.getInstanceSimpleName(entitySaveSate.getAggregateRoot());
    }

    public void putIgnoreUpdateItem(MemoryAddressCompareValue valueObject, Object aggregateRoot, Consumer<Object> consumer) {
        EntitySaveSate entitySaveSate = entityItems.computeIfAbsent(valueObject, key -> new EntitySaveSate());
        if (entitySaveSate.getType() == SaveNodeTypeEnum.INIT) {
            entitySaveSate.setAggregateRoot(aggregateRoot);
            entitySaveSate.setConsumer(consumer);
            entitySaveSate.setType(SaveNodeTypeEnum.UPDATE_IGNORE);
        } else {
            throw new EasyQueryInvalidOperationException("The current object:[" + EasyClassUtil.getInstanceSimpleName(valueObject.getEntity()) + "] has a conflicting save state and cannot be changed from [" + getEntitySaveStateAggregateRootDisplayName(entitySaveSate) + "." + entitySaveSate.getType() + "] to [" + EasyClassUtil.getInstanceSimpleName(aggregateRoot) + "." + SaveNodeTypeEnum.UPDATE_IGNORE + "].");
        }
    }


    public List<Object> getDeleteBys() {
        return deleteBys;
    }

    public static class EntitySaveSate {
        private Object aggregateRoot;
        private Consumer<Object> consumer;
        private SaveNodeTypeEnum type = SaveNodeTypeEnum.INIT;
        private SaveNodeDbTypeEnum dbType;


        public Object getAggregateRoot() {
            return aggregateRoot;
        }

        public void setAggregateRoot(Object aggregateRoot) {
            this.aggregateRoot = aggregateRoot;
        }

        public Consumer<Object> getConsumer() {
            return consumer;
        }

        public void executeBefore(Object entity) {
            if (consumer != null) {
                consumer.accept(entity);
            }
        }

        public void setConsumer(Consumer<Object> consumer) {
            this.consumer = consumer;
        }

        public SaveNodeTypeEnum getType() {
            return type;
        }

        public void setType(SaveNodeTypeEnum type) {
            this.type = type;
        }

        public SaveNodeDbTypeEnum getDbType() {
            return dbType;
        }

        public void setDbType(SaveNodeDbTypeEnum dbType) {
            this.dbType = dbType;
        }
    }
}
