package com.easy.query.core.expression.parser.core.base;

import com.easy.query.core.expression.builder.GroupSelector;
import com.easy.query.core.expression.func.ColumnPropertyFunction;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.parser.core.EntitySQLTableOwner;
import com.easy.query.core.expression.parser.core.base.scec.ColumnConstExpressionContext;

/**
 * create time 2023/4/30 21:49
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ColumnGroupSelector<T1> extends EntitySQLTableOwner<T1> {
    GroupSelector getGroupSelector();
    ColumnGroupSelector<T1> column(String property);
    default ColumnGroupSelector<T1> columnConst(String columnConst){
        return columnConst(columnConst,c->{});
    }
    ColumnGroupSelector<T1> columnConst(String columnConst, SQLExpression1<ColumnConstExpressionContext> contextConsume);

    ColumnGroupSelector<T1> columnFunc(ColumnPropertyFunction columnPropertyFunction);

    default <T2> ColumnGroupSelector<T2> then(ColumnGroupSelector<T2> sub) {
        return sub;
    }
}
