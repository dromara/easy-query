package com.easy.query.core.expression.parser.core.base.scec;

import com.easy.query.core.basic.api.select.ClientQueryable;
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
    private final SQLNativeExpressionContext sqlNativeExpressionContext;

    public SQLNativePropertyExpressionContextImpl(TableAvailable table, SQLNativeExpressionContext sqlNativeExpressionContext) {
        this.table = table;

        this.sqlNativeExpressionContext = sqlNativeExpressionContext;
    }

    @Override
    public SQLNativePropertyExpressionContext expression(String property) {
        sqlNativeExpressionContext.expression(table, property);
        return this;
    }

    @Override
    public <TEntity> SQLNativePropertyExpressionContext expression(ClientQueryable<TEntity> subQuery) {
        sqlNativeExpressionContext.expression(subQuery);
        return this;
    }

    @Override
    public SQLNativePropertyExpressionContext expression(TableAvailable table, String property) {
        sqlNativeExpressionContext.expression(table, property);
        return this;
    }

    @Override
    public SQLNativePropertyExpressionContext value(Object val) {
        sqlNativeExpressionContext.value(val);
        return this;
    }

    @Override
    public SQLNativePropertyExpressionContext constValue(Object constVal) {
        sqlNativeExpressionContext.constValue(constVal);
        return this;
    }

    @Override
    public SQLNativePropertyExpressionContext setAlias(String alias) {
        sqlNativeExpressionContext.setAlias(alias);
        return this;
    }
}
