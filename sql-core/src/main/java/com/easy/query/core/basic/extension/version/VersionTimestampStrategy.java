package com.easy.query.core.basic.extension.version;

import com.easy.query.core.metadata.EntityMetadata;

/**
 * create time 2023/4/8 13:28
 * 不推荐使用 后续版本将会删除
 *
 * @author xuejiaming
 */
@Deprecated
public class VersionTimestampStrategy implements VersionStrategy {
    @Override
    public Object nextVersion(EntityMetadata entityMetadata, String propertyName, Object version) {
        return System.currentTimeMillis();
    }
}
