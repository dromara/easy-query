package com.easy.query.api.proxy.entity.select.extension.queryable;

import com.easy.query.api.proxy.entity.select.EntityQueryable;
import com.easy.query.core.func.SQLFunc;
import com.easy.query.core.proxy.ProxyEntity;

/**
 * create time 2023/8/17 11:14
 * 文件说明
 *
 * @author xuejiaming
 */
public interface EntityQueryableAvailable<T1Proxy extends ProxyEntity<T1Proxy, T1>, T1> {
    EntityQueryable<T1Proxy, T1> getQueryable();
    default SQLFunc getSQLFunc(){
        return getQueryable().getRuntimeContext().fx();
    }
}
