package com.easy.query.core.proxy.grouping;

import com.easy.query.core.proxy.ProxyEntityAvailable;
import com.easy.query.core.proxy.grouping.proxy.GroupingProxy;
import com.easy.query.core.util.EasyObjectUtil;

/**
 * create time 2023/12/22 21:44
 * 文件说明
 *
 * @author xuejiaming
 */
public class Grouping<TKey, TSourceProxy> implements ProxyEntityAvailable<Grouping<TKey,TSourceProxy> , GroupingProxy<TKey,TSourceProxy>> {
    private TKey keys;

    public TKey getKeys() {
        return keys;
    }

    public void setKeys(TKey keys) {
        this.keys = keys;
    }

    @Override
    public Class<GroupingProxy<TKey,TSourceProxy>> proxyTableClass() {
        return EasyObjectUtil.typeCastNullable(GroupingProxy.class);
    }
}
