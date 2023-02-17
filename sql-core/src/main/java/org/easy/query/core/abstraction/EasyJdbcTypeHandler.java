package org.easy.query.core.abstraction;

import org.easy.query.core.executor.type.JdbcTypeHandler;

/**
 * @FileName: EasyJdbcTypeHandler.java
 * @Description: 文件说明
 * @Date: 2023/2/17 22:07
 * @Created by xuejiaming
 */
public interface EasyJdbcTypeHandler {
    void appendHandler(Class type,JdbcTypeHandler typeHandler,boolean replace);
    JdbcTypeHandler getHandler(Class type);
}
