package com.easy.query.core.annotation;

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
public @interface EntityProxy {
    /**
     * 设置代理实例对象名称比如'SysUserProxy'
     * 不建议设置别名
     * @return
     */
    String value() default "";
    /**
     * 生成代理对象扩展
     * @return
     */
    Class<?> extension() default Object.class;

    /**
     * 需要忽略生成的属性
     * @return
     */
    String[] ignoreProperties() default {};

    /**
     * 兼容旧版本的eq,因为插件升级到最新版本如果eq没有升级到最新那么插件生成的代码将是有问题的,
     * 为了兼容1.x版本的eq的apt所以这边增加了版本号这个值,请不要随意修改这个值
     * @return
     */
    int version() default 2;
}