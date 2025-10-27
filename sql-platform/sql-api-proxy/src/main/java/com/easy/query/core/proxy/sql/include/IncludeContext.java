package com.easy.query.core.proxy.sql.include;

import com.easy.query.core.proxy.AbstractProxyEntity;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.columns.SQLQueryable;

/**
 * create time 2025/10/25 15:50
 * 文件说明
 *
 * @author xuejiaming
 */
public interface IncludeContext {
     <TPropertyProxy extends AbstractProxyEntity<TPropertyProxy, TProperty>, TProperty> NavigateInclude<TPropertyProxy, TProperty> query(SQLQueryable<TPropertyProxy, TProperty> includeMany);
     <TPropertyProxy extends AbstractProxyEntity<TPropertyProxy, TProperty>, TProperty> NavigateInclude<TPropertyProxy, TProperty> query(TPropertyProxy includeOne) ;
}
