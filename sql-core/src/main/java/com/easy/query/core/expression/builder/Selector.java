package com.easy.query.core.expression.builder;

import com.easy.query.core.expression.func.ColumnPropertyFunction;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.segment.scec.context.SQLConstExpressionContext;

/**
 * create time 2023/6/22 20:33
 * 文件说明
 *
 * @author xuejiaming
 */
public interface Selector {

    Selector column(TableAvailable table, String property);
    default Selector columnConst(String columnConst){
        return columnConst(columnConst,c->{});
    }
    Selector columnConst(String columnConst, SQLExpression1<SQLConstExpressionContext> contextConsume);

    Selector columnFunc(TableAvailable table, ColumnPropertyFunction columnPropertyFunction);

    Selector columnIgnore(TableAvailable table, String property);

    Selector columnAll(TableAvailable table);
}
