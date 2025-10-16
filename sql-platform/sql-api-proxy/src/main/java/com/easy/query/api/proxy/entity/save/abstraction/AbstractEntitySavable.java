package com.easy.query.api.proxy.entity.save.abstraction;

import com.easy.query.api.proxy.entity.save.DefaultSaveConfigurer;
import com.easy.query.api.proxy.entity.save.EntitySavable;
import com.easy.query.api.proxy.entity.save.PrimaryKeyInsertProcessor;
import com.easy.query.api.proxy.entity.save.SaveBehavior;
import com.easy.query.api.proxy.entity.save.SaveConfigurer;
import com.easy.query.api.proxy.entity.save.command.SaveCommand;
import com.easy.query.api.proxy.entity.save.provider.AutoTrackSaveProvider;
import com.easy.query.core.api.client.EasyQueryClient;
import com.easy.query.core.basic.extension.track.TrackContext;
import com.easy.query.core.common.ValueHolder;
import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.exception.EasyQueryException;
import com.easy.query.core.exception.EasyQueryInvalidOperationException;
import com.easy.query.core.expression.lambda.SQLFuncExpression;
import com.easy.query.core.expression.lambda.SQLFuncExpression1;
import com.easy.query.core.expression.parser.core.available.MappingPath;
import com.easy.query.core.metadata.IncludePathTreeNode;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.util.EasyCollectionUtil;
import com.easy.query.core.util.EasyObjectUtil;
import com.easy.query.core.util.EasyUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;

/**
 * create time 2025/9/5 16:12
 * 文件说明
 *
 * @author xuejiaming
 */
public abstract class AbstractEntitySavable<TProxy extends ProxyEntity<TProxy, T>, T> implements EntitySavable<TProxy, T> {
    protected final List<T> entities = new ArrayList<>();
    private final TProxy tProxy;
    private final Class<T> entityClass;
    private final EasyQueryClient easyQueryClient;
    private final QueryRuntimeContext runtimeContext;
    private final TrackContext currentTrackContext;
    private boolean batch;
    private boolean removeRoot;
    private SaveBehavior saveBehavior;
    private IncludePathTreeNode includePathTreeRoot;
    private final PrimaryKeyInsertProcessor primaryKeyInsertProcessor;

    public AbstractEntitySavable(TProxy tProxy, Class<T> entityClass, Collection<T> entities, EasyQueryClient easyQueryClient) {
        this.tProxy = tProxy;
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
        this.removeRoot = false;
        this.saveBehavior = new SaveBehavior();
        this.primaryKeyInsertProcessor=new PrimaryKeyInsertProcessor(runtimeContext);
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
    public EntitySavable<TProxy, T> configure(Consumer<SaveConfigurer> behaviorConfigure) {
        if (behaviorConfigure != null) {
            behaviorConfigure.accept(new DefaultSaveConfigurer(saveBehavior));
        }
        return this;
    }

    @Override
    public EntitySavable<TProxy, T> removeRoot(boolean removeRoot) {
        this.removeRoot = removeRoot;
        return this;
    }

    @Override
    public EntitySavable<TProxy, T> primaryKeyOnInsert(SQLFuncExpression<Object> primaryKeyOnInsertGetter) {
        primaryKeyInsertProcessor.setExpressionStrategy(primaryKeyOnInsertGetter);
        return this;
    }

    @Override
    public EntitySavable<TProxy, T> savePath(SQLFuncExpression1<TProxy, List<MappingPath>> navigateIncludeSQLExpression) {
        ValueHolder<List<MappingPath>> includeAvailableValueHolder = new ValueHolder<>();
        tProxy.getEntitySQLContext()._include(() -> {
            List<MappingPath> values = navigateIncludeSQLExpression.apply(tProxy);
            if (values != null) {
                includeAvailableValueHolder.setValue(values);
            }
        });
        List<MappingPath> values = includeAvailableValueHolder.getValue();
        if (values != null) {
            this.includePathTreeRoot = EasyUtil.getIncludePathTreeRoot0(values);
        }
        return this;
    }

    @Override
    public void executeCommand() {
        if (!entities.isEmpty()) {
            List<Set<String>> savePathLimit = getSavePathLimit();
            SaveCommand command = new AutoTrackSaveProvider(currentTrackContext, entityClass, EasyObjectUtil.typeCastNotNull(entities), easyQueryClient, savePathLimit, saveBehavior, removeRoot,primaryKeyInsertProcessor).createCommand();
            command.execute(batch);
        }
    }

    private List<Set<String>> getSavePathLimit() {
        List<Set<String>> savePathLimit = new ArrayList<>();
        if (includePathTreeRoot != null) {
            parseSavePath(includePathTreeRoot, savePathLimit, 0);
        }
        return savePathLimit;
    }

    private void parseSavePath(IncludePathTreeNode includePathTreeRoot, List<Set<String>> savePathLimit, int deep) {
        if (EasyCollectionUtil.isNotEmpty(includePathTreeRoot.getChildren())) {
            Set<String> savablePathSet = getSavablePath(savePathLimit, deep);
            for (IncludePathTreeNode child : includePathTreeRoot.getChildren()) {
                savablePathSet.add(child.getName());
                parseSavePath(child, savePathLimit, deep + 1);
            }
        }

    }

    public Set<String> getSavablePath(List<Set<String>> savePathLimit, int index) {
        if (index == savePathLimit.size()) {
            savePathLimit.add(new HashSet<>());
        } else {
            if (index > savePathLimit.size()) {
                throw new EasyQueryInvalidOperationException("index out of range");
            }
        }
        return savePathLimit.get(index);
    }
}
