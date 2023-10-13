package com.easy.query.api4kt.sql.scec;

import com.easy.query.api4kt.select.KtQueryable;
import com.easy.query.api4kt.util.EasyKtLambdaUtil;
import com.easy.query.core.expression.parser.core.EntitySQLTableOwner;
import com.easy.query.core.expression.parser.core.base.scec.SQLNativePropertyExpressionContext;
import kotlin.reflect.KProperty1;

import java.util.Collection;

/**
 * create time 2023/7/29 23:41
 * 文件说明
 *
 * @author xuejiaming
 */
public class SQLNativeLambdaKtExpressionContextImpl<T1> implements SQLNativeLambdaKtExpressionContext<T1> {
    private final SQLNativePropertyExpressionContext columnConstExpressionContext;

    public SQLNativeLambdaKtExpressionContextImpl(SQLNativePropertyExpressionContext columnConstExpressionContext){

        this.columnConstExpressionContext = columnConstExpressionContext;
    }
    @Override
    public SQLNativeLambdaKtExpressionContext<T1> expression(KProperty1<? super T1, ?> property) {
        columnConstExpressionContext.expression(EasyKtLambdaUtil.getPropertyName(property));
        return this;
    }

    @Override
    public <TEntity> SQLNativeLambdaKtExpressionContext<T1> expression(KtQueryable<TEntity> subQuery) {
        columnConstExpressionContext.expression(subQuery.getClientQueryable());
        return this;
    }

    @Override
    public <T2> SQLNativeLambdaKtExpressionContext<T1> expression(EntitySQLTableOwner<T2> table, KProperty1<? super T2, ?> property) {
        columnConstExpressionContext.expression(table,EasyKtLambdaUtil.getPropertyName(property));
        return this;
    }

    @Override
    public SQLNativeLambdaKtExpressionContext<T1> value(Object val) {
        columnConstExpressionContext.value(val);
        return this;
    }

    @Override
    public <T> SQLNativeLambdaKtExpressionContext<T1> collection(Collection<T> values) {
        columnConstExpressionContext.collection(values);
        return this;
    }

    @Override
    public SQLNativeLambdaKtExpressionContext<T1> format(Object formatVal) {
        columnConstExpressionContext.format(formatVal);
        return this;
    }

    @Override
    public SQLNativeLambdaKtExpressionContext<T1> setAlias(String alias) {
        columnConstExpressionContext.setAlias(alias);
        return this;
    }

    @Override
    public SQLNativeLambdaKtExpressionContext<T1> keepStyle() {
        columnConstExpressionContext.keepStyle();
        return this;
    }

    @Override
    public SQLNativeLambdaKtExpressionContext<T1> messageFormat() {
        return null;
    }
}
