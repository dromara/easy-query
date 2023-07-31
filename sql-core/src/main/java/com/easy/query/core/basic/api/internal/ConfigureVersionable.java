package com.easy.query.core.basic.api.internal;

/**
 * create time 2023/7/31 10:39
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ConfigureVersionable<TChain> {

    TChain noVersionError();
    TChain noVersionIgnore();
}
