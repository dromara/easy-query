package com.easy.query.api4j.sql;

import com.easy.query.core.expression.SQLTableOwner;
import com.easy.query.core.expression.func.ColumnPropertyFunction;
import com.easy.query.core.expression.lambda.Property;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.parser.core.base.ColumnOrderSelector;
import com.easy.query.api4j.util.EasyLambdaUtil;

/**
 * create time 2023/6/16 21:54
 * 文件说明
 *
 * @author xuejiaming
 */
public interface SQLOrderBySelector<T1> extends SQLTableOwner {
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
    default SQLOrderBySelector<T1> columnConst(String columnConst) {
        getOrderBySelector().columnConst(columnConst);
        return this;
    }

    default <T2> SQLOrderBySelector<T2> then(SQLOrderBySelector<T2> sub) {
        return sub;
    }
}