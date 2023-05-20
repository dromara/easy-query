package com.easy.query.core.basic.plugin.interceptor;

/**
 * @FileName: GlobalInterceptor.java
 * @Description: 文件说明
 * @Date: 2023/3/9 21:35
 * @author xuejiaming
 */
public interface Interceptor {
    /**
     * 越小越先执行
     * @return
     */
    default int order(){
        return 100;
    }

    /**
     * 默认生效
     * @return
     */
    default boolean defaultEnable(){return true;}
    /**
     * 拦截器名称
     * @return
     */
    String name();

    /**
     * 是否接受当前拦截器
     * @param entityClass
     * @return
     */
    boolean apply(Class<?> entityClass);
}
