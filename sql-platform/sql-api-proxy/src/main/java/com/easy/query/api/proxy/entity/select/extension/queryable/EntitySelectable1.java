package com.easy.query.api.proxy.entity.select.extension.queryable;

import com.easy.query.api.proxy.entity.select.EntityQueryable;
import com.easy.query.core.annotation.EntityFileProxy;
import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.basic.api.select.Query;
import com.easy.query.core.expression.lambda.SQLFuncExpression1;
import com.easy.query.core.expression.segment.ColumnSegment;
import com.easy.query.core.proxy.PropTypeColumn;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.SQLSelectAsExpression;
import com.easy.query.core.proxy.SQLSelectExpression;

import java.util.Collection;
import java.util.Collections;

/**
 * create time 2023/8/16 08:47
 * 文件说明
 *
 * @author xuejiaming
 */
public interface EntitySelectable1<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1> {


    /**
     * 返回自定义形状的结果代理对象,由{@link EntityProxy}或者{@link EntityFileProxy}生成
     * <blockquote><pre>
     *     {@code
     *          //表示入参o表全部映射到返回结果TopicProxy中
     *         .select(o->new TopicProxy())
     *          //表示入参o表自定义映射到返回结果TopicProxy中
     *          //SELECT t.`id` as `id`,t.`title` as `title` FROM ....
     *         .select(o->new TopicProxy()
     *               .id().set(o.id())
     *               .title().set(o.title()))
     *
     *          //最原始的写法和上述adapter一致
     *         .select(o->{
     *            TopicProxy r=new TopicProxy();
     *            r.id().set(o.id()); //t.`id` as `id`
     *            r.title().set(o.title())//t.`title` as `title`
     *            return r;
     *         })
     *          //表示入参o表自定义映射到返回结果TopicProxy中除了title 也可以不需要写到adapter内部
     *         .select(o->new TopicProxy()
     *              .selectAll(o);//相当于t.*
     *              .selectIgnores(o.title());//相当于从t.*中移除title列
     *         )
     *          //如果您的映射列名都一样那么可以通过selectExpression来处理大部分列
     *         .select(o->new TopicProxy().selectExpression(o.FETCHER.id().name().phone().departName()))
     *          //返回自身仅查询两列
     *          .select(t -> t.FETCHER.id().stars())
     *          //返回自身仅查询两列并且支持后续继续where或者join之类的操作
     *          .select(t -> t.FETCHER.id().stars().fetchProxy()).where(......)
     *          //不需要后续where等操作可以不加.fetchProxy()
     *          .select(t -> t.FETCHER.id().stars())
     *          //返回基本类型
     *          .select(t->t.id())
     *                 }
     * </pre></blockquote>
     *
     * @param selectExpression 入参表达式返回需要转成需要的结果便于后续操作
     * @param <TRProxy>        返回结果的对象代理类型
     * @param <TR>             返回结果的对象类型
     * @return 返回新的结果操作表达式可以继续筛选处理
     */
    <TRProxy extends ProxyEntity<TRProxy, TR>, TR> EntityQueryable<TRProxy, TR> select(SQLFuncExpression1<T1Proxy, TRProxy> selectExpression);


    /**
     * 快速读取单列用于返回基本类型或者subQuery等查询
     * <blockquote><pre>
     *     {@code
     *
     *          //如果您是枚举需要单独查询请转成integer或者具体数据库对应的值
     *          //直接返回单个列如果是Enum类型的不支持
     *         .selectColumn(o -> o.enumProp().toNumber(Integer.class))
     *          //快速生成子查询
     *          Query<Enum> query = easyEntityQuery.queryable(EntityClass.class).where(o -> o.id().eq("123" )).selectColumn(o -> o.enumProp());
     *         List<EntityClass> list = easyEntityQuery.queryable(EntityClass.class).where(o -> {
     *             o.enumProp().in(query);
     *         }).toList();
     *
     *
     *                 }
     * </pre></blockquote>
     *
     * @param selectExpression
     * @param <TR>
     * @return
     */
    <TR> Query<TR> selectColumn(SQLFuncExpression1<T1Proxy, PropTypeColumn<TR>> selectExpression);
//    <TRProxy extends ProxyEntity<TRProxy, TR>, TR> Query<List<TR>> selectMany(SQLFuncExpression1<T1Proxy, SQLQueryable<TRProxy,TR>> selectExpression);

    /**
     * 返回select类型不支持后续链式
     *
     * <blockquote><pre>
     *     {@code
     *          //如果您是枚举需要单独查询请转成integer或者具体数据库对应的值
     *          //直接返回单个列如果是Enum类型的不支持
     *         .select(ResultEntity.class)
     *
     *                 }
     * </pre></blockquote>
     *
     * @param resultClass
     * @param <TR>
     * @return
     */
    <TR> Query<TR> select(Class<TR> resultClass);

    <TR> Query<TR> select(Class<TR> resultClass, SQLFuncExpression1<T1Proxy, SQLSelectAsExpression> selectExpression);

    /**
     * 自动查询结构化DTO
     * <blockquote><pre>
     *     {@code
     *      List<SchoolClassAOProp6> list = easyEntityQuery.queryable(SchoolClass.class)
     *                         .selectAutoInclude(SchoolClassAOProp6.class)
     *                         .toList();
     *
     *                 }
     * </pre></blockquote>
     *
     * @param resultClass 返回的结构化dto
     * @param <TR>
     * @return
     */
    default <TR> Query<TR> selectAutoInclude(Class<TR> resultClass) {
        return selectAutoInclude(resultClass, x -> null, false);
    }

    /**
     * 自动查询结构化DTO
     * <blockquote><pre>
     *     {@code
     *      List<SchoolClassAOProp6> list = easyEntityQuery.queryable(SchoolClass.class)
     *                         .selectAutoInclude(SchoolClassAOProp6.class,s->Select.of(
     *                                 //s.FETCHER.allFields(), //不需要执行
     *                                 s.company().name().nullOrDefault("1").as("name1")
     *                         ))
     *                         .toList();
     *
     *                 }
     * </pre></blockquote>
     *
     * @param resultClass           返回的结构化dto
     * @param extraSelectExpression 额外select表达式增强,默认会执行映射allFields所以不再需要执行主表的allFields
     * @param <TR>
     * @return
     */
    default <TR> Query<TR> selectAutoInclude(Class<TR> resultClass, SQLFuncExpression1<T1Proxy, SQLSelectAsExpression> extraSelectExpression) {
        return selectAutoInclude(resultClass, extraSelectExpression, false);
    }

    /**
     * 自动查询结构化DTO
     * <blockquote><pre>
     *     {@code
     *      List<SchoolClassAOProp6> list = easyEntityQuery.queryable(SchoolClass.class)
     *                         .selectAutoInclude(SchoolClassAOProp6.class,s->Select.of(
     *                                 //s.FETCHER.allFields(), //不需要执行
     *                                 s.company().name().nullOrDefault("1").as("name1")
     *                         ),false)
     *                         .toList();
     *
     *                 }
     * </pre></blockquote>
     *
     * @param resultClass           返回的结构化dto
     * @param extraSelectExpression 额外select表达式增强,默认会执行映射allFields所以不再需要执行主表的allFields
     * @param replace               false:表示当前的selectAutoInclude里面的东西不去替换前面的include,如果false表示以表达式前面的include为准,true:表示以selectAutoInclude里面的为准
     * @param <TR>
     * @return
     */
    <TR> Query<TR> selectAutoInclude(Class<TR> resultClass, SQLFuncExpression1<T1Proxy, SQLSelectAsExpression> extraSelectExpression, boolean replace);

    default EntityQueryable<T1Proxy, T1> select(ColumnSegment columnSegment, boolean clearAll) {
        return select(Collections.singletonList(columnSegment), clearAll);
    }

    EntityQueryable<T1Proxy, T1> select(Collection<ColumnSegment> columnSegments, boolean clearAll);

}
