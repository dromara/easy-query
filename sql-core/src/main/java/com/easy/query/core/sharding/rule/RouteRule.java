package com.easy.query.core.sharding.rule;

/**
 * create time 2023/4/18 22:57
 * 文件说明
 *
 * @author xuejiaming
 */
public interface RouteRule {
    /**
     * 哪个对象使用分片路由
     * @return 使用分片路由规则的对象字节
     */
    Class<?> entityClass();
}
