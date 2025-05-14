package com.easy.query.core.basic.extension.interceptor;

/**
 * @FileName: GlobalInterceptor.java
 * @Description: 文件说明
 * create time 2023/3/9 21:35
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
     * 默认生效每次生成sql都会获取判断当前拦截器是否默认使用
     * 可以设置为动态值
     * @return
     */
    default boolean enable(){return true;}
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
