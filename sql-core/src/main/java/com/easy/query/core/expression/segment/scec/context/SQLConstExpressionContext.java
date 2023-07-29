package com.easy.query.core.expression.segment.scec.context;

import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.segment.scec.expression.ConstParamExpression;

import java.util.List;

/**
 * create time 2023/7/29 21:44
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLConstExpressionContext {
    SQLConstExpressionContext expression(TableAvailable table, String property);
    SQLConstExpressionContext value(Object val);

     List<ConstParamExpression> getExpressions();


     String getAlias();

    SQLConstExpressionContext setAlias(String alias);
}
