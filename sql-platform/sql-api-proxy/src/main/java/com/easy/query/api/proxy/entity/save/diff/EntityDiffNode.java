package com.easy.query.api.proxy.entity.save.diff;

/**
 * create time 2025/11/29 20:50
 * 文件说明
 *
 * @author xuejiaming
 */
public class EntityDiffNode {
    private final PropertyDiffNode propertyDiffNode;

    private final Object before;
    private final Object after;

    public EntityDiffNode(Object before, Object after, PropertyDiffNode propertyDiffNode) {
        this.before = before;
        this.after = after;
        this.propertyDiffNode = propertyDiffNode;
    }

    public Object getBefore() {
        return before;
    }

    public Object getAfter() {
        return after;
    }


    public boolean isInsert() {
        return before == null;
    }

    public boolean isUpdate() {
        return before != null && after != null;
    }

    public boolean isDelete() {
        return before != null && after == null;
    }

    /**
     * 所属属性节点
     * @return
     */
    public PropertyDiffNode getPropertyOwnerNode() {
        return propertyDiffNode;
    }
}
