package com.easy.query.core.trigger;

import com.easy.query.core.context.QueryRuntimeContext;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Consumer;

/**
 * create time 2025/7/3 13:25
 * 文件说明
 *
 * @author xuejiaming
 */
public interface EntityExpressionTrigger {
    boolean hasListener();

    /**
     * 是否支持当前类运行时每次触发{@link #trigger(Class, List, TriggerTypeEnum, LocalDateTime, QueryRuntimeContext)}判断
     * @param entityClass
     * @return
     */
    boolean support(@NotNull Class<?> entityClass);

    void trigger(@NotNull Class<?> entityClass, @Nullable List<?> entities, @NotNull TriggerTypeEnum type, @NotNull LocalDateTime triggerTime, @NotNull QueryRuntimeContext runtimeContext);

    void addTriggerListener(Consumer<TriggerEvent> trigger);
}
