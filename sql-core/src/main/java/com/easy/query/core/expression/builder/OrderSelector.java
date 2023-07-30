package com.easy.query.core.expression.builder;

import com.easy.query.core.expression.func.ColumnPropertyFunction;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.segment.scec.context.SQLNativeExpressionContext;

/**
 * create time 2023/6/23 14:36
 * 文件说明
 *
 * @author xuejiaming
 */
public interface OrderSelector {
    void setAsc(boolean asc);
    OrderSelector column(TableAvailable table, String property);
    default OrderSelector sqlNativeSegment(String columnConst){
        return sqlNativeSegment(columnConst, c->{});
    }
    OrderSelector sqlNativeSegment(String columnConst, SQLExpression1<SQLNativeExpressionContext> contextConsume);

    OrderSelector columnFunc(TableAvailable table,ColumnPropertyFunction columnPropertyFunction);
//    OrderSelector columnConst(String columnConst);
}
