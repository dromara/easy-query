package com.easy.query.core.expression.sql.fill;

import java.util.function.BiConsumer;

/**
 * create time 2025/10/29 08:49
 * 文件说明
 *
 * @author xuejiaming
 */
public interface FillContext {
    default FillContext self_target(String selfProp, String targetProp) {
        return self_target(selfProp, targetProp, false);
    }

    /**
     * 消费函数里面是否
     * @param selfProp 主表属性
     * @param targetProp 目标表属性
     * @param consumeNull 是否处理null
     * @return
     */
    FillContext self_target(String selfProp, String targetProp, boolean consumeNull);

}
