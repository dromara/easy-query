package com.easy.query.sql.starter.config;

import java.util.Set;

/**
 * create time 2023/8/4 15:01
 * 文件说明
 *
 * @author xuejiaming
 */
public interface JdbcTypeHandlerReplaceConfigurer {
    boolean replace();
    Set<Class<?>> allowTypes();
}
