package com.easy.query.api4j.sql.scec;

import com.easy.query.api4j.util.EasyLambdaUtil;
import com.easy.query.core.expression.lambda.Property;
import com.easy.query.core.expression.parser.core.EntitySQLTableOwner;
import com.easy.query.core.expression.parser.core.base.scec.ColumnConstExpressionContext;

/**
 * create time 2023/7/29 23:41
 * 文件说明
 *
 * @author xuejiaming
 */
public class SQLColumnConstExpressionContextImpl<T1> implements SQLColumnConstExpressionContext<T1> {
    private final ColumnConstExpressionContext columnConstExpressionContext;

    public SQLColumnConstExpressionContextImpl(ColumnConstExpressionContext columnConstExpressionContext){

        this.columnConstExpressionContext = columnConstExpressionContext;
    }
    @Override
    public SQLColumnConstExpressionContext<T1> expression(Property<T1, ?> property) {
        columnConstExpressionContext.expression(EasyLambdaUtil.getPropertyName(property));
        return this;
    }

    @Override
    public <T2> SQLColumnConstExpressionContext<T1> expression(EntitySQLTableOwner<T2> table, Property<T2, ?> property) {
        columnConstExpressionContext.expression(table.getTable(),EasyLambdaUtil.getPropertyName(property));
        return this;
    }

    @Override
    public SQLColumnConstExpressionContext<T1> value(Object val) {
        columnConstExpressionContext.value(val);
        return this;
    }

    @Override
    public SQLColumnConstExpressionContext<T1> setAlias(String alias) {
        columnConstExpressionContext.setAlias(alias);
        return this;
    }
}
