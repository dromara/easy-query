package com.easy.query.core.basic.extension.version;

import com.easy.query.core.metadata.EntityMetadata;

/**
 * create time 2023/4/8 13:26
 * 文件说明
 *
 * @author xuejiaming
 */
public class VersionLongStrategy implements VersionStrategy {
    @Override
    public Object nextVersion(EntityMetadata entityMetadata, String propertyName, Object version) {
        return (long)version + 1;
    }
}
