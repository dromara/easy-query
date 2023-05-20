package com.easy.query.core.metadata;

import com.easy.query.core.basic.plugin.version.VersionStrategy;

/**
 * create time 2023/3/26 22:22
 * 乐观锁元信息
 *
 * @author xuejiaming
 */
public final class VersionMetadata {
    private final String propertyName;
    private final VersionStrategy easyVersionStrategy;

    public VersionMetadata(String propertyName, VersionStrategy easyVersionStrategy){

        this.propertyName = propertyName;
        this.easyVersionStrategy = easyVersionStrategy;
    }
    public String getPropertyName() {
        return propertyName;
    }
    public VersionStrategy getEasyVersionStrategy() {
        return easyVersionStrategy;
    }
}
