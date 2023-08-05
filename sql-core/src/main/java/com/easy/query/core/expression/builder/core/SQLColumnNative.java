package com.easy.query.core.expression.builder.core;

import com.easy.query.core.basic.jdbc.parameter.SQLParameter;
import com.easy.query.core.expression.lambda.SQLExpression2;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.segment.scec.context.SQLNativeExpressionContext;

/**
 * create time 2023/8/5 23:28
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLColumnNative<TChain> {
    TChain columnSQL(TableAvailable table, String property, String sqlSegment, SQLExpression2<SQLNativeExpressionContext, SQLParameter> contextConsume);
}
