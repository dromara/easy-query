package com.easy.query.core.metadata;

import com.easy.query.core.basic.plugin.version.EasyVersionStrategy;

/**
 * create time 2023/3/26 22:22
 * 乐观锁元信息
 *
 * @author xuejiaming
 */
public final class VersionMetadata {
    private final String propertyName;
    private final Class<? extends EasyVersionStrategy> versionStrategy;

    public VersionMetadata(String propertyName, Class<? extends EasyVersionStrategy> versionStrategy){

        this.propertyName = propertyName;
        this.versionStrategy = versionStrategy;
    }
    public String getPropertyName() {
        return propertyName;
    }


    public Class<? extends EasyVersionStrategy> getVersionStrategy() {
        return versionStrategy;
    }
}
