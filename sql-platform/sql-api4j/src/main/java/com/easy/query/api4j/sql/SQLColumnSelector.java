package com.easy.query.api4j.sql;

import com.easy.query.core.expression.func.ColumnPropertyFunction;
import com.easy.query.core.expression.lambda.Property;
import com.easy.query.core.expression.parser.core.available.TableAvailable;
import com.easy.query.core.expression.parser.core.base.ColumnSelector;
import com.easy.query.core.util.EasyLambdaUtil;

/**
 * @author xuejiaming
 * @Description: 文件说明
 * @Date: 2023/2/6 23:20
 */
public interface SQLColumnSelector<T1> {
    ColumnSelector<T1> getColumnSelector();

    default TableAvailable getTable() {
        return getColumnSelector().getTable();
    }

    default SQLColumnSelector<T1> column(Property<T1, ?> column) {
        getColumnSelector().column(EasyLambdaUtil.getPropertyName(column));
        return this;
    }

    default SQLColumnSelector<T1> columnFunc(ColumnPropertyFunction columnPropertyFunction) {
        getColumnSelector().columnFunc(columnPropertyFunction);
        return this;
    }

    default SQLColumnSelector<T1> columnIgnore(Property<T1, ?> column) {
        getColumnSelector().columnIgnore(EasyLambdaUtil.getPropertyName(column));
        return this;
    }

    default SQLColumnSelector<T1> columnAll() {
        getColumnSelector().columnAll();
        return this;
    }

    default <T2> SQLColumnSelector<T2> then(SQLColumnSelector<T2> sub) {
        return sub;
    }
}
