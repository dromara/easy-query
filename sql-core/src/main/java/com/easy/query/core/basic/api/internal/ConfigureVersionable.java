package com.easy.query.core.basic.api.internal;

/**
 * create time 2023/7/31 10:39
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ConfigureVersionable<TChain> {

    /**
     * 忽略版本号
     *
     * @return
     */
    default TChain ignoreVersion() {
        return ignoreVersion(true);
    }

    /**
     * 忽略版本号
     *
     * @param ignored true忽略 false不忽略
     * @return
     */
    TChain ignoreVersion(boolean ignored);
}
