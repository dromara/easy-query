package com.easy.query.core.expression.parser.core.base;

import com.easy.query.core.basic.jdbc.parameter.SQLParameter;
import com.easy.query.core.expression.builder.Configurer;
import com.easy.query.core.expression.lambda.SQLExpression2;
import com.easy.query.core.expression.parser.core.EntitySQLTableOwner;
import com.easy.query.core.expression.parser.core.base.scec.SQLNativePropertyExpressionContext;
import com.easy.query.core.expression.parser.core.base.scec.SQLNativePropertyExpressionContextImpl;

/**
 * create time 2023/8/7 08:38
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ColumnConfigurer<T1> extends EntitySQLTableOwner<T1> {
    Configurer getConfigurer();
    default ColumnConfigurer<T1> column(String property, String sqlSegment, SQLExpression2<SQLNativePropertyExpressionContext, SQLParameter> contextConsume){
        getConfigurer().column(getTable(),property,sqlSegment,(context,sqlParameter)->{
            contextConsume.apply(new SQLNativePropertyExpressionContextImpl(getTable(),context),sqlParameter);
        });
        return this;
    }
}
