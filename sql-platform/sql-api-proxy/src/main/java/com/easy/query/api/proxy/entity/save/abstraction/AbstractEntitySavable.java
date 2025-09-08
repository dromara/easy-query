package com.easy.query.api.proxy.entity.save.abstraction;

import com.easy.query.api.proxy.entity.save.EntitySavable;
import com.easy.query.api.proxy.entity.save.SavableContext;
import com.easy.query.api.proxy.entity.save.SaveCommandContext;
import com.easy.query.api.proxy.entity.save.SaveNode;
import com.easy.query.api.proxy.entity.save.command.SaveCommand;
import com.easy.query.api.proxy.entity.save.provider.InsertSaveProvider;
import com.easy.query.api.proxy.entity.save.provider.UpdateSaveProvider;
import com.easy.query.core.api.SQLClientApiFactory;
import com.easy.query.core.api.client.EasyQueryClient;
import com.easy.query.core.basic.extension.track.EntityState;
import com.easy.query.core.basic.extension.track.EntityValueState;
import com.easy.query.core.basic.extension.track.TrackContext;
import com.easy.query.core.basic.extension.track.TrackManager;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.enums.RelationTypeEnum;
import com.easy.query.core.exception.EasyQueryException;
import com.easy.query.core.exception.EasyQueryInvalidOperationException;
import com.easy.query.core.expression.lambda.Property;
import com.easy.query.core.metadata.ColumnMetadata;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.metadata.EntityMetadataManager;
import com.easy.query.core.metadata.NavigateMetadata;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.util.EasyArrayUtil;
import com.easy.query.core.util.EasyClassUtil;
import com.easy.query.core.util.EasyCollectionUtil;
import com.easy.query.core.util.EasyTrackUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Supplier;

/**
 * create time 2025/9/5 16:12
 * 文件说明
 *
 * @author xuejiaming
 */
public abstract class AbstractEntitySavable<TProxy extends ProxyEntity<TProxy, T>, T> implements EntitySavable<TProxy, T> {
    protected final List<T> entities = new ArrayList<>();
    private final Class<T> entityClass;
    private final EasyQueryClient easyQueryClient;
    private final QueryRuntimeContext runtimeContext;
    private final TrackContext currentTrackContext;
    private boolean batch;

    public AbstractEntitySavable(Class<T> entityClass, Collection<T> entities, EasyQueryClient easyQueryClient) {
        this.entityClass = entityClass;
        this.easyQueryClient = easyQueryClient;
        this.runtimeContext = easyQueryClient.getRuntimeContext();
        if (entities == null) {
            throw new EasyQueryException("不支持空对象的save");
        }
        this.entities.addAll(entities);
        boolean threadInTransaction = runtimeContext.getConnectionManager().currentThreadInTransaction();
        if (!threadInTransaction) {
            throw new EasyQueryInvalidOperationException("current thread not in transaction");
        }
        this.currentTrackContext = Objects.requireNonNull(runtimeContext.getTrackManager().getCurrentTrackContext(), "currentTrackContext can not be null");
        this.batch = false;
    }

    @Override
    public List<T> getEntities() {
        return entities;
    }

    @Override
    public EntitySavable<TProxy, T> batch(boolean use) {
        this.batch = use;
        return this;
    }

    @Override
    public void executeCommand() {
        if (!entities.isEmpty()) {
            List<SaveCommand> saveCommands = new ArrayList<>();
            List<Object> insertEntities = new ArrayList<>();
            List<Object> updateEntities = new ArrayList<>();
            for (T entity : entities) {
                EntityState entityState = currentTrackContext.getTrackEntityState(entity);
                if (entityState == null) {
                    insertEntities.add(entity);
                } else {
                    updateEntities.add(entity);
                }
            }
            if (EasyCollectionUtil.isNotEmpty(insertEntities)) {
                saveCommands.add(new InsertSaveProvider(entityClass, insertEntities, easyQueryClient).createCommand());
            }
            if (EasyCollectionUtil.isNotEmpty(updateEntities)) {
                saveCommands.add(new UpdateSaveProvider(entityClass, updateEntities, easyQueryClient).createCommand());
            }
            for (SaveCommand saveCommand : saveCommands) {
                saveCommand.execute();
            }
        }
    }
}
