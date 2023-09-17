//package com.easy.query.api.proxy.sql.impl;
//
//import com.easy.query.api.proxy.sql.ProxyColumnOnlySelector;
//import com.easy.query.core.expression.builder.OnlySelector;
//import com.easy.query.core.expression.builder.core.SQLNative;
//import com.easy.query.core.proxy.ProxyEntity;
//import com.easy.query.core.util.EasyObjectUtil;
//
///**
// * create time 2023/6/25 19:15
// * 文件说明
// *
// * @author xuejiaming
// */
//public class ProxyUpdateSetSelectorImpl<TProxy extends ProxyEntity<TProxy,T>,T> implements ProxyColumnOnlySelector<TProxy,T> {
//    private final OnlySelector updateSetSelector;
//    private final TProxy proxy;
//
//    public ProxyUpdateSetSelectorImpl(OnlySelector updateSetSelector, TProxy proxy){
//
//        this.updateSetSelector = updateSetSelector;
//        this.proxy = proxy;
//    }
//    @Override
//    public OnlySelector getOnlySelector() {
//        return updateSetSelector;
//    }
//
//    @Override
//    public <TEntity> SQLNative<TEntity> getSQLNative() {
//        return EasyObjectUtil.typeCastNullable(updateSetSelector);
//    }
//
//    @Override
//    public ProxyColumnOnlySelector<TProxy,T> castTChain() {
//        return this;
//    }
//}
