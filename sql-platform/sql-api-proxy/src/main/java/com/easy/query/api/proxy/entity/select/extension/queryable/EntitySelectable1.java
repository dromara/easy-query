package com.easy.query.api.proxy.entity.select.extension.queryable;

import com.easy.query.api.proxy.entity.select.EntityQueryable;
import com.easy.query.core.expression.lambda.SQLFuncExpression1;
import com.easy.query.core.expression.lambda.SQLFuncExpression2;
import com.easy.query.core.expression.segment.ColumnSegment;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.core.proxy.SQLSelect;
import com.easy.query.core.proxy.SQLSelectAs;

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
     * 对当前表达式返回自定义select列
     *
     * @param selectExpression
     * @return
     */
    EntityQueryable<T1Proxy, T1> select(SQLFuncExpression1<T1Proxy, SQLSelect> selectExpression);

    /**
     * 将当前T1对象转成TR对象，select会将T1属性所对应的列名映射到TR对象的列名上(忽略大小写)
     * T1.property1列名叫做column1,T1.property2列名叫做column2，TR.property3的列名也叫column1
     * 那么生成的sql为:select column1 from t1
     * 如果当前存在join，那么join的子表一律不会映射到resultClass上,如果需要那么请手动调用双参数select
     *
     * @param resultEntityClass
     * @return
     * @param <TRProxy>
     * @param <TR>
     */
    <TRProxy extends ProxyEntity<TRProxy, TR>, TR extends ProxyEntityAvailable<TR,TRProxy>> EntityQueryable<TRProxy, TR> select(Class<TR> resultEntityClass);

    /**
     * 设置返回对象，返回对象会根据selectExpression映射相同列名
     * 多次select会在前一次基础上进行对上次结果进行匿名表处理
     *
     * @param resultEntityClass
     * @param selectExpression
     * @return
     * @param <TRProxy>
     * @param <TR>
     */
    <TRProxy extends ProxyEntity<TRProxy, TR>, TR extends ProxyEntityAvailable<TR,TRProxy>> EntityQueryable<TRProxy, TR> select(Class<TR> resultEntityClass, SQLFuncExpression2<T1Proxy,TRProxy, SQLSelectAs> selectExpression);

    default EntityQueryable<T1Proxy, T1> select(ColumnSegment columnSegment, boolean clearAll) {
        return select(Collections.singletonList(columnSegment), clearAll);
    }

    EntityQueryable<T1Proxy, T1> select(Collection<ColumnSegment> columnSegments, boolean clearAll);

}
