package com.easy.query.api4kt.sql;

import com.easy.query.core.expression.func.ColumnPropertyFunction;
import com.easy.query.core.expression.lambda.Property;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.parser.core.base.OrderBySelector;
import com.easy.query.core.util.EasyLambdaUtil;

/**
 * create time 2023/6/16 21:54
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLKtOrderBySelector<T1> {
    OrderBySelector<T1> getOrderBySelector();

    default TableAvailable getTable() {
        return getOrderBySelector().getTable();
    }

    default SQLKtOrderBySelector<T1> column(Property<T1, ?> column) {
        getOrderBySelector().column(EasyLambdaUtil.getPropertyName(column));
        return this;
    }

    default SQLKtOrderBySelector<T1> columnFunc(ColumnPropertyFunction columnPropertyFunction) {
        getOrderBySelector().columnFunc(columnPropertyFunction);
        return this;
    }
    default SQLKtOrderBySelector<T1> columnConst(String columnConst) {
        getOrderBySelector().columnConst(columnConst);
        return this;
    }

    default SQLKtOrderBySelector<T1> columnIgnore(Property<T1, ?> column) {
        getOrderBySelector().columnIgnore(EasyLambdaUtil.getPropertyName(column));
        return this;
    }

    default SQLKtOrderBySelector<T1> columnAll() {
        getOrderBySelector().columnAll();
        return this;
    }

    default <T2> SQLKtOrderBySelector<T2> then(SQLKtOrderBySelector<T2> sub) {
        return sub;
    }
}