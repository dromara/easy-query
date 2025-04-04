package com.easy.query.core.expression;

import com.easy.query.core.basic.api.select.ClientQueryable;
import com.easy.query.core.expression.lambda.SQLFuncExpression1;

/**
 * create time 2025/3/13 13:02
 * 文件说明
 *
 * @author xuejiaming
 */
public class ManyConfiguration {
    public static final ManyConfiguration EMPTY = new ManyConfiguration(x->x);
    private final SQLFuncExpression1<ClientQueryable<?>, ClientQueryable<?>> configureExpression;

    public ManyConfiguration(SQLFuncExpression1<ClientQueryable<?>, ClientQueryable<?>> configureExpression) {
        this.configureExpression = configureExpression;
    }

    public SQLFuncExpression1<ClientQueryable<?>, ClientQueryable<?>> getConfigureExpression() {
        return configureExpression;
    }
}
