package com.easy.query.core.basic.plugin.version;

import com.easy.query.core.metadata.EntityMetadata;

/**
 * create time 2023/3/27 09:01
 * 文件说明
 *
 * @author xuejiaming
 */
public interface EasyVersionStrategy {
    Object newVersionValue(EntityMetadata entityMetadata, String propertyName,Object version);
}
