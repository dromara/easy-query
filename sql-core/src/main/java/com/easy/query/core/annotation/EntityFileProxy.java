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
     * 不建议设置别名
     * @return
     */
    String value() default "";

    /**
     * 需要忽略生成的属性
     * @return
     */
    String[] ignoreProperties() default {};

    /**
     * 默认直接覆盖全部编译
     * @return
     */
    FileGenerateEnum strategy() default FileGenerateEnum.GENERATE_COMPILE_ALWAYS_OVERRIDE;

    /**
     * 提供给插件
     * 兼容旧版本的eq,因为插件升级到最新版本如果eq没有升级到最新那么插件生成的代码将是有问题的,
     * 为了兼容1.x版本的eq的apt所以这边增加了版本号这个值,请不要随意修改这个值并且这个值与eq版本不一一对应
     * 无或者1表示第一个版本
     * 2表示第二个版本columnType生成
     * @return
     */
    int version() default 2;

    /**
     * 提供给插件
     * 修正版本号
     * @return
     */
    int revision() default 9;
    /**
     * 生成包名
     * @return
     */
    String generatePackage() default "";
}