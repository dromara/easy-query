package com.easy.query.core.expression.builder;

import com.easy.query.core.basic.jdbc.parameter.SQLParameter;
import com.easy.query.core.expression.lambda.SQLActionExpression2;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.segment.scec.context.SQLNativeExpressionContext;

/**
 * create time 2023/8/6 10:25
 * 文件说明
 *
 * @author xuejiaming
 */
public interface Configurer {
    Configurer column(TableAvailable table, String property, String sqlSegment, SQLActionExpression2<SQLNativeExpressionContext, SQLParameter> contextConsume);

}
