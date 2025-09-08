package com.easy.query.api.proxy.entity.save;

import com.easy.query.core.expression.parser.core.base.ColumnOnlySelector;
import com.easy.query.core.metadata.EntityMetadata;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * create time 2025/9/5 20:31
 * 文件说明
 *
 * @author xuejiaming
 */
public class SaveNode {
    private final List<InsertItem> inserts;
    private final List<Object> updates;
    private final List<Object> deletes;
    private final List<Object> deleteBys;
    private final int index;
    private final EntityMetadata entityMetadata;

    public SaveNode(int index, EntityMetadata entityMetadata) {
        this.index = index;
        this.entityMetadata = entityMetadata;
        this.inserts = new ArrayList<>();
        this.updates = new ArrayList<>();
        this.deletes = new ArrayList<>();
        this.deleteBys = new ArrayList<>();
    }

    public int getIndex() {
        return index;
    }

    public EntityMetadata getEntityMetadata() {
        return entityMetadata;
    }

    public List<InsertItem> getInserts() {
        return inserts;
    }

    public List<Object> getUpdates() {
        return updates;
    }

    public List<Object> getDeletes() {
        return deletes;
    }

    public List<Object> getDeleteBys() {
        return deleteBys;
    }

    public static class InsertItem {
        private final Object entity;
        private final Consumer<Object> consumer;

        public InsertItem(Object entity, Consumer<Object> consumer) {
            this.entity = entity;
            this.consumer = consumer;
        }

        public Object getEntity() {
            return entity;
        }

        public void insertBefore() {
            consumer.accept(entity);
        }
    }
}
