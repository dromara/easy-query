package com.easy.query.core.expression.builder;

import com.easy.query.core.expression.func.ColumnPropertyFunction;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.segment.scec.context.SQLConstExpressionContext;

/**
 * create time 2023/6/23 13:11
 * 文件说明
 *
 * @author xuejiaming
 */
public interface GroupSelector {

    GroupSelector column(TableAvailable table,String property);
    default GroupSelector columnConst(String columnConst){
        return columnConst(columnConst,c->{});
    }
    GroupSelector columnConst(String columnConst, SQLExpression1<SQLConstExpressionContext> contextConsume);

    GroupSelector columnFunc(TableAvailable table,ColumnPropertyFunction columnPropertyFunction);
}
