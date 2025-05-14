package com.easy.query.core.basic.jdbc.types;

import com.easy.query.core.annotation.NotNull;
import com.easy.query.core.annotation.Nullable;
import com.easy.query.core.basic.jdbc.types.handler.JdbcTypeHandler;

/**
 * @author xuejiaming
 * @FileName: EasyJdbcTypeHandler.java
 * @Description: 文件说明
 * create time 2023/2/17 22:07
 */
public interface JdbcTypeHandlerManager {
    /**
     * 添加自定义类型处理器
     * @param type 自定义类型
     * @param typeHandler 类型处理器
     * @param replace 是否替换
     */
    void appendHandler(@NotNull Class<?> type,@NotNull JdbcTypeHandler typeHandler, boolean replace);

    @NotNull
    JdbcTypeHandler getHandler(@Nullable Class<?> type);

    @NotNull
    JdbcTypeHandler getHandlerByHandlerClass(@Nullable Class<?> handlerType);
}
