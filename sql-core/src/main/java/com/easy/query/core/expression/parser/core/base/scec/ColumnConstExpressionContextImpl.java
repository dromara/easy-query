package com.easy.query.core.expression.parser.core.base.scec;

import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.segment.scec.context.SQLConstExpressionContext;

/**
 * create time 2023/7/29 22:58
 * 文件说明
 *
 * @author xuejiaming
 */
public class ColumnConstExpressionContextImpl implements ColumnConstExpressionContext {
    private final TableAvailable table;
    private final SQLConstExpressionContext sqlConstExpressionContext;

    public ColumnConstExpressionContextImpl(TableAvailable table, SQLConstExpressionContext sqlConstExpressionContext) {
        this.table = table;

        this.sqlConstExpressionContext = sqlConstExpressionContext;
    }

    @Override
    public ColumnConstExpressionContext expression(String property) {
        sqlConstExpressionContext.expression(table, property);
        return this;
    }

    @Override
    public ColumnConstExpressionContext expression(TableAvailable table, String property) {
        sqlConstExpressionContext.expression(table, property);
        return this;
    }

    @Override
    public ColumnConstExpressionContext value(Object val) {
        sqlConstExpressionContext.value(val);
        return this;
    }

    @Override
    public ColumnConstExpressionContext setAlias(String alias) {
        sqlConstExpressionContext.setAlias(alias);
        return this;
    }
}
