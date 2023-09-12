package com.easy.query.api.proxy.sql;

import com.easy.query.core.expression.func.ColumnPropertyFunction;
import com.easy.query.core.proxy.ProxyEntity;
import com.easy.query.core.proxy.SQLColumn;

/**
 * create time 2023/6/22 21:26
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ProxyColumnPropertyFunction {
    ColumnPropertyFunction getColumnPropertyFunction();
    <TProxy extends ProxyEntity<TProxy,T>,T,TProperty> SQLColumn<TProxy,TProperty> getColumn();
}
