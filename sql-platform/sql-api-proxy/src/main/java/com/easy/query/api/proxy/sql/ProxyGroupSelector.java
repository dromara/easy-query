package com.easy.query.api.proxy.sql;

import com.easy.query.api.proxy.core.ProxyQuery;
import com.easy.query.api.proxy.core.base.SQLColumn;
import com.easy.query.core.expression.builder.GroupSelector;

/**
 * create time 2023/6/23 13:21
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ProxyGroupSelector {
    GroupSelector getGroupSelector();
    ProxyGroupSelector columns(SQLColumn<?>... column);
    ProxyGroupSelector column(SQLColumn<?> column);
   <TProxy extends ProxyQuery<TProxy,TEntity>,TEntity> ProxyGroupSelector columnConst(TProxy proxy, String columnConst);

    ProxyGroupSelector columnFunc(ProxyColumnPropertyFunction proxyColumnPropertyFunction);
}
