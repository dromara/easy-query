package com.easy.query.core.annotation;

import com.easy.query.core.enums.FileGenerateEnum;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * create time 2023/6/24 14:16
 * 添加到数据库对象或者VO、BO等对象上,通过apt技术生成代理对象实现代理模式的增删改查
 *
 * @author xuejiaming
 */
@Documented
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.TYPE)
public @interface EntityFileProxy {
    /**
     * 设置代理实例对象名称比如'SysUserProxy'
     * @return
     */
    String value() default "";

    /**
     * 需要忽略生成的属性
     * @return
     */
    String[] ignoreProperties() default {};
    FileGenerateEnum strategy() default FileGenerateEnum.GENERATE_CURRENT_COMPILE_OVERRIDE;
}