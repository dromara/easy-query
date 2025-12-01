package com.easy.query.api.proxy.entity.save.diff;

import com.easy.query.core.metadata.EntityMetadata;
import com.easy.query.core.metadata.NavigateMetadata;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * create time 2025/11/30 10:54
 * 文件说明
 *
 * @author xuejiaming
 */
public class PropertyDiffNode {

    private final EntityMetadata entityMetadata;
    private final NavigateMetadata navigateMetadata;
    private final int deep;
    private final Map<PropertyKey, PropertyDiffNode> childrenMap;
    private final Set<EntityDiffNode> diffNodes;
    private final PropertyKey propertyKey;

    public PropertyDiffNode(@NotNull PropertyKey propertyKey, @NotNull EntityMetadata entityMetadata, @Nullable NavigateMetadata navigateMetadata, int deep) {
        this.entityMetadata = entityMetadata;
        this.navigateMetadata = navigateMetadata;
        this.deep = deep;
        this.childrenMap = new HashMap<>();
        this.diffNodes = new  LinkedHashSet<>();
        this.propertyKey = propertyKey;
    }

    public EntityMetadata getEntityMetadata() {
        return entityMetadata;
    }

    public NavigateMetadata getNavigateMetadata() {
        return navigateMetadata;
    }

    public List<PropertyDiffNode> getChildren() {
        return new ArrayList<>(childrenMap.values());
    }

    public PropertyDiffNode addChild(PropertyDiffNode propertyDiffNode) {
        return childrenMap.computeIfAbsent(propertyDiffNode.propertyKey, propertyKey1 -> propertyDiffNode);
    }

    public Set<EntityDiffNode> getDiffNodes() {
        return diffNodes;
    }

    public int getDeep() {
        return deep;
    }

    public PropertyKey getNavigateKey() {
        return propertyKey;
    }
}
