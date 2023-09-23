package com.easy.query.api.proxy.select.extension.queryable;

import com.easy.query.api.proxy.base.LongProxy;
import com.easy.query.api.proxy.select.ProxyQueryable;
import com.easy.query.api.proxy.select.impl.EasyProxyQueryable;
import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.proxy.ProxyEntity;

/**
 * create time 2023/9/23 22:27
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ProxyCountable1<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1>  extends ClientProxyQueryableAvailable<T1> {
    /**
     * SELECT COUNT(*) FROM TABLE
     * @return
     */
   default ProxyQueryable<LongProxy,Long> selectCount(){
       return this.selectCount(LongProxy.createTable());
   }

    /**
     * SELECT COUNT(*) FROM TABLE
     * @param numberProxy 自定义返回结果字节代理
     * @return 返回当前queryable链式
     * @param <TTNumberProxy> 返回结果类型代理
     * @param <TNumber> 返回结果类型
     */
   default  <TTNumberProxy extends ProxyEntity<TTNumberProxy, TNumber>, TNumber extends Number> ProxyQueryable<TTNumberProxy,TNumber> selectCount(TTNumberProxy numberProxy){
       ClientQueryable<TNumber> numberClientQueryable = getClientQueryable().selectCount(numberProxy.getEntityClass());
       return new EasyProxyQueryable<>(numberProxy,numberClientQueryable);
   }
}
