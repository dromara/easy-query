package com.easy.query.core.basic.api.internal;


/**
 * create time 2023/4/8 12:21
 * 文件说明
 *
 * @author xuejiaming
 */
public interface Versionable<TChain> {


    default TChain withVersion(Object versionValue){
        return withVersion(true,versionValue);
    }
    TChain withVersion(boolean condition,Object versionValue);

    TChain noVersionError();
    TChain noVersionIgnore();
}
