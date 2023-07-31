package com.easy.query.api.proxy.sql;

import com.easy.query.api.proxy.sql.core.SQLProxyNative;
import com.easy.query.api.proxy.sql.scec.SQLNativeProxyExpressionContext;
import com.easy.query.api.proxy.sql.scec.SQLNativeProxyExpressionContextImpl;
import com.easy.query.core.expression.builder.GroupSelector;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.proxy.SQLColumn;

/**
 * create time 2023/6/23 13:21
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ProxyGroupSelector extends SQLProxyNative<ProxyGroupSelector> {
    GroupSelector getGroupSelector();

    default ProxyGroupSelector columns(SQLColumn<?>... columns) {
        if (columns != null) {
            for (SQLColumn<?> sqlColumn : columns) {
                column(sqlColumn);
            }
        }
        return this;
    }

    default ProxyGroupSelector column(SQLColumn<?> column) {
        getGroupSelector().column(column.getTable(), column.value());
        return this;
    }

    default ProxyGroupSelector columnFunc(ProxyColumnPropertyFunction proxyColumnPropertyFunction) {
        getGroupSelector().columnFunc(proxyColumnPropertyFunction.getColumn().getTable(), proxyColumnPropertyFunction.getColumnPropertyFunction());
        return this;
    }
}
