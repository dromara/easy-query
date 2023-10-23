package com.easy.query.api4kt.sql.scec;

import com.easy.query.api4kt.select.KtQueryable;
import com.easy.query.api4kt.util.EasyKtLambdaUtil;
import com.easy.query.core.expression.parser.core.EntitySQLTableOwner;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.parser.core.base.scec.SQLAliasNativePropertyExpressionContext;
import kotlin.reflect.KProperty1;

import java.util.Collection;

/**
 * create time 2023/7/29 23:41
 * 文件说明
 *
 * @author xuejiaming
 */
public class SQLAliasNativeLambdaKtExpressionContextImpl<T1,TR> implements SQLAliasNativeLambdaKtExpressionContext<T1,TR> {
    private final SQLAliasNativePropertyExpressionContext sqlAliasNativePropertyExpressionContext;

    public SQLAliasNativeLambdaKtExpressionContextImpl(SQLAliasNativePropertyExpressionContext sqlAliasNativePropertyExpressionContext){

        this.sqlAliasNativePropertyExpressionContext = sqlAliasNativePropertyExpressionContext;
    }
    @Override
    public SQLAliasNativeLambdaKtExpressionContext<T1,TR> expressionAlias(KProperty1<? super TR, ?> property) {
        sqlAliasNativePropertyExpressionContext.expressionAlias(EasyKtLambdaUtil.getPropertyName(property));
        return this;
    }

    @Override
    public SQLAliasNativeLambdaKtExpressionContext<T1, TR> setPropertyAlias(KProperty1<? super TR, ?> property) {
        sqlAliasNativePropertyExpressionContext.setPropertyAlias(EasyKtLambdaUtil.getPropertyName(property));
        return this;
    }

    @Override
    public SQLAliasNativeLambdaKtExpressionContext<T1,TR> expression(KProperty1<? super T1, ?> property) {
        sqlAliasNativePropertyExpressionContext.expression(EasyKtLambdaUtil.getPropertyName(property));
        return this;
    }

    @Override
    public <TEntity> SQLAliasNativeLambdaKtExpressionContext<T1,TR> expression(KtQueryable<TEntity> subQuery) {
        sqlAliasNativePropertyExpressionContext.expression(subQuery.getClientQueryable());
        return this;
    }

    @Override
    public <T2> SQLAliasNativeLambdaKtExpressionContext<T1,TR> expression(EntitySQLTableOwner<T2> table, KProperty1<? super T2, ?> property) {
        sqlAliasNativePropertyExpressionContext.expression(table,EasyKtLambdaUtil.getPropertyName(property));
        return this;
    }

    @Override
    public SQLAliasNativeLambdaKtExpressionContext<T1, TR> columnName(String columnName) {
        sqlAliasNativePropertyExpressionContext.columnName(columnName);
        return this;
    }

    @Override
    public SQLAliasNativeLambdaKtExpressionContext<T1, TR> columnName(TableAvailable table, String columnName) {
        sqlAliasNativePropertyExpressionContext.columnName(table,columnName);
        return this;
    }

    @Override
    public SQLAliasNativeLambdaKtExpressionContext<T1,TR> value(Object val) {
        sqlAliasNativePropertyExpressionContext.value(val);
        return this;
    }

    @Override
    public <T> SQLAliasNativeLambdaKtExpressionContext<T1, TR> collection(Collection<T> values) {
        sqlAliasNativePropertyExpressionContext.collection(values);
        return this;
    }

    @Override
    public SQLAliasNativeLambdaKtExpressionContext<T1,TR> format(Object formatVal) {
        sqlAliasNativePropertyExpressionContext.format(formatVal);
        return this;
    }

    @Override
    public SQLAliasNativeLambdaKtExpressionContext<T1,TR> setAlias(String alias) {
        sqlAliasNativePropertyExpressionContext.setAlias(alias);
        return this;
    }

    @Override
    public SQLAliasNativeLambdaKtExpressionContext<T1, TR> keepStyle() {
        sqlAliasNativePropertyExpressionContext.keepStyle();
        return this;
    }

    @Override
    public SQLAliasNativeLambdaKtExpressionContext<T1, TR> messageFormat() {
        sqlAliasNativePropertyExpressionContext.messageFormat();
        return this;
    }
}
