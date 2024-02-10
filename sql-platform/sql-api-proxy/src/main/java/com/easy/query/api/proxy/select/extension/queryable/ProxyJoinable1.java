package com.easy.query.api.proxy.select.extension.queryable;

import com.easy.query.api.proxy.select.ProxyQueryable;
import com.easy.query.api.proxy.select.ProxyQueryable2;
import com.easy.query.api.proxy.select.extension.queryable2.sql.MultiProxyFilter2;
import com.easy.query.api.proxy.select.extension.queryable2.sql.impl.MultiProxyFilter2Impl;
import com.easy.query.api.proxy.select.impl.EasyProxyQueryable2;
import com.easy.query.core.basic.api.select.ClientQueryable2;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.proxy.ProxyEntity;

/**
 * create time 2023/8/17 11:19
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ProxyJoinable1<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1> extends ClientProxyQueryableAvailable<T1>,ProxyQueryableAvailable<T1Proxy,T1>{

   default  <T2Proxy extends ProxyEntity<T2Proxy, T2>, T2> ProxyQueryable2<T1Proxy, T1, T2Proxy, T2> leftJoin(T2Proxy joinProxy, SQLExpression1<MultiProxyFilter2<T1Proxy, T2Proxy>> on){
       ClientQueryable2<T1, T2> entityQueryable2 = getClientQueryable().leftJoin(joinProxy.getEntityClass(), (t, t1) -> {
           on.apply(new MultiProxyFilter2Impl<>(t.getFilter(), get1Proxy(), joinProxy.create(t1.getTable(),getClientQueryable().getSQLEntityExpressionBuilder(), getRuntimeContext())));
       });
       return new EasyProxyQueryable2<>(get1Proxy(), joinProxy, entityQueryable2);
   }

    default <T2Proxy extends ProxyEntity<T2Proxy, T2>, T2> ProxyQueryable2<T1Proxy, T1, T2Proxy, T2> leftJoin(ProxyQueryable<T2Proxy, T2> joinQueryable, SQLExpression1<MultiProxyFilter2<T1Proxy, T2Proxy>> on){
        ClientQueryable2<T1, T2> entityQueryable2 = getClientQueryable().leftJoin(joinQueryable.getClientQueryable(), (t, t1) -> {
            on.apply(new MultiProxyFilter2Impl<>(t.getFilter(), get1Proxy(), joinQueryable.get1Proxy().create(t1.getTable(),getClientQueryable().getSQLEntityExpressionBuilder(), getRuntimeContext())));
        });
        return new EasyProxyQueryable2<>(get1Proxy(), joinQueryable.get1Proxy(), entityQueryable2);
    }

   default  <T2Proxy extends ProxyEntity<T2Proxy, T2>, T2> ProxyQueryable2<T1Proxy, T1, T2Proxy, T2> rightJoin(T2Proxy joinProxy, SQLExpression1<MultiProxyFilter2<T1Proxy, T2Proxy>> on){
       ClientQueryable2<T1, T2> entityQueryable2 = getClientQueryable().rightJoin(joinProxy.getEntityClass(), (t, t1) -> {
           on.apply(new MultiProxyFilter2Impl<>(t.getFilter(), get1Proxy(), joinProxy.create(t1.getTable(),getClientQueryable().getSQLEntityExpressionBuilder(), getRuntimeContext())));
       });
       return new EasyProxyQueryable2<>(get1Proxy(), joinProxy, entityQueryable2);
   }

   default  <T2Proxy extends ProxyEntity<T2Proxy, T2>, T2> ProxyQueryable2<T1Proxy, T1, T2Proxy, T2> rightJoin(ProxyQueryable<T2Proxy, T2> joinQueryable, SQLExpression1<MultiProxyFilter2<T1Proxy, T2Proxy>> on){
       ClientQueryable2<T1, T2> entityQueryable2 = getClientQueryable().rightJoin(joinQueryable.getClientQueryable(), (t, t1) -> {
           on.apply(new MultiProxyFilter2Impl<>(t.getFilter(), get1Proxy(), joinQueryable.get1Proxy().create(t1.getTable(),getClientQueryable().getSQLEntityExpressionBuilder(), getRuntimeContext())));
       });
       return new EasyProxyQueryable2<>(get1Proxy(), joinQueryable.get1Proxy(), entityQueryable2);

   }

   default  <T2Proxy extends ProxyEntity<T2Proxy, T2>, T2> ProxyQueryable2<T1Proxy, T1, T2Proxy, T2> innerJoin(T2Proxy joinProxy, SQLExpression1<MultiProxyFilter2<T1Proxy, T2Proxy>> on){
       ClientQueryable2<T1, T2> entityQueryable2 = getClientQueryable().innerJoin(joinProxy.getEntityClass(), (t, t1) -> {
           on.apply(new MultiProxyFilter2Impl<>(t.getFilter(), get1Proxy(), joinProxy.create(t1.getTable(),getClientQueryable().getSQLEntityExpressionBuilder(), getRuntimeContext())));
       });
       return new EasyProxyQueryable2<>(get1Proxy(), joinProxy, entityQueryable2);
   }

   default  <T2Proxy extends ProxyEntity<T2Proxy, T2>, T2> ProxyQueryable2<T1Proxy, T1, T2Proxy, T2> innerJoin(ProxyQueryable<T2Proxy, T2> joinQueryable, SQLExpression1<MultiProxyFilter2<T1Proxy, T2Proxy>> on){
       ClientQueryable2<T1, T2> entityQueryable2 = getClientQueryable().innerJoin(joinQueryable.getClientQueryable(), (t, t1) -> {
           on.apply(new MultiProxyFilter2Impl<>(t.getFilter(), get1Proxy(), joinQueryable.get1Proxy().create(t1.getTable(),getClientQueryable().getSQLEntityExpressionBuilder(), getRuntimeContext())));
       });
       return new EasyProxyQueryable2<>(get1Proxy(), joinQueryable.get1Proxy(), entityQueryable2);

   }



}
