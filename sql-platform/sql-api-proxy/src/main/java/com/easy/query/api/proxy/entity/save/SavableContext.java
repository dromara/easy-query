package com.easy.query.api.proxy.entity.save;

import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.metadata.NavigateMetadata;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * create time 2025/9/5 20:29
 * 文件说明
 *
 * @author xuejiaming
 */
public class SavableContext {
    private final Map<NavigateMetadata, SaveNode> saveNodeMap;
    private final int index;

    public SavableContext(int index) {
        this.index = index;
        this.saveNodeMap = new LinkedHashMap<>();
    }

    public Map<NavigateMetadata, SaveNode> getSaveNodeMap() {
        return saveNodeMap;
    }

    public SaveNode createSaveNodeMap(NavigateMetadata navigateMetadata, EntityMetadata entityMetadata) {
        return saveNodeMap.computeIfAbsent(navigateMetadata, k -> new SaveNode(this.index, entityMetadata));
    }
}
