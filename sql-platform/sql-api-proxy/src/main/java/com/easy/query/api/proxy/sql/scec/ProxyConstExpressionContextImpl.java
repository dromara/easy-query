package com.easy.query.api.proxy.sql.scec;

import com.easy.query.core.expression.segment.scec.context.SQLConstExpressionContext;
import com.easy.query.core.proxy.SQLColumn;

/**
 * create time 2023/7/29 23:41
 * 文件说明
 *
 * @author xuejiaming
 */
public class ProxyConstExpressionContextImpl implements ProxyConstExpressionContext {
    private final SQLConstExpressionContext sqlConstExpressionContext;

    public ProxyConstExpressionContextImpl(SQLConstExpressionContext sqlConstExpressionContext){

        this.sqlConstExpressionContext = sqlConstExpressionContext;
    }
    @Override
    public ProxyConstExpressionContext expression(SQLColumn<?> property) {
        sqlConstExpressionContext.expression(property.getTable(),property.value());
        return this;
    }

    @Override
    public ProxyConstExpressionContext value(Object val) {
        sqlConstExpressionContext.value(val);
        return this;
    }

    @Override
    public ProxyConstExpressionContext setAlias(String alias) {
        sqlConstExpressionContext.setAlias(alias);
        return this;
    }
}
