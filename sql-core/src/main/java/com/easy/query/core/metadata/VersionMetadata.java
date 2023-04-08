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
    private final EasyVersionStrategy easyVersionStrategy;

    public VersionMetadata(String propertyName,EasyVersionStrategy easyVersionStrategy){

        this.propertyName = propertyName;
        this.easyVersionStrategy = easyVersionStrategy;
    }
    public String getPropertyName() {
        return propertyName;
    }
    public EasyVersionStrategy getEasyVersionStrategy() {
        return easyVersionStrategy;
    }
}
