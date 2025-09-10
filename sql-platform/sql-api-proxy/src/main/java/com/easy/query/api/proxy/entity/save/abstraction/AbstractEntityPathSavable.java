//package com.easy.query.api.proxy.entity.save.abstraction;
//
//import com.easy.query.api.proxy.entity.save.EntityPathSavable;
//import com.easy.query.api.proxy.entity.save.EntitySavable;
//import com.easy.query.api.proxy.entity.save.command.SaveCommand;
//import com.easy.query.api.proxy.entity.save.provider.AutoTrackSaveProvider;
//import com.easy.query.core.api.client.EasyQueryClient;
//import com.easy.query.core.basic.extension.track.TrackContext;
//import com.easy.query.core.common.ValueHolder;
//import com.easy.query.core.context.QueryRuntimeContext;
//import com.easy.query.core.exception.EasyQueryException;
//import com.easy.query.core.exception.EasyQueryInvalidOperationException;
//import com.easy.query.core.expression.lambda.SQLFuncExpression1;
//import com.easy.query.core.expression.parser.core.available.MappingPath;
//import com.easy.query.core.metadata.IncludePathTreeNode;
//import com.easy.query.core.proxy.ProxyEntity;
//import com.easy.query.core.util.EasyCollectionUtil;
//import com.easy.query.core.util.EasyObjectUtil;
//import com.easy.query.core.util.EasyUtil;
//
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.Collections;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Objects;
//import java.util.Set;
//
///**
// * create time 2025/9/10 10:32
// * 文件说明
// *
// * @author xuejiaming
// */
//public class AbstractEntityPathSavable<TProxy extends ProxyEntity<TProxy, T>, T> implements EntityPathSavable<TProxy, T> {
//    protected final List<T> entities = new ArrayList<>();
//    private final TProxy tProxy;
//    private final Class<T> entityClass;
//    private final EasyQueryClient easyQueryClient;
//    private final QueryRuntimeContext runtimeContext;
//    private boolean batch;
//    private IncludePathTreeNode includePathTreeRoot;
//
//    public AbstractEntityPathSavable(TProxy tProxy, Class<T> entityClass, Collection<T> entities, EasyQueryClient easyQueryClient) {
//        this.tProxy = tProxy;
//        this.entityClass = entityClass;
//        this.easyQueryClient = easyQueryClient;
//        this.runtimeContext = easyQueryClient.getRuntimeContext();
//        if (entities == null) {
//            throw new EasyQueryException("不支持空对象的save");
//        }
//        this.entities.addAll(entities);
//        boolean threadInTransaction = runtimeContext.getConnectionManager().currentThreadInTransaction();
//        if (!threadInTransaction) {
//            throw new EasyQueryInvalidOperationException("current thread not in transaction");
//        }
//        this.batch = false;
//    }
//
//    @Override
//    public List<T> getEntities() {
//        return entities;
//    }
//
//    @Override
//    public void executeCommand(SQLFuncExpression1<TProxy, List<MappingPath>> navigateIncludeSQLExpression) {
//        if (EasyCollectionUtil.isNotEmpty(entities)) {
//
//            //获取路径
//            ValueHolder<List<MappingPath>> includeAvailableValueHolder = new ValueHolder<>();
//            tProxy.getEntitySQLContext()._include(() -> {
//                List<MappingPath> values = navigateIncludeSQLExpression.apply(tProxy);
//                if (values != null) {
//                    includeAvailableValueHolder.setValue(values);
//                }
//            });
//            List<MappingPath> values = includeAvailableValueHolder.getValue();
//            if (values != null) {
//                this.includePathTreeRoot = EasyUtil.getIncludePathTreeRoot0(values);
//            }
//            //保存路径
//            List<Set<String>> savePaths = getSavePathLimit();//检查path不可以包含聚合根
//            if(EasyCollectionUtil.isEmpty(savePaths)){
//                throw new EasyQueryInvalidOperationException("save paths is empty");
//            }
//            SaveCommand command = new AutoTrackSaveProvider(currentTrackContext, entityClass, EasyObjectUtil.typeCastNotNull(entities), easyQueryClient, new ArrayList<>()).createCommand();
//            command.execute(batch);
//
//        }
//    }
//    private List<Set<String>> getSavePathLimit() {
//        List<Set<String>> savePathLimit = new ArrayList<>();
//        if (includePathTreeRoot != null) {
//            parseSavePath(includePathTreeRoot, savePathLimit, 0);
//        }
//        return savePathLimit;
//    }
//    private void parseSavePath(IncludePathTreeNode includePathTreeRoot, List<Set<String>> savePathLimit, int deep) {
//        if (EasyCollectionUtil.isNotEmpty(includePathTreeRoot.getChildren())) {
//            Set<String> savablePathSet = getSavablePath(savePathLimit, deep);
//            for (IncludePathTreeNode child : includePathTreeRoot.getChildren()) {
//                savablePathSet.add(child.getName());
//                parseSavePath(child, savePathLimit, deep + 1);
//            }
//        }
//
//    }
//
//    public Set<String> getSavablePath(List<Set<String>> savePathLimit, int index) {
//        if (index == savePathLimit.size()) {
//            savePathLimit.add(new HashSet<>());
//        } else {
//            if (index > savePathLimit.size()) {
//                throw new EasyQueryInvalidOperationException("index out of range");
//            }
//        }
//        return savePathLimit.get(index);
//    }
//
//    @Override
//    public EntityPathSavable<TProxy, T> batch(boolean use) {
//        this.batch = use;
//        return this;
//    }
//}
