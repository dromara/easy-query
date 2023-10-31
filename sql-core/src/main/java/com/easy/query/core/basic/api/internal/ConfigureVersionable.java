package com.easy.query.core.basic.api.internal;

/**
 * create time 2023/7/31 10:39
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ConfigureVersionable<TChain> {

    /**
     * 无版本号报错,请使用{@link #ignoreVersion}
     *
     * @return
     */
    @Deprecated
    TChain noVersionError();

    /**
     * 无版本号忽略不报错,请使用{@link #ignoreVersion}
     *
     * @return
     */
    @Deprecated
    TChain noVersionIgnore();

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
