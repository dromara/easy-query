package com.easy.query.core.basic.plugin.version;

import com.easy.query.core.metadata.EntityMetadata;

/**
 * create time 2023/4/8 13:26
 * 文件说明
 *
 * @author xuejiaming
 */
public class VersionIntStrategy implements VersionStrategy {
    @Override
    public Object nextVersion(EntityMetadata entityMetadata, String propertyName, Object version) {
        return (int)version + 1;
    }
}
