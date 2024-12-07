package com.easy.query.core.common.reverse;

import com.easy.query.core.expression.lambda.SQLActionExpression;

/**
 * create time 2024/12/7 10:15
 * 文件说明
 *
 * @author xuejiaming
 */
public class DefaultReverseEach implements ReverseEach{
    private final SQLActionExpression sqlActionExpression;

    public DefaultReverseEach(SQLActionExpression sqlActionExpression){
        this.sqlActionExpression = sqlActionExpression;
    }
    @Override
    public void invoke() {
        sqlActionExpression.apply();
    }
}
