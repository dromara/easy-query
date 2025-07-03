package com.easy.query.core.trigger;

import com.easy.query.core.context.QueryRuntimeContext;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * create time 2025/7/3 13:32
 * 文件说明
 *
 * @author xuejiaming
 */
public class DefaultEntityExpressionTrigger implements EntityExpressionTrigger {
    private final List<Consumer<TriggerEvent>> triggers = new ArrayList<>();

    @Override
    public void trigger(@NotNull Class<?> entityClass, @Nullable List<?> entities, @NotNull TriggerTypeEnum type, @NotNull LocalDateTime triggerTime, @NotNull QueryRuntimeContext runtimeContext) {
        TriggerEvent triggerEvent = new TriggerEvent(entityClass, entities, type, triggerTime, runtimeContext);
        for (Consumer<TriggerEvent> trigger : triggers) {
            trigger.accept(triggerEvent);
        }
    }

    public void addTriggerListener(Consumer<TriggerEvent> trigger) {
        triggers.add(trigger);
    }
}
