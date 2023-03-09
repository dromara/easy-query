package org.easy.query.core.annotation;

import org.easy.query.core.basic.enums.LogicDeleteStrategyEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @FileName: LogicDelete.java
 * @Description: 逻辑删除标识
 * @Date: 2023/2/26 22:47
 * @Created by xuejiaming
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.METHOD, ElementType.FIELD})
public @interface LogicDelete {
    LogicDeleteStrategyEnum strategy() default LogicDeleteStrategyEnum.BOOLEAN;

    /**
     * 当strategy为LogicDeleteStrategyEnum.CUSTOM,时通过strategyName匹配逻辑删除策略
     * 存在多个逻辑删除字段以最后一个为准
     * @return
     */
    String strategyName() default "";
}
