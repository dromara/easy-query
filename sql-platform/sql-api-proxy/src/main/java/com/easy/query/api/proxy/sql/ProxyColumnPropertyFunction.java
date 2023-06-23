package com.easy.query.api.proxy.sql;

import com.easy.query.api.proxy.core.base.SQLColumn;
import com.easy.query.core.expression.func.ColumnFunction;

/**
 * create time 2023/6/22 21:26
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ProxyColumnPropertyFunction {
    ColumnFunction getColumnFunction();
    SQLColumn<?> getColumn();
}
