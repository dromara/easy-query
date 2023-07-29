package com.easy.query.core.expression.parser.core.base;

import com.easy.query.core.expression.builder.Selector;
import com.easy.query.core.expression.func.ColumnPropertyFunction;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.parser.core.EntitySQLTableOwner;
import com.easy.query.core.expression.parser.core.base.scec.ColumnConstExpressionContext;

/**
 * @author xuejiaming
 * @Description: 文件说明
 * @Date: 2023/2/6 23:20
 */
public interface ColumnSelector<T1> extends EntitySQLTableOwner<T1> {
    Selector getSelector();

    ColumnSelector<T1> column(String property);

    default ColumnSelector<T1> columnConst(String columnConst){
        return columnConst(columnConst,c->{});
    }
    ColumnSelector<T1> columnConst(String columnConst, SQLExpression1<ColumnConstExpressionContext> contextConsume);

    ColumnSelector<T1> columnFunc(ColumnPropertyFunction columnPropertyFunction);

    ColumnSelector<T1> columnIgnore(String property);

    ColumnSelector<T1> columnAll();

    default <T2> ColumnSelector<T2> then(ColumnSelector<T2> sub) {
        return sub;
    }
}
