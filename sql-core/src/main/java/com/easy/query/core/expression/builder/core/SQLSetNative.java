package com.easy.query.core.expression.builder.core;

import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.segment.scec.context.SQLNativeExpressionContext;

/**
 * create time 2023/12/8 10:15
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLSetNative<TChain> extends SQLNative<TChain> {
    TChain sqlNativeSegment(TableAvailable table,String property, String sqlSegment, SQLExpression1<SQLNativeExpressionContext> contextConsume);
}
