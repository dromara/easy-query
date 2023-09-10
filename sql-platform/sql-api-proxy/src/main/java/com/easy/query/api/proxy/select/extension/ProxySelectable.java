package com.easy.query.api.proxy.select.extension;

import com.easy.query.api.proxy.select.ProxyQueryable;
import com.easy.query.core.expression.segment.ColumnSegment;
import com.easy.query.core.proxy.ProxyEntity;

import java.util.Collection;
import java.util.Collections;

/**
 * create time 2023/9/10 12:57
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ProxySelectable<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1> {


    /**
     * 将当前T1对象转成TR对象，select会将T1属性所对应的列名映射到TR对象的列名上(忽略大小写)
     * T1.property1列名叫做column1,T1.property2列名叫做column2，TR.property3的列名也叫column1
     * 那么生成的sql为:select column1 from t1
     * 如果当前存在join，那么join的子表一律不会映射到resultClass上,如果需要那么请手动调用双参数select
     *
     * @param trProxy
     * @param <TRProxy>
     * @param <TR>
     * @return
     */
    <TRProxy extends ProxyEntity<TRProxy, TR>, TR> ProxyQueryable<TRProxy, TR> select(ProxyEntity<TRProxy, TR> trProxy);

    default ProxyQueryable<T1Proxy, T1> select(ColumnSegment columnSegment, boolean clearAll) {
        return select(Collections.singletonList(columnSegment), clearAll);
    }

    ProxyQueryable<T1Proxy, T1> select(Collection<ColumnSegment> columnSegments, boolean clearAll);

}
