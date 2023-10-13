package com.easy.query.api4j.sql.scec;

import com.easy.query.api4j.select.Queryable;
import com.easy.query.api4j.util.EasyLambdaUtil;
import com.easy.query.core.expression.lambda.Property;
import com.easy.query.core.expression.parser.core.EntitySQLTableOwner;
import com.easy.query.core.expression.parser.core.base.scec.SQLNativePropertyExpressionContext;

import java.util.Collection;

/**
 * create time 2023/7/29 23:41
 * 文件说明
 *
 * @author xuejiaming
 */
public class SQLNativeLambdaExpressionContextImpl<T1> implements SQLNativeLambdaExpressionContext<T1> {
    protected final SQLNativePropertyExpressionContext sqlNativePropertyExpressionContext;

    public SQLNativeLambdaExpressionContextImpl(SQLNativePropertyExpressionContext columnConstExpressionContext){

        this.sqlNativePropertyExpressionContext = columnConstExpressionContext;
    }
    @Override
    public SQLNativeLambdaExpressionContext<T1> expression(Property<T1, ?> property) {
        sqlNativePropertyExpressionContext.expression(EasyLambdaUtil.getPropertyName(property));
        return this;
    }

    @Override
    public <TEntity> SQLNativeLambdaExpressionContext<T1> expression(Queryable<TEntity> subQuery) {
        sqlNativePropertyExpressionContext.expression(subQuery.getClientQueryable());
        return this;
    }

    @Override
    public <T2> SQLNativeLambdaExpressionContext<T1> expression(EntitySQLTableOwner<T2> table, Property<T2, ?> property) {
        sqlNativePropertyExpressionContext.expression(table,EasyLambdaUtil.getPropertyName(property));
        return this;
    }

    @Override
    public SQLNativeLambdaExpressionContext<T1> value(Object val) {
        sqlNativePropertyExpressionContext.value(val);
        return this;
    }

    @Override
    public <T> SQLNativeLambdaExpressionContext<T1> collection(Collection<T> values) {
        sqlNativePropertyExpressionContext.collection(values);
        return this;
    }

    @Override
    public SQLNativeLambdaExpressionContext<T1> format(Object formatVal) {
        sqlNativePropertyExpressionContext.format(formatVal);
        return this;
    }

    @Override
    public SQLNativeLambdaExpressionContext<T1> setAlias(String alias) {
        sqlNativePropertyExpressionContext.setAlias(alias);
        return this;
    }

    @Override
    public SQLNativeLambdaExpressionContext<T1> keepStyle() {
        sqlNativePropertyExpressionContext.keepStyle();
        return this;
    }

    @Override
    public SQLNativeLambdaExpressionContext<T1> messageFormat() {
        sqlNativePropertyExpressionContext.messageFormat();
        return this;
    }
}
