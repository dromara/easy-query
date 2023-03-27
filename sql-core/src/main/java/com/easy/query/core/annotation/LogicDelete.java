package com.easy.query.core.annotation;

import com.easy.query.core.basic.enums.LogicDeleteStrategyEnum;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.annotation.RetentionPolicy;

/**
 * 默认该属性不会出现在对应的update set column处，除非手动指定，并且同一个对象如果有多个属性标识LogicDelete会报错
 * @author xuejiaming
 */
@Documented
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
