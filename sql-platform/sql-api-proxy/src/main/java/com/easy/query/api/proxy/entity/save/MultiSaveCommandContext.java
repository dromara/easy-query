//package com.easy.query.api.proxy.entity.save;
//
//import com.easy.query.core.exception.EasyQueryInvalidOperationException;
//import com.easy.query.core.metadata.NavigateMetadata;
//
//import java.util.ArrayList;
//import java.util.HashSet;
//import java.util.LinkedHashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.Objects;
//import java.util.Set;
//
///**
// * create time 2025/9/7 22:27
// * 文件说明
// *
// * @author xuejiaming
// */
//public class MultiSaveCommandContext implements SaveCommandContext {
//
//    private final Map<Object, SaveCommandContext> saveCommandContextMap;
//    private final Class<?> entityClass;
//
//    private SaveCommandContext currentSaveCommandContext;
//
//    public MultiSaveCommandContext(Class<?> entityClass) {
//        this.entityClass = entityClass;
//        this.saveCommandContextMap = new LinkedHashMap<>();
//    }
//
//    public void change(Object entity) {
//        currentSaveCommandContext = saveCommandContextMap.computeIfAbsent(entity, k -> new SingleSaveCommandContext(entityClass));
//    }
//
//    private void checkCurrent() {
//        if (currentSaveCommandContext == null) {
//            throw new EasyQueryInvalidOperationException("currentSaveCommandContext is null");
//        }
//    }
//
//    @Override
//    public boolean circulateCheck(Class<?> targetEntityClass, int deep) {
//        return currentSaveCommandContext.circulateCheck(targetEntityClass, deep);
//    }
//
//    @Override
//    public void addProcessEntity(Object entity) {
//        currentSaveCommandContext.addProcessEntity(entity);
//    }
//
//    @Override
//    public boolean isProcessEntity(Object entity) {
//        return currentSaveCommandContext.isProcessEntity(entity);
//    }
//
//    @Override
//    public List<SavableContext> getSavableContexts() {
//        return currentSaveCommandContext.getSavableContexts();
//    }
//
//    @Override
//    public SavableContext getSavableContext(int index) {
//        return currentSaveCommandContext.getSavableContext(index);
//    }
//
//    @Override
//    public SaveCommandContext mergeContext() {
//        SingleSaveCommandContext singleSaveCommandContext = new SingleSaveCommandContext(entityClass);
//        for (Map.Entry<Object, SaveCommandContext> saveCommandContextKv : saveCommandContextMap.entrySet()) {
//            SaveCommandContext saveCommandContext = saveCommandContextKv.getValue();
//            List<SavableContext> savableContexts = saveCommandContext.getSavableContexts();
//            for (int i = 0; i < savableContexts.size(); i++) {
//                SavableContext savableContext = savableContexts.get(i);
//                SavableContext newSavableContext = singleSaveCommandContext.getSavableContext(i);
//                Map<NavigateMetadata, SaveNode> saveNodeMap = savableContext.getSaveNodeMap();
//                for (Map.Entry<NavigateMetadata, SaveNode> saveNodeKey : saveNodeMap.entrySet()) {
//                    SaveNode value = saveNodeKey.getValue();
//                    SaveNode saveNode = newSavableContext.putSaveNodeMap(saveNodeKey.getKey(), value.getEntityMetadata());
//                    saveNode.getInserts().addAll(value.getInserts());
//                    saveNode.getUpdates().addAll(value.getUpdates());
//                    saveNode.getDeletes().addAll(value.getDeletes());
//                }
//            }
//        }
//        return singleSaveCommandContext;
//    }
//}
