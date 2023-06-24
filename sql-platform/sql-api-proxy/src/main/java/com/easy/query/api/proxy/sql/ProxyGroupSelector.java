package com.easy.query.api.proxy.sql;

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

    default ProxyGroupSelector columnConst(String columnConst) {
        getGroupSelector().columnConst(columnConst);
        return this;
    }

    default ProxyGroupSelector columnFunc(ProxyColumnPropertyFunction proxyColumnPropertyFunction) {
        getGroupSelector().columnFunc(proxyColumnPropertyFunction.getColumn().getTable(), proxyColumnPropertyFunction.getColumnPropertyFunction());
        return this;
    }
}
