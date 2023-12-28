package com.easy.query.api.proxy.entity.select.extension.queryable;

import com.easy.query.api.proxy.entity.select.EntityQueryable;
import com.easy.query.core.basic.jdbc.executor.internal.enumerable.Draft;
import com.easy.query.core.expression.lambda.SQLFuncExpression1;
import com.easy.query.core.expression.segment.ColumnSegment;
import com.easy.query.core.proxy.PropTypeColumn;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.ProxyEntityAvailable;
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


//    /**
//     * 对当前表达式返回自定义select列
//     *
//     * @param selectExpression
//     * @return
//     */
    EntityQueryable<T1Proxy, T1> fetcher(SQLFuncExpression1<T1Proxy, SQLSelectExpression> selectExpression);

    /**
     * 将当前T1对象转成TR对象，select会将T1属性所对应的列名映射到TR对象的列名上(忽略大小写)
     * T1.property1列名叫做column1,T1.property2列名叫做column2，TR.property3的列名也叫column1
     * 那么生成的sql为:select column1 from t1
     * 如果当前存在join，那么join的子表一律不会映射到resultClass上,如果需要那么请手动调用双参数select
     * @param selectExpression
     * @return
     * @param <TRProxy>
     * @param <TR>
     */
    <TRProxy extends ProxyEntity<TRProxy, TR>, TR> EntityQueryable<TRProxy, TR> select(SQLFuncExpression1<T1Proxy, TRProxy> selectExpression);

    /**
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
     *                 .selectDraft(o -> Select.draft(
     *                         o.id(),
     *                         o.id().count()
     *                 ))
     *                 .toList();
     * </pre></blockquote>
     *
     * @param selectExpression
     * @param <TRProxy>
     * @param <TR>
     * @return
     */
    <TRProxy extends ProxyEntity<TRProxy, TR>, TR extends ProxyEntityAvailable<TR, TRProxy> & Draft> EntityQueryable<TRProxy, TR> selectDraft(SQLFuncExpression1<T1Proxy, DraftFetcher<TR, TRProxy>> selectExpression);

    default EntityQueryable<T1Proxy, T1> select(ColumnSegment columnSegment, boolean clearAll) {
        return select(Collections.singletonList(columnSegment), clearAll);
    }

    EntityQueryable<T1Proxy, T1> select(Collection<ColumnSegment> columnSegments, boolean clearAll);

}
