package com.easy.query.core.func;

import com.easy.query.core.context.QueryRuntimeContext;
import com.easy.query.core.expression.lambda.SQLActionExpression;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.segment.SQLSegment;
import com.easy.query.core.expression.segment.scec.context.SQLNativeExpressionContext;
import com.easy.query.core.expression.sql.builder.ExpressionContext;

/**
 * create time 2023/10/18 11:17
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLFunctionTranslate {
    SQLSegment toSQLSegment(ExpressionContext expressionContext, TableAvailable defTable, QueryRuntimeContext runtimeContext,String alias);
}
