package com.easy.query.api.proxy.entity.select.extension.queryable;

import com.easy.query.api.proxy.entity.select.EntityQueryable;
import com.easy.query.core.annotation.EntityFileProxy;
import com.easy.query.core.annotation.EntityProxy;
import com.easy.query.core.basic.api.select.Query;
import com.easy.query.core.basic.jdbc.executor.internal.enumerable.DraftResult;
import com.easy.query.core.expression.lambda.SQLFuncExpression1;
import com.easy.query.core.expression.segment.ColumnSegment;
import com.easy.query.core.proxy.PropTypeColumn;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.core.proxy.SQLSelectAsExpression;
import com.easy.query.core.proxy.SQLSelectExpression;
import com.easy.query.core.proxy.core.draft.DraftFetcher;
import com.easy.query.core.proxy.sql.Select;

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
     * 请使用select
     * 快速选择当前对象
     * <blockquote><pre>
     *     {@code
     *          .select(t -> t.FETCHER.id().createTime().title().fetchProxy())
     *      }
     * </pre></blockquote>
     *
     * @param selectExpression 快速选择表达式
     * @return
     */
    @Deprecated
    EntityQueryable<T1Proxy, T1> fetchBy(SQLFuncExpression1<T1Proxy, SQLSelectExpression> selectExpression);


    /**
     * 返回自定义形状的结果代理对象,由{@link EntityProxy}或者{@link EntityFileProxy}生成
     * <blockquote><pre>
     *     {@code
     *          //表示入参o表全部映射到返回结果TopicProxy中
     *         .select(o->new TopicProxy())
     *          //表示入参o表自定义映射到返回结果TopicProxy中
     *          //SELECT t.`id` as `id`,t.`title` as `title` FROM ....
     *         .select(o->new TopicProxy().adapter(r->{
     *             r.id().set(o.id()); //手动指定赋值
     *             r.title().set(o.title())
     *         }))
     *
     *          //最原始的写法和上述adapter一致
     *         .select(o->{
     *            TopicProxy r=new TopicProxy();
     *            r.id().set(o.id()); //t.`id` as `id`
     *            r.title().set(o.title())//t.`title` as `title`
     *            return r;
     *         })
     *          //表示入参o表自定义映射到返回结果TopicProxy中除了title 也可以不需要写到adapter内部
     *         .select(o->new TopicProxy().adapter(r->{
     *             r.selectAll(o);//相当于t.*
     *             r.selectIgnores(o.title());//相当于从t.*中移除title列
     *         }))
     *          //如果您的映射列名都一样那么可以通过selectExpression来处理大部分列
     *         .select(o->new TopicProxy().selectExpression(o.FETCHER.id().name().phone().departName()).adapter(r->{
     *             //这边处理别名
     *         }))
     *                 }
     * </pre></blockquote>
     *
     * @param selectExpression 入参表达式返回需要转成需要的结果便于后续操作
     * @param <TRProxy>        返回结果的对象代理类型
     * @param <TR>             返回结果的对象类型
     * @return 返回新的结果操作表达式可以继续筛选处理
     */
    <TRProxy extends ProxyEntity<TRProxy, TR>, TR> EntityQueryable<TRProxy, TR> select(SQLFuncExpression1<T1Proxy, TRProxy> selectExpression);
//    default <TRProxy extends ProxyEntity<TRProxy, TR>, TR> EntityQueryable<TRProxy, TR> selectAny(SQLFuncExpression1<T1Proxy, TRProxy> selectExpression){
//        return select(selectExpression);
//    }

    /**
     * 快速读取单列用于返回基本类型或者subQuery等查询
     * <blockquote><pre>
     *     {@code
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

   default  <TR> Query<TR> selectAutoInclude(Class<TR> resultClass){
       return selectAutoInclude(resultClass,false);
   }
    <TR> Query<TR> selectAutoInclude(Class<TR> resultClass,boolean replace);
//    <TR> Query<TR> select(Class<TR> resultClass,SQLFuncExpression1<T1Proxy, SQLSelectAsExpression> selectExpression);


//    <TR> Query<TR> select(Class<TR> resultClass,SQLFuncExpression1<T1Proxy, PropTypeColumn<TR>> selectExpression);


    /**
     * 请使用 select + Select.DRAFT.of
     * 草稿对象返回草稿对象{@link com.easy.query.core.proxy.core.draft.Draft1}到{@link com.easy.query.core.proxy.core.draft.Draft10}
     * 使用 {@link Select#draft(PropTypeColumn)}来构建返回需要列,
     * 支持普通属性列,函数列
     * <blockquote><pre>
     *     {@code
     * List<Draft2<String, Long>> list = entityQuery.queryable(Topic.class)
     *                 .where(o -> {
     *                     o.title().like("123");
     *                     o.createTime().ge(LocalDateTime.of(2022, 2, 1, 3, 4));
     *                 })
     *                 .groupBy(o -> o.id())//多个用GroupBy.of(.....)
     *                 .selectDraft(t -> Select.draft(
     *                         t.key1(),
     *                         t.count()
     *                 ))
     *                 .toList();
     *                 }
     * </pre></blockquote>
     *
     * @param selectExpression
     * @param <TRProxy>
     * @param <TR>
     * @return
     */
    @Deprecated
    <TRProxy extends ProxyEntity<TRProxy, TR>, TR extends ProxyEntityAvailable<TR, TRProxy> & DraftResult> EntityQueryable<TRProxy, TR> selectDraft(SQLFuncExpression1<T1Proxy, DraftFetcher<TR, TRProxy>> selectExpression);

    default EntityQueryable<T1Proxy, T1> select(ColumnSegment columnSegment, boolean clearAll) {
        return select(Collections.singletonList(columnSegment), clearAll);
    }

    EntityQueryable<T1Proxy, T1> select(Collection<ColumnSegment> columnSegments, boolean clearAll);

}
