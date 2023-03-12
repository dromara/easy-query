package com.easy.query.core.basic.jdbc.types;

import com.easy.query.core.basic.jdbc.types.handler.JdbcTypeHandler;

/**
 * @FileName: EasyJdbcTypeHandler.java
 * @Description: 文件说明
 * @Date: 2023/2/17 22:07
 * @Created by xuejiaming
 */
public interface EasyJdbcTypeHandlerManager {
    void appendHandler(Class<?> type, JdbcTypeHandler typeHandler, boolean replace);
    JdbcTypeHandler getHandler(Class<?> type);
}
