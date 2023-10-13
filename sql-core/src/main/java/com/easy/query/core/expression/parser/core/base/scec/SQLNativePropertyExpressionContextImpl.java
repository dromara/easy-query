package com.easy.query.core.expression.parser.core.base.scec;

import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.expression.parser.core.SQLTableOwner;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.parser.core.base.scec.core.SQLNativeChainExpressionContext;
import com.easy.query.core.expression.parser.core.base.scec.core.SQLNativeChainExpressionContextImpl;
import com.easy.query.core.expression.segment.scec.context.SQLNativeExpressionContext;

import java.util.Collection;

/**
 * create time 2023/7/29 22:58
 * 文件说明
 *
 * @author xuejiaming
 */
public class SQLNativePropertyExpressionContextImpl implements SQLNativePropertyExpressionContext {

    private final SQLNativeChainExpressionContext sqlNativeChainExpressionContext;
    public SQLNativePropertyExpressionContextImpl(TableAvailable table, SQLNativeExpressionContext sqlAliasNativeExpressionContext) {
        this(new SQLNativeChainExpressionContextImpl(table,sqlAliasNativeExpressionContext));
    }
    public SQLNativePropertyExpressionContextImpl(SQLNativeChainExpressionContext sqlNativeChainExpressionContext) {

        this.sqlNativeChainExpressionContext = sqlNativeChainExpressionContext;
    }

    @Override
    public SQLNativeChainExpressionContext getSQLNativeChainExpressionContext() {
        return sqlNativeChainExpressionContext;
    }

    @Override
    public SQLNativePropertyExpressionContext expression(String property) {
        sqlNativeChainExpressionContext.expression(property);
        return this;
    }

    @Override
    public <TEntity> SQLNativePropertyExpressionContext expression(ClientQueryable<TEntity> subQuery) {
        sqlNativeChainExpressionContext.expression(subQuery);
        return this;
    }

    @Override
    public SQLNativePropertyExpressionContext expression(SQLTableOwner sqlTableOwner, String property) {
        sqlNativeChainExpressionContext.expression(sqlTableOwner.getTable(), property);
        return this;
    }

    @Override
    public SQLNativePropertyExpressionContext expression(TableAvailable table, String property) {
        sqlNativeChainExpressionContext.expression(table, property);
        return this;
    }

    @Override
    public SQLNativePropertyExpressionContext value(Object val) {
        sqlNativeChainExpressionContext.value(val);
        return this;
    }

    @Override
    public <T> SQLNativePropertyExpressionContext collection(Collection<T> values) {
        sqlNativeChainExpressionContext.collection(values);
        return this;
    }

    @Override
    public SQLNativePropertyExpressionContext format(Object formatVal) {
        sqlNativeChainExpressionContext.format(formatVal);
        return this;
    }

    @Override
    public SQLNativePropertyExpressionContext setAlias(String alias) {
        sqlNativeChainExpressionContext.setAlias(alias);
        return this;
    }

    @Override
    public SQLNativePropertyExpressionContext keepStyle() {
        sqlNativeChainExpressionContext.keepStyle();
        return this;
    }

    @Override
    public SQLNativePropertyExpressionContext messageFormat() {
        sqlNativeChainExpressionContext.messageFormat();
        return this;
    }
}
