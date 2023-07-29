package com.easy.query.api4kt.sql.scec;

import com.easy.query.api4kt.util.EasyKtLambdaUtil;
import com.easy.query.core.expression.parser.core.EntitySQLTableOwner;
import com.easy.query.core.expression.parser.core.base.scec.ColumnConstExpressionContext;
import kotlin.reflect.KProperty1;

/**
 * create time 2023/7/29 23:41
 * 文件说明
 *
 * @author xuejiaming
 */
public class SQLKtColumnConstExpressionContextImpl<T1> implements SQLKtColumnConstExpressionContext<T1> {
    private final ColumnConstExpressionContext columnConstExpressionContext;

    public SQLKtColumnConstExpressionContextImpl(ColumnConstExpressionContext columnConstExpressionContext){

        this.columnConstExpressionContext = columnConstExpressionContext;
    }
    @Override
    public SQLKtColumnConstExpressionContext<T1> expression(KProperty1<T1, ?> property) {
        columnConstExpressionContext.expression(EasyKtLambdaUtil.getPropertyName(property));
        return this;
    }

    @Override
    public <T2> SQLKtColumnConstExpressionContext<T1> expression(EntitySQLTableOwner<T2> table, KProperty1<T2, ?> property) {
        columnConstExpressionContext.expression(table.getTable(),EasyKtLambdaUtil.getPropertyName(property));
        return this;
    }

    @Override
    public SQLKtColumnConstExpressionContext<T1> value(Object val) {
        columnConstExpressionContext.value(val);
        return this;
    }

    @Override
    public SQLKtColumnConstExpressionContext<T1> setAlias(String alias) {
        columnConstExpressionContext.setAlias(alias);
        return this;
    }
}
