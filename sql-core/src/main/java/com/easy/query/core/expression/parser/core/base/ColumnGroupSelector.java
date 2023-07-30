package com.easy.query.core.expression.parser.core.base;

import com.easy.query.core.expression.builder.GroupSelector;
import com.easy.query.core.expression.func.ColumnPropertyFunction;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.parser.core.EntitySQLTableOwner;
import com.easy.query.core.expression.parser.core.base.scec.NativeSQLPropertyExpressionContext;

/**
 * create time 2023/4/30 21:49
 * 文件说明
 *
 * @author xuejiaming
 */
public interface ColumnGroupSelector<T1> extends EntitySQLTableOwner<T1> {
    GroupSelector getGroupSelector();
    ColumnGroupSelector<T1> column(String property);

    /**
     * 请使用 sqlNativeSegment
     * @param columnConst
     * @return
     */
    @Deprecated
    default ColumnGroupSelector<T1> columnConst(String columnConst){
        return sqlNativeSegment(columnConst,c->{});
    }

    default ColumnGroupSelector<T1> sqlNativeSegment(String sqlSegment){
        return sqlNativeSegment(sqlSegment, c->{});
    }
    ColumnGroupSelector<T1> sqlNativeSegment(String sqlSegment, SQLExpression1<NativeSQLPropertyExpressionContext> contextConsume);

    ColumnGroupSelector<T1> columnFunc(ColumnPropertyFunction columnPropertyFunction);

    default <T2> ColumnGroupSelector<T2> then(ColumnGroupSelector<T2> sub) {
        return sub;
    }
}
