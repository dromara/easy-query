package com.easy.query.api.proxy.sql.scec;

import com.easy.query.core.proxy.SQLColumn;

/**
 * create time 2023/7/29 23:38
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ProxyConstExpressionContext {
    ProxyConstExpressionContext expression(SQLColumn<?> property);
    ProxyConstExpressionContext value(Object val);
    ProxyConstExpressionContext setAlias(String alias);
}
