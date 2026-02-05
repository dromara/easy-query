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
 * navigateFlat如果展开集合后希望查询单个属性比如toOne的那么应该要把最后一层的集合保留
 * navigateFlat获取方式以实体作为数据接受容器，所以被处理的字段类型必须要和实体对应的字段类型一致
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

    /**
     * 是否为基本类型
     * false: 不清楚/不确定
     * true: 是基本类型
     * 默认值: false (表示不清楚)
     * @return false (表示不清楚) true: 是基本类型
     */
    boolean basicType() default false;

}
