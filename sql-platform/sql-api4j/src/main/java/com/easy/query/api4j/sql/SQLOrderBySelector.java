package com.easy.query.api4j.sql;

import com.easy.query.api4j.sql.core.SQLLambdaNative;
import com.easy.query.api4j.sql.scec.SQLNativeLambdaExpressionContext;
import com.easy.query.api4j.sql.scec.SQLNativeLambdaExpressionContextImpl;
import com.easy.query.api4j.util.EasyLambdaUtil;
import com.easy.query.core.expression.func.ColumnPropertyFunction;
import com.easy.query.core.expression.lambda.Property;
import com.easy.query.core.expression.lambda.SQLExpression1;
import com.easy.query.core.expression.parser.core.EntitySQLTableOwner;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.parser.core.base.ColumnOrderSelector;

/**
 * create time 2023/6/16 21:54
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLOrderBySelector<T1> extends EntitySQLTableOwner<T1>, SQLLambdaNative<T1,SQLOrderBySelector<T1>> {
    ColumnOrderSelector<T1> getOrderBySelector();

    default TableAvailable getTable() {
        return getOrderBySelector().getTable();
    }

    default SQLOrderBySelector<T1> column(Property<T1, ?> column) {
        getOrderBySelector().column(EasyLambdaUtil.getPropertyName(column));
        return this;
    }

    default SQLOrderBySelector<T1> columnFunc(ColumnPropertyFunction columnPropertyFunction) {
        getOrderBySelector().columnFunc(columnPropertyFunction);
        return this;
    }

    /**
     * 请使用 sqlNativeSegment
     * @param columnConst
     * @return
     */
    @Deprecated
    default SQLOrderBySelector<T1> columnConst(String columnConst){
        return sqlNativeSegment(columnConst,c->{});
    }

    default <T2> SQLOrderBySelector<T2> then(SQLOrderBySelector<T2> sub) {
        return sub;
    }
}