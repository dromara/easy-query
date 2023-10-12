package com.easy.query.core.expression.parser.core.base.scec;

import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.expression.parser.core.SQLTableOwner;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.parser.core.base.scec.core.SQLNativeChainExpressionContext;
import com.easy.query.core.expression.parser.core.base.scec.core.SQLNativeChainExpressionContextImpl;
import com.easy.query.core.expression.segment.scec.context.SQLNativeExpressionContext;

/**
 * create time 2023/7/29 22:58
 * 文件说明
 *
 * @author xuejiaming
 */
public class SQLAliasNativePropertyExpressionContextImpl implements SQLAliasNativePropertyExpressionContext {

    private final SQLNativeChainExpressionContext sqlNativeChainExpressionContext;
    public SQLAliasNativePropertyExpressionContextImpl(TableAvailable table, SQLNativeExpressionContext sqlAliasNativeExpressionContext) {
        this(new SQLNativeChainExpressionContextImpl(table,sqlAliasNativeExpressionContext));
    }
    public SQLAliasNativePropertyExpressionContextImpl(SQLNativeChainExpressionContext sqlNativeChainExpressionContext) {

        this.sqlNativeChainExpressionContext = sqlNativeChainExpressionContext;
    }

    @Override
    public SQLAliasNativePropertyExpressionContext expressionAlias(String property) {
        sqlNativeChainExpressionContext.expressionAlias(property);
        return this;
    }

    @Override
    public SQLAliasNativePropertyExpressionContext setPropertyAlias(String property) {
        sqlNativeChainExpressionContext.setPropertyAlias(property);
        return this;
    }

    @Override
    public SQLNativeChainExpressionContext getSQLNativeChainExpressionContext() {
        return sqlNativeChainExpressionContext;
    }

    @Override
    public SQLAliasNativePropertyExpressionContext expression(String property) {
        sqlNativeChainExpressionContext.expression(property);
        return this;
    }

    @Override
    public SQLAliasNativePropertyExpressionContext expression(SQLTableOwner sqlTableOwner, String property) {
        sqlNativeChainExpressionContext.expression(sqlTableOwner.getTable(),property);
        return this;
    }

    @Override
    public SQLAliasNativePropertyExpressionContext expression(TableAvailable table, String property) {
        sqlNativeChainExpressionContext.expression(table,property);
        return this;
    }

    @Override
    public <TEntity> SQLAliasNativePropertyExpressionContext expression(ClientQueryable<TEntity> subQuery) {
        sqlNativeChainExpressionContext.expression(subQuery);
        return this;
    }

    @Override
    public SQLAliasNativePropertyExpressionContext value(Object val) {
        sqlNativeChainExpressionContext.value(val);
        return this;
    }

    @Override
    public SQLAliasNativePropertyExpressionContext format(Object formatVal) {
        sqlNativeChainExpressionContext.format(formatVal);
        return this;
    }

    @Override
    public SQLAliasNativePropertyExpressionContext setAlias(String alias) {
        sqlNativeChainExpressionContext.setAlias(alias);
        return this;
    }

    @Override
    public SQLAliasNativePropertyExpressionContext keepStyle() {
        sqlNativeChainExpressionContext.keepStyle();
        return this;
    }

    @Override
    public SQLAliasNativePropertyExpressionContext messageFormat() {
        sqlNativeChainExpressionContext.messageFormat();
        return this;
    }
}
