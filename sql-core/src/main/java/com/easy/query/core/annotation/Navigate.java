package com.easy.query.core.annotation;

import com.easy.query.core.basic.extension.navigate.DefaultNavigateExtraFilterStrategy;
import com.easy.query.core.basic.extension.navigate.NavigateExtraFilterStrategy;
import com.easy.query.core.enums.RelationTypeEnum;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * create time 2023/6/17 19:21
 * 导航属性 关联查询
 * 如果用到非数据库对象譬如VO对象上面,那么只需要定义RelationType即可,其余属性定义了也会被忽略
 *
 * @author xuejiaming
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE})
public @interface Navigate {
    /**
     * 关联关系
     */
    RelationTypeEnum value();

    /**
     * 当前对象的哪个属性关联目标对象,空表示使用当前对象的主键
     *
     * @return
     */
    String[] selfProperty() default {};

    /**
     * 当前对象的{@param selfProperty}属性关联目标的哪个属性,空表示使用目标对象的主键
     */
    String[] targetProperty() default {};

    /**
     * 多对多填写
     * 多对多中间表 中间表必须是表对象 多对多不能为空
     *
     * @return
     */
    Class<?> mappingClass() default Object.class;

    /**
     * 多对多填写
     * 当前对象的{@param selfProperty}属性对应中间表的哪个属性,多对多不能为空
     *
     * @return
     */
    String[] selfMappingProperty() default {};

    /**
     * 多对多填写
     * 目标对象的{@param targetProperty}属性对应中间表的哪个属性,多对多不能为空
     *
     * @return
     */
    String[] targetMappingProperty() default {};

    /**
     * 属性是否是代理对象
     *
     * @return
     */
    boolean propIsProxy() default true;

    /**
     * toMany数据支持排序
     * <blockquote><pre>
     *     {@code
     *          @Navigate(value = RelationTypeEnum.OneToMany, selfProperty = {"comId", "userId"}, targetProperty = {"comId", "userId"}, orderByProps = {
     *               @OrderByProperty(property = "comId"),
     *               @OrderByProperty(property = "time", asc = false, mode = OrderByPropertyModeEnum.NULLS_FIRST),
     *          })
     *      }
     * </pre></blockquote>
     *
     * @return
     */
    OrderByProperty[] orderByProps() default {};

    /**
     * 偏移量
     *
     * @return
     */
    long offset() default 0;

    /**
     * 拉取数量
     *
     * @return
     */
    long limit() default 0;


    /**
     * 仅entity生效
     *
     * @return
     */

    Class<? extends NavigateExtraFilterStrategy> extraFilter() default DefaultNavigateExtraFilterStrategy.class;

    /**
     * xToOne中间表
     *
     * @return
     */
    String[] directMapping() default {};

    /**
     * 属性关联策略
     *
     * @return
     */
    String relationPropertyStrategy() default "";

    /**
     * 表示目标对象是否必须存在
     * 作用如果你是ManyToOne或者OneToOne则隐式join会变成inner join
     * 如果你是OneToMany或者ManyToMany那么隐式group将会以inner join进行连表 如果是隐式partition by那么index=0也就是firstElement也是inner join
     * 其余情况使用left join
     *
     * @return
     */
    boolean required() default false;

    /**
     * 子查询转groupJoin 仅toMany生效
     * 建议数据量大于20-50w后或者系统明显出现子查询缓慢的情况下开启这个
     * @return
     */
    boolean subQueryToGroupJoin() default false;

    /**
     * 允许非实体也支持导航
     * @return
     */
    boolean supportNonEntity() default false;
}
