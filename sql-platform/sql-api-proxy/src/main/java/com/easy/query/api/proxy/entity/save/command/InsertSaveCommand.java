package com.easy.query.api.proxy.entity.save.command;

import com.easy.query.api.proxy.entity.save.SavableContext;
import com.easy.query.api.proxy.entity.save.SaveCommandContext;
import com.easy.query.api.proxy.entity.save.SaveNode;
import com.easy.query.api.proxy.entity.save.provider.InsertSaveProvider;
import com.easy.query.core.api.client.EasyQueryClient;
import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.metadata.NavigateMetadata;
import com.easy.query.core.util.EasyCollectionUtil;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * create time 2025/9/6 21:07
 * 文件说明
 *
 * @author xuejiaming
 */
public class InsertSaveCommand implements SaveCommand {
    private final EntityMetadata entityMetadata;
    private final List<Object> entities;
    private final EasyQueryClient easyQueryClient;
    private final SaveCommandContext saveCommandContext;

    public InsertSaveCommand(EntityMetadata entityMetadata, List<Object> entities, EasyQueryClient easyQueryClient, SaveCommandContext saveCommandContext) {
        this.entityMetadata = entityMetadata;
        this.entities = entities;
        this.easyQueryClient = easyQueryClient;
        this.saveCommandContext = saveCommandContext;
    }

    @Override
    public void execute(boolean batch) {
        easyQueryClient.insertable(entities).batch(batch).executeRows(insertFillAutoIncrement(entityMetadata));
        List<SavableContext> savableContexts = this.saveCommandContext.getSavableContexts();
        for (int i = 0; i < savableContexts.size(); i++) {
            SavableContext savableContext = savableContexts.get(i);
            for (Map.Entry<NavigateMetadata, SaveNode> nodeKv : savableContext.getSaveNodeMap().entrySet()) {
                SaveNode saveNode = nodeKv.getValue();
                List<Object> inserts = saveNode.getInserts().stream().map(o->{
                    o.insertBefore();
                    return o.getEntity();
                }).collect(Collectors.toList());
                easyQueryClient.insertable(inserts).batch(batch).executeRows(insertFillAutoIncrement(saveNode.getEntityMetadata()));
            }
        }
    }

    private boolean insertFillAutoIncrement(EntityMetadata entityMetadata) {
        List<String> generatedKeyColumns = entityMetadata.getGeneratedKeyColumns();
        return EasyCollectionUtil.isNotEmpty(generatedKeyColumns);
    }
}
