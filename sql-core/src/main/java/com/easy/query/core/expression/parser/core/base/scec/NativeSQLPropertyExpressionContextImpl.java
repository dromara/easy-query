package com.easy.query.core.expression.parser.core.base.scec;

import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.segment.scec.context.SQLNativeExpressionContext;

/**
 * create time 2023/7/29 22:58
 * 文件说明
 *
 * @author xuejiaming
 */
public class NativeSQLPropertyExpressionContextImpl implements NativeSQLPropertyExpressionContext {
    private final TableAvailable table;
    private final SQLNativeExpressionContext sqlConstExpressionContext;

    public NativeSQLPropertyExpressionContextImpl(TableAvailable table, SQLNativeExpressionContext sqlConstExpressionContext) {
        this.table = table;

        this.sqlConstExpressionContext = sqlConstExpressionContext;
    }

    @Override
    public NativeSQLPropertyExpressionContext expression(String property) {
        sqlConstExpressionContext.expression(table, property);
        return this;
    }

    @Override
    public NativeSQLPropertyExpressionContext expression(TableAvailable table, String property) {
        sqlConstExpressionContext.expression(table, property);
        return this;
    }

    @Override
    public NativeSQLPropertyExpressionContext value(Object val) {
        sqlConstExpressionContext.value(val);
        return this;
    }

    @Override
    public NativeSQLPropertyExpressionContext setAlias(String alias) {
        sqlConstExpressionContext.setAlias(alias);
        return this;
    }
}
