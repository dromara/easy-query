package com.easy.query.core.basic.extension.conversion;

import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.parser.core.SQLTableOwner;
import com.easy.query.core.expression.parser.core.base.scec.SQLNativePropertyExpressionContext;
import com.easy.query.core.expression.segment.SQLSegment;
import com.easy.query.core.func.SQLFunction;

/**
 * create time 2023/8/8 15:42
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLPropertyConverter extends SQLTableOwner, SQLSegment {
    SQLFunction getSQLFunction();
    default void sqlNativeSegment(String sqlSegment){
        sqlNativeSegment(sqlSegment,c->{});
    }
    void sqlNativeSegment(String sqlSegment, SQLExpression1<SQLNativePropertyExpressionContext> contextConsume);
}
