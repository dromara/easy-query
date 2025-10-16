package com.easy.query.api.proxy.entity.save.command;

import com.easy.query.api.proxy.entity.save.PrimaryKeyInsertProcessor;
import com.easy.query.api.proxy.entity.save.SavableContext;
import com.easy.query.api.proxy.entity.save.SaveBehavior;
import com.easy.query.api.proxy.entity.save.SaveBehaviorEnum;
import com.easy.query.api.proxy.entity.save.SaveCommandContext;
import com.easy.query.api.proxy.entity.save.SaveNode;
import com.easy.query.api.proxy.entity.save.SaveNodeDbTypeEnum;
import com.easy.query.api.proxy.entity.save.SaveNodeTypeEnum;
import com.easy.query.core.api.client.EasyQueryClient;
import com.easy.query.core.enums.CascadeTypeEnum;
import com.easy.query.core.expression.lambda.SQLFuncExpression;
import com.easy.query.core.metadata.ColumnMetadata;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.metadata.NavigateMetadata;
import com.easy.query.core.util.EasyCollectionUtil;

import java.util.List;
import java.util.Map;

/**
 * create time 2025/9/6 21:07
 * 文件说明
 *
 * @author xuejiaming
 */
public class BasicSaveCommand implements SaveCommand {
    private final EntityMetadata entityMetadata;
    private final List<Object> inserts;
    private final List<Object> updates;
    private final EasyQueryClient easyQueryClient;
    private final SaveCommandContext saveCommandContext;
    private final SaveBehavior saveBehavior;
    private final boolean deleteAll;
    private final PrimaryKeyInsertProcessor primaryKeyInsertProcessor;

    public BasicSaveCommand(EntityMetadata entityMetadata, List<Object> inserts, List<Object> updates, EasyQueryClient easyQueryClient, SaveCommandContext saveCommandContext, SaveBehavior saveBehavior, boolean deleteAll, PrimaryKeyInsertProcessor primaryKeyInsertProcessor) {
        this.entityMetadata = entityMetadata;
        this.inserts = inserts;
        this.updates = updates;
        this.easyQueryClient = easyQueryClient;
        this.saveCommandContext = saveCommandContext;
        this.saveBehavior = saveBehavior;
        this.deleteAll = deleteAll;
        this.primaryKeyInsertProcessor = primaryKeyInsertProcessor;
    }

    @Override
    public void execute(boolean batch) {
        List<SavableContext> savableContexts = this.saveCommandContext.getSavableContexts();

        for (int i = savableContexts.size() - 1; i >= 0; i--) {
            SavableContext savableContext = savableContexts.get(i);
            for (Map.Entry<NavigateMetadata, SaveNode> saveNodeEntry : savableContext.getSaveNodeMap().entrySet()) {
                SaveNode saveNode = saveNodeEntry.getValue();
                List<Object> deleteItems = EasyCollectionUtil.mapFilterSelect(saveNode.getEntityItems(), kv -> kv.getValue().getType() == SaveNodeTypeEnum.DELETE && kv.getValue().getDbType() == SaveNodeDbTypeEnum.DELETE, kv -> {
                    kv.getValue().executeBefore(kv.getKey().getEntity());
                    return kv.getKey().getEntity();
                });
                easyQueryClient.deletable(deleteItems).batch(batch).allowDeleteStatement(true).executeRows();
                if (EasyCollectionUtil.isNotEmpty(saveNode.getDeleteBys())) {
                    NavigateMetadata navigateMetadata = saveNodeEntry.getKey();
                    if (navigateMetadata.getCascade() == CascadeTypeEnum.DELETE) {
                        String[] selfMappingProperties = navigateMetadata.getSelfMappingProperties();
                        String[] targetMappingProperties = navigateMetadata.getTargetMappingProperties();
                        easyQueryClient.deletable(saveNode.getDeleteBys()).batch(batch).allowDeleteStatement(true).whereColumns(col -> {
                            for (String selfMappingProperty : selfMappingProperties) {
                                col.column(selfMappingProperty);
                            }
                            for (String targetMappingProperty : targetMappingProperties) {
                                col.column(targetMappingProperty);
                            }
                        }).executeRows();
                    } else if (navigateMetadata.getCascade() == CascadeTypeEnum.AUTO || navigateMetadata.getCascade() == CascadeTypeEnum.SET_NULL) {
                        String[] selfMappingProperties = navigateMetadata.getSelfMappingProperties();
                        String[] targetMappingProperties = navigateMetadata.getTargetMappingProperties();
                        easyQueryClient.updatable(saveNode.getDeleteBys()).batch(batch)
                                .setColumns(col -> {
                                    for (String selfMappingProperty : selfMappingProperties) {
                                        //noinspection EasyQuerySetColumns
                                        col.columnNull(selfMappingProperty);
                                    }
                                    for (String targetMappingProperty : targetMappingProperties) {
                                        //noinspection EasyQuerySetColumns
                                        col.columnNull(targetMappingProperty);
                                    }
                                }).whereColumns(col -> {
                                    for (String selfMappingProperty : selfMappingProperties) {
                                        col.column(selfMappingProperty);
                                    }
                                    for (String targetMappingProperty : targetMappingProperties) {
                                        col.column(targetMappingProperty);
                                    }
                                }).executeRows();
                    }
                }
            }
        }
        if (deleteAll) {
            if (!saveBehavior.hasBehavior(SaveBehaviorEnum.ROOT_IGNORE)) {
                easyQueryClient.deletable(updates).allowDeleteStatement(true).batch(batch).executeRows();
            }
        } else {
            if (!saveBehavior.hasBehavior(SaveBehaviorEnum.ROOT_IGNORE)) {
                inserts.forEach(o -> {
                    setPrimaryKeyOnInsert(o, entityMetadata);
                });
                easyQueryClient.insertable(inserts).batch(batch).executeRows(insertFillAutoIncrement(entityMetadata));
                if (!saveBehavior.hasBehavior(SaveBehaviorEnum.ROOT_UPDATE_IGNORE)) {
                    easyQueryClient.updatable(updates).batch(batch).executeRows();
                }
            }
        }
        for (int i = 0; i < savableContexts.size(); i++) {
            SavableContext savableContext = savableContexts.get(i);
            for (Map.Entry<NavigateMetadata, SaveNode> nodeKv : savableContext.getSaveNodeMap().entrySet()) {
                SaveNode saveNode = nodeKv.getValue();

                List<Object> insertItems = EasyCollectionUtil.mapFilterSelect(saveNode.getEntityItems(), kv -> kv.getValue().getType() == SaveNodeTypeEnum.INSERT, kv -> {
                    kv.getValue().executeBefore(kv.getKey().getEntity());
                    Object entity = kv.getKey().getEntity();
                    setPrimaryKeyOnInsert(entity, saveNode.getEntityMetadata());
                    return entity;
                });
                easyQueryClient.insertable(insertItems).batch(batch).executeRows(insertFillAutoIncrement(saveNode.getEntityMetadata()));

                List<Object> setNullItems = EasyCollectionUtil.mapFilterSelect(saveNode.getEntityItems(), kv -> kv.getValue().getType() == SaveNodeTypeEnum.DELETE && kv.getValue().getDbType() == SaveNodeDbTypeEnum.UPDATE, kv -> {
                    kv.getValue().executeBefore(kv.getKey().getEntity());
                    return kv.getKey().getEntity();
                });
                easyQueryClient.updatable(setNullItems).batch(batch).executeRows();
                List<Object> updateItems = EasyCollectionUtil.mapFilterSelect(saveNode.getEntityItems(), kv -> kv.getValue().getType() == SaveNodeTypeEnum.UPDATE || kv.getValue().getType() == SaveNodeTypeEnum.CHANGE, kv -> {
                    kv.getValue().executeBefore(kv.getKey().getEntity());
                    return kv.getKey().getEntity();
                });
                easyQueryClient.updatable(updateItems).batch(batch).executeRows();
            }
        }
    }

    private boolean insertFillAutoIncrement(EntityMetadata entityMetadata) {
        List<String> generatedKeyColumns = entityMetadata.getGeneratedKeyColumns();
        return EasyCollectionUtil.isNotEmpty(generatedKeyColumns);
    }

    private void setPrimaryKeyOnInsert(Object entity, EntityMetadata entityMetadata) {
        primaryKeyInsertProcessor.setPrimaryKeyOnInsert(entity, entityMetadata);
    }
}
