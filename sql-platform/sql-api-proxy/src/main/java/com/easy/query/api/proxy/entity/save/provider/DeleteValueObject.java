package com.easy.query.api.proxy.entity.save.provider;

import com.easy.query.api.proxy.entity.save.SaveNode;
import com.easy.query.core.metadata.EntityMetadata;

/**
 * create time 2025/9/13 23:24
 * 文件说明
 *
 * @author xuejiaming
 */
public class DeleteValueObject {
    public final Object target;
    public final EntityMetadata targetEntityMetadata;
    public final SaveNode saveNode;

    public DeleteValueObject(Object target, EntityMetadata targetEntityMetadata, SaveNode saveNode) {
        this.target = target;
        this.targetEntityMetadata = targetEntityMetadata;
        this.saveNode = saveNode;
    }
}
