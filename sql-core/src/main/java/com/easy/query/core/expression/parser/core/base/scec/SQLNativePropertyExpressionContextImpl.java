package com.easy.query.core.expression.parser.core.base.scec;

import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.segment.scec.context.SQLNativeExpressionContext;

/**
 * create time 2023/7/29 22:58
 * 文件说明
 *
 * @author xuejiaming
 */
public class SQLNativePropertyExpressionContextImpl implements SQLNativePropertyExpressionContext {
    private final TableAvailable table;
    private final SQLNativeExpressionContext sqlConstExpressionContext;

    public SQLNativePropertyExpressionContextImpl(TableAvailable table, SQLNativeExpressionContext sqlConstExpressionContext) {
        this.table = table;

        this.sqlConstExpressionContext = sqlConstExpressionContext;
    }

    @Override
    public SQLNativePropertyExpressionContext expression(String property) {
        sqlConstExpressionContext.expression(table, property);
        return this;
    }

    @Override
    public SQLNativePropertyExpressionContext expression(TableAvailable table, String property) {
        sqlConstExpressionContext.expression(table, property);
        return this;
    }

    @Override
    public SQLNativePropertyExpressionContext value(Object val) {
        sqlConstExpressionContext.value(val);
        return this;
    }

    @Override
    public SQLNativePropertyExpressionContext constValue(Object constVal) {
        sqlConstExpressionContext.constValue(constVal);
        return this;
    }

    @Override
    public SQLNativePropertyExpressionContext setAlias(String alias) {
        sqlConstExpressionContext.setAlias(alias);
        return this;
    }
}
