package com.easy.query.core.expression.parser.core.base.scec;

import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.segment.scec.context.SQLAliasNativeExpressionContext;

/**
 * create time 2023/7/29 22:58
 * 文件说明
 *
 * @author xuejiaming
 */
public class SQLAliasNativePropertyExpressionContextImpl implements SQLAliasNativePropertyExpressionContext {

    private final TableAvailable table;
    protected final SQLAliasNativeExpressionContext sqlAliasNativeExpressionContext;
    public SQLAliasNativePropertyExpressionContextImpl(TableAvailable table,SQLAliasNativeExpressionContext sqlAliasNativeExpressionContext) {
        this.table = table;
        this.sqlAliasNativeExpressionContext=sqlAliasNativeExpressionContext;

    }

    @Override
    public SQLAliasNativePropertyExpressionContext expressionAlias(String property) {
        sqlAliasNativeExpressionContext.expressionAlias(property);
        return this;
    }

    @Override
    public SQLAliasNativePropertyExpressionContext setPropertyAlias(String property) {
        sqlAliasNativeExpressionContext.setPropertyAlias(property);
        return this;
    }

    @Override
    public SQLAliasNativePropertyExpressionContext expression(String property) {
        sqlAliasNativeExpressionContext.expression(this.table,property);
        return this;
    }

    @Override
    public SQLAliasNativePropertyExpressionContext expression(TableAvailable table, String property) {
        sqlAliasNativeExpressionContext.expression(table,property);
        return this;
    }

    @Override
    public <TEntity> SQLAliasNativePropertyExpressionContext expression(ClientQueryable<TEntity> subQuery) {
        sqlAliasNativeExpressionContext.expression(subQuery);
        return this;
    }

    @Override
    public SQLAliasNativePropertyExpressionContext value(Object val) {
        sqlAliasNativeExpressionContext.value(val);
        return this;
    }

    @Override
    public SQLAliasNativePropertyExpressionContext format(Object formatVal) {
        sqlAliasNativeExpressionContext.format(formatVal);
        return this;
    }

    @Override
    public SQLAliasNativePropertyExpressionContext setAlias(String alias) {
        sqlAliasNativeExpressionContext.setAlias(alias);
        return this;
    }
}
