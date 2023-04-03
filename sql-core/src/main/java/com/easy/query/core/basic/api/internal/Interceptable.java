package com.easy.query.core.basic.api.internal;

/**
 * create time 2023/3/31 22:00
 * 文件说明
 *
 * @author xuejiaming
 */
public interface Interceptable<TChain> {

    /**
     * 当前表达式不使用拦截器,并且删除之前手动指定的 {@link #noInterceptor(String name)}、{@link #useInterceptor(String name)}
     * @return
     */
    TChain noInterceptor();

    /**
     * 使用某个拦截器
     * @param name
     * @return
     */
    TChain useInterceptor(String name);

    /**
     * 不使用某个拦截器
     * @param name
     * @return
     */
    TChain noInterceptor(String name);

    /**
     * 当前表达式启用拦截器,并且删除之前手动指定的 {@link #noInterceptor(String name)}、{@link #useInterceptor(String name)}
     * @return
     */
    TChain useInterceptor();
}
