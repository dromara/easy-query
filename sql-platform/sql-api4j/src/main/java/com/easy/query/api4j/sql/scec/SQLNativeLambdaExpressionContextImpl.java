package com.easy.query.api4j.sql.scec;

import com.easy.query.api4j.select.Queryable;
import com.easy.query.api4j.util.EasyLambdaUtil;
import com.easy.query.core.expression.lambda.Property;
import com.easy.query.core.expression.parser.core.EntitySQLTableOwner;
import com.easy.query.core.expression.parser.core.base.scec.SQLNativePropertyExpressionContext;

/**
 * create time 2023/7/29 23:41
 * 文件说明
 *
 * @author xuejiaming
 */
public class SQLNativeLambdaExpressionContextImpl<T1> implements SQLNativeLambdaExpressionContext<T1> {
    private final SQLNativePropertyExpressionContext columnConstExpressionContext;

    public SQLNativeLambdaExpressionContextImpl(SQLNativePropertyExpressionContext columnConstExpressionContext){

        this.columnConstExpressionContext = columnConstExpressionContext;
    }
    @Override
    public SQLNativeLambdaExpressionContext<T1> expression(Property<T1, ?> property) {
        columnConstExpressionContext.expression(EasyLambdaUtil.getPropertyName(property));
        return this;
    }

    @Override
    public <TEntity> SQLNativeLambdaExpressionContext<T1> expression(Queryable<TEntity> subQuery) {
        columnConstExpressionContext.expression(subQuery.getClientQueryable());
        return this;
    }

    @Override
    public <T2> SQLNativeLambdaExpressionContext<T1> expression(EntitySQLTableOwner<T2> table, Property<T2, ?> property) {
        columnConstExpressionContext.expression(table.getTable(),EasyLambdaUtil.getPropertyName(property));
        return this;
    }

    @Override
    public SQLNativeLambdaExpressionContext<T1> value(Object val) {
        columnConstExpressionContext.value(val);
        return this;
    }

    @Override
    public SQLNativeLambdaExpressionContext<T1> constValue(Object constVal) {
        columnConstExpressionContext.constValue(constVal);
        return this;
    }

    @Override
    public SQLNativeLambdaExpressionContext<T1> setAlias(String alias) {
        columnConstExpressionContext.setAlias(alias);
        return this;
    }
}
