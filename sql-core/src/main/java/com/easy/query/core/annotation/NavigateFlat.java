package com.easy.query.core.annotation;


import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * create time 2024/5/14 08:41
 * entity对象表不会生效
 * NavigateFlat支持获取穿透数据库对象到VO
 * 如果NavigateFlat了一个VO又在同级对象下获取了这个VO的id那么将会报错
 * 目标对象必须是集合不可以是单个对象如果是单个对象必须是基本类型或数据库实体
 *
 * @author xuejiaming
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE})
public @interface NavigateFlat {

    /**
     * 使用静态属性MappingPath来制定路径,值为静态属性别名
     *
     * <blockquote><pre>
     * {@code
     *     //告诉框架获取的路径是用户下的roles下的menus下的id
     *     private static final MappingPath MENU_IDS_PATH = SysUserProxy.TABLE.roles().flatElement().menus().flatElement().id();
     *
     *     @NavigateFlat(pathAlias = "MENU_IDS_PATH")
     *     private List<String> menuIds;
     * }
     * </pre></blockquote>
     * @return 静态路径名称
     */
    String pathAlias() default "";

    /**
     * 路径别名是否是前缀
     * 如果是前缀则不需要将别名一直写写到属性而是使用当前属性名作为拉取的属性
     *
     * @return
     */
    boolean prefix() default false;

}
