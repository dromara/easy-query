package com.easy.query.core.basic.plugin.version;

import com.easy.query.core.metadata.EntityMetadata;

/**
 * create time 2023/4/8 13:28
 * 文件说明
 *
 * @author xuejiaming
 */
public class VersionTimestampStrategy implements VersionStrategy {
    @Override
    public Object nextVersion(EntityMetadata entityMetadata, String propertyName, Object version) {
        return System.currentTimeMillis();
    }
}
